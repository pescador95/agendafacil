package app.api.agendaFacil.business.statusAgendamento.repository;

import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class StatusAgendamentoRepository extends PersistenceRepository<StatusAgendamento> {

    final StatusAgendamentoRepository statusAgendamentoRepository;

    public StatusAgendamentoRepository(StatusAgendamentoRepository statusAgendamentoRepository) {
        super();
        this.statusAgendamentoRepository = statusAgendamentoRepository;
    }

    public static StatusAgendamento findById(Long id) {
        return StatusAgendamento.findById(id);
    }

    public static StatusAgendamento findByStatus(String status) {
        return StatusAgendamento.find("status = ?1 ", status).firstResult();
    }

    public static PanacheQuery<StatusAgendamento> find(String query) {
        return StatusAgendamento.find(query);
    }

    public static List<StatusAgendamento> listByIds(List<Long> pListIdStatusAgendamento) {
        return StatusAgendamento.list("id in ?1", pListIdStatusAgendamento);
    }

    public List<StatusAgendamento> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, StatusAgendamento.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
