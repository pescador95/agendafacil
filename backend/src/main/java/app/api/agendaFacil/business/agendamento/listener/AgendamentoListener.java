package app.api.agendaFacil.business.agendamento.listener;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.lembrete.thread.LembreteThread;
import app.api.agendaFacil.management.scheduler.service.SchedulerServices;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.jetbrains.annotations.NotNull;

@ApplicationScoped
public class AgendamentoListener {

    final LembreteThread lembreteThread;
    private final SchedulerServices schedulerServices;

    public AgendamentoListener(LembreteThread lembreteThread, SchedulerServices schedulerServices) {
        this.lembreteThread = lembreteThread;
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
            schedulerServices.verifyAndRunScheduler();
        }
    }

    void verificarAgendamentoEAtualizarNaFila(Agendamento agendamento) {
        if (agendamento.adicionarAgendamentoNaFila()) {
            schedulerServices.verifyAndRunScheduler();
        }
    }

    void verificarAgendamentoERemoverDaFila(Agendamento agendamento) {
        if (agendamento.removerAgendamentoDaFila()) {
            schedulerServices.verifyAndRunScheduler();
        }
    }
}
