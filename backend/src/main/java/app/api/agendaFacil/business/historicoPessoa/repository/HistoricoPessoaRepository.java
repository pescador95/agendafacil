package app.api.agendaFacil.business.historicoPessoa.repository;

import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class HistoricoPessoaRepository extends PersistenceRepository<HistoricoPessoa> {

    public HistoricoPessoaRepository() {
        super();
    }

    public static HistoricoPessoa findById(Long id) {
        return HistoricoPessoa.findById(id);
    }

    public static HistoricoPessoa findById(HistoricoPessoa pHistoricoPessoa) {
        return HistoricoPessoa.find("id = ?1 and ativo = true", pHistoricoPessoa.getId()).firstResult();
    }

    public static HistoricoPessoa findByPessoa(Pessoa pPessoa) {
        return HistoricoPessoa.find("pessoa = ?1 and ativo = true", pPessoa).firstResult();
    }

    public static PanacheQuery<HistoricoPessoa> find(String query) {
        return HistoricoPessoa.find(query);
    }

    public static List<HistoricoPessoa> listByIds(List<Long> pListIdHistoricoPessoa, Boolean ativo) {
        return HistoricoPessoa.list("id in ?1 and ativo = ?2", pListIdHistoricoPessoa, ativo);
    }

    public List<HistoricoPessoa> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, HistoricoPessoa.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
