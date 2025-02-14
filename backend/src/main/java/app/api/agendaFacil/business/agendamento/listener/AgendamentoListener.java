package app.api.agendaFacil.business.agendamento.listener;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.management.scheduler.service.SchedulerServices;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.jetbrains.annotations.NotNull;

@ApplicationScoped
public class AgendamentoListener {

    @Inject
    SchedulerServices schedulerServices;

    public AgendamentoListener() {

    }

    public AgendamentoListener(SchedulerServices schedulerServices) {
        this.schedulerServices = schedulerServices;
    }

    @PostPersist
    void onAgendamentoCreated(@NotNull Agendamento agendamento) {
        verificarAgendamentoEAdicionarNaFila(agendamento);
    }

    @PostUpdate
    void onAgendamentoUpdated(@NotNull Agendamento agendamento) {

        if (agendamento.ativo()) {
            verificarAgendamentoEAtualizarNaFila(agendamento);
        }

        if (!agendamento.ativo()) {
            verificarAgendamentoERemoverDaFila(agendamento);
        }
    }

    void verificarAgendamentoEAdicionarNaFila(Agendamento agendamento) {
        if (agendamento.adicionarAgendamentoNaFila()) {

        }
    }

    void verificarAgendamentoEAtualizarNaFila(Agendamento agendamento) {
        if (agendamento.adicionarAgendamentoNaFila()) {

        }
    }

    void verificarAgendamentoERemoverDaFila(Agendamento agendamento) {
        if (agendamento.removerAgendamentoDaFila()) {

        }
    }
}
