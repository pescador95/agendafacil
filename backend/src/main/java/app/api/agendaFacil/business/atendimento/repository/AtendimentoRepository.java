package app.api.agendaFacil.business.atendimento.repository;

import app.api.agendaFacil.business.atendimento.entity.Atendimento;
import app.api.agendaFacil.business.atendimento.query.AtendimentoQueries;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class AtendimentoRepository extends PersistenceRepository<Atendimento> {

    final AtendimentoQueries atendimentoQueries;

    public AtendimentoRepository(AtendimentoQueries atendimentoQueries) {
        super();
        this.atendimentoQueries = atendimentoQueries;
    }

    public static Atendimento findById(Long id) {
        return Atendimento.findById(id);
    }

    public static Atendimento findById(Atendimento pAtendimento) {
        return Atendimento.find("id = ?1 and ativo = true", pAtendimento.getId()).firstResult();
    }

    public static PanacheQuery<Atendimento> find(String query) {
        return Atendimento.find(query);
    }

    public static Atendimento findByPessoaDataAtendimento(Atendimento pAtendimento) {

        return Atendimento.find("pessoa = ?1 and dataAtendimento = ?2 and ativo = true",
                        pAtendimento.getPessoa(),
                        pAtendimento.getDataAtendimento())
                .firstResult();
    }

    public static List<Atendimento> listByIds(List<Long> pListIdAtendimento, Boolean ativo) {
        return Atendimento.list("id in ?1 and ativo = ?2", pListIdAtendimento, ativo);
    }

    public List<Atendimento> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Atendimento.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
