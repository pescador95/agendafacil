package app.api.agendaFacil.business.genero.repository;

import app.api.agendaFacil.business.genero.entity.Genero;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class GeneroRepository extends PersistenceRepository<Genero> {

    public GeneroRepository() {
        super();
    }

    public static Genero findById(Long id) {
        return Genero.findById(id);
    }

    public static Genero findByGenero(String genero) {
        return Genero.find("genero = ?1 ", genero).firstResult();
    }

    public static PanacheQuery<Genero> find(String query) {
        return Genero.find(query);
    }

    public static List<Genero> listByIds(List<Long> pListIdGenero) {
        return Genero.list("id in ?1", pListIdGenero);
    }

    @Override
    @Transactional
    public void remove(Genero entity) {
        super.remove(entity);
    }

    public List<Genero> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Genero.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
