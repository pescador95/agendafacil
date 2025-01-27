package app.api.agendaFacil.management.rotina.repository;

import app.api.agendaFacil.management.rotina.entity.Rotina;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class RotinaRepository extends PersistenceRepository<Rotina> {

    public RotinaRepository() {
        super();
    }

    public static Rotina findById(Long id) {
        return Rotina.findById(id);
    }

    public static PanacheQuery<Rotina> find(String query) {
        return Rotina.find(query);
    }

    public static List<Rotina> listByIds(List<Long> rotinasId) {
        return Rotina.list("id in ?1", rotinasId);
    }

    public List<Rotina> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Rotina.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
