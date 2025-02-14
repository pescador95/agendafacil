package app.core.application.tenant;

import app.core.application.persistence.PersistenceRepository;
import app.core.helpers.utils.Contexto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
@Transactional
public class TenantRepository extends PersistenceRepository<Tenant> {

    final EntityManager em;

    public TenantRepository(EntityManager em) {
        super();
        this.em = em;
    }

    public static PanacheQuery<Tenant> find(String query) {
        return Tenant.find(query);
    }

    public static Tenant findById(Long id) {
        return Tenant.findById(id);
    }

    public static Tenant findByTenant(String tenant) {
        return Tenant.find("tenant = ?1", tenant).firstResult();
    }

    public static List<Tenant> listAll() {
        return Tenant.listAll();
    }


    public void insertTenantFromContrato() {
        String sql = "INSERT INTO config.tenant (tenant, contratoid) " +
                "SELECT tenant, id FROM config.contrato co " +
                "WHERE co.id NOT IN (SELECT contratoid FROM config.tenant t WHERE contratoid = co.id and ativo = true)";

        em.createNativeQuery(sql).executeUpdate();
    }

    public List<Tenant> listAllTenantsByContratoAtivo() {

        List<Tenant> returnList;

        try {

            Query query = em.createNativeQuery(
                    "select t.* from config.tenant t join config.contrato c on c.id = t.contratoid where c.dataterminocontrato is null or c.dataterminocontrato >= :dataContrato",
                    Tenant.class);
            query.setParameter("dataContrato", Contexto.dataContexto());
            returnList = (List<Tenant>) query.getResultList();

            return returnList;
        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
            return null;
        }
    }
}
