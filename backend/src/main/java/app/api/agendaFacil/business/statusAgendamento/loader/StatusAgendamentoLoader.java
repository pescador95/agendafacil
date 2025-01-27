package app.api.agendaFacil.business.statusAgendamento.loader;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.statusAgendamento.DTO.StatusAgendamentoDTO;
import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.api.agendaFacil.business.statusAgendamento.repository.StatusAgendamentoRepository;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class StatusAgendamentoLoader {

    final StatusAgendamentoLoader statusAgendamentoLoader;
    final StatusAgendamentoRepository statusAgendamentoRepository;

    public StatusAgendamentoLoader(StatusAgendamentoLoader statusAgendamentoLoader, StatusAgendamentoRepository statusAgendamentoRepository) {
        this.statusAgendamentoLoader = statusAgendamentoLoader;
        this.statusAgendamentoRepository = statusAgendamentoRepository;
    }

    public static StatusAgendamento findById(Long id) {
        return StatusAgendamentoRepository.findById(id);
    }

    public static List<StatusAgendamento> listByIds(List<Long> pListIdStatusAgendamento) {
        if (BasicFunctions.isNotEmpty(pListIdStatusAgendamento)) {
            return StatusAgendamentoRepository.listByIds(pListIdStatusAgendamento);
        }
        return null;
    }

    public static PanacheQuery<StatusAgendamento> find(String query) {
        return StatusAgendamentoRepository.find(query);
    }

    public StatusAgendamento loadByAgendamento(AgendamentoDTO pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getStatusAgendamento())) {
            if (BasicFunctions.isNotEmpty(pAgendamento.getStatusAgendamento().getId())) {
                return StatusAgendamentoRepository.findById(pAgendamento.getStatusAgendamento().getId());
            }
            if (BasicFunctions.isNotEmpty(pAgendamento.getStatusAgendamento().getStatus())) {
                return StatusAgendamentoRepository.findByStatus(pAgendamento.getStatusAgendamento().getStatus());
            }
        }
        return null;
    }

    public StatusAgendamento findByStatusAgendamento(StatusAgendamentoDTO pStatusAgendamento) {
        if (BasicFunctions.isNotEmpty(pStatusAgendamento)) {
            if (BasicFunctions.isNotEmpty(pStatusAgendamento.getId())) {
                return StatusAgendamentoRepository.findById(pStatusAgendamento.getId());
            }
            if (BasicFunctions.isNotEmpty(pStatusAgendamento.getStatus())) {
                return StatusAgendamentoRepository.findByStatus(pStatusAgendamento.getStatus());
            }
        }
        return null;
    }

    public List<StatusAgendamento> list(QueryFilter queryFilter) {
        return statusAgendamentoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return statusAgendamentoRepository.count(queryFilter);
    }
}
