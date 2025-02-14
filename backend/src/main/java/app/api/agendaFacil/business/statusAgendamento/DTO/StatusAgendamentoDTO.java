package app.api.agendaFacil.business.statusAgendamento.DTO;

import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.core.helpers.utils.BasicFunctions;

import static app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento.AGENDADO;

public class StatusAgendamentoDTO {

    private Long id;

    private String status;

    public StatusAgendamentoDTO() {
    }

    public StatusAgendamentoDTO(StatusAgendamento statusAgendamento) {

        if (BasicFunctions.isNotEmpty(statusAgendamento)) {

            this.id = statusAgendamento.getId();
            this.status = statusAgendamento.getStatus();

        }
    }

    public StatusAgendamentoDTO(StatusAgendamentoDTO statusAgendamento) {

        if (BasicFunctions.isNotEmpty(statusAgendamento)) {

            this.id = statusAgendamento.getId();
            this.status = statusAgendamento.getStatus();

        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean agendado() {
        return this.id == AGENDADO;
    }
}
