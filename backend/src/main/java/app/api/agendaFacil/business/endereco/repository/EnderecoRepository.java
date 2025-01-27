package app.api.agendaFacil.business.endereco.repository;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class EnderecoRepository extends PersistenceRepository<Endereco> {

    public EnderecoRepository() {
        super();
    }

    public static Endereco findById(Long id) {
        return Endereco.findById(id);
    }

    public static PanacheQuery<Endereco> find(String query) {
        return Endereco.find(query);
    }

    public static List<Endereco> listByIds(List<Long> pListIdEndereco, Boolean ativo) {
        return Endereco.list("id in ?1 and ativo = ?2", pListIdEndereco, ativo);
    }

    public List<Endereco> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Endereco.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
