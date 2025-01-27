package app.api.agendaFacil.business.tipoAgendamento.repository;

import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.query.TipoAgendamentoQueries;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class TipoAgendamentoRepository extends PersistenceRepository<TipoAgendamento> {

    final TipoAgendamentoQueries tipoAgendamentoQueries;

    public TipoAgendamentoRepository(TipoAgendamentoQueries tipoAgendamentoQueries) {
        super();
        this.tipoAgendamentoQueries = tipoAgendamentoQueries;
    }

    public static TipoAgendamento findById(Long id) {
        return TipoAgendamento.findById(id);
    }

    public static TipoAgendamento findByTipoAgendamento(String tipoAgendamento) {
        return TipoAgendamento.find("tipoAgendamento = ?1", tipoAgendamento).firstResult();
    }

    public static PanacheQuery<TipoAgendamento> find(String query) {
        return TipoAgendamento.find(query);
    }

    public static List<TipoAgendamento> listByIds(List<Long> pListIdTipoAgendamento) {
        return TipoAgendamento.list("id in ?1", pListIdTipoAgendamento);
    }

    public List<TipoAgendamento> listTipoAgendamentoByOrganizacoes(List<Long> organizacoes) {
        return tipoAgendamentoQueries.listTipoAgendamentoByOrganizacoes(organizacoes);
    }


    public List<TipoAgendamentoDTO> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, TipoAgendamentoDTO.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
