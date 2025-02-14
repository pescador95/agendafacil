package app.api.agendaFacil.business.perfilAcesso.repository;

import app.api.agendaFacil.business.perfilAcesso.entity.PerfilAcesso;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class PerfilAcessoRepository extends PersistenceRepository<PerfilAcesso> {

    public PerfilAcessoRepository() {
        super();
    }

    public static PerfilAcesso findById(Long id) {
        return PerfilAcesso.findById(id);
    }

    public static PanacheQuery<PerfilAcesso> find(String query) {
        return PerfilAcesso.find(query);
    }

    public static List<PerfilAcesso> listByIds(List<Long> pListIdPerfilAcesso) {
        return PerfilAcesso.list("id in ?1", pListIdPerfilAcesso);
    }

    public List<PerfilAcesso> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, PerfilAcesso.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
