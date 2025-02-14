package app.api.agendaFacil.management.scheduler.manager;

import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.lembrete.thread.LembreteThread;
import app.core.application.ContextManager;
import app.core.application.tenant.TenantService;
import app.core.helpers.trace.Invoker;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public abstract class SchedulerManager extends ContextManager {

    protected final LembreteThread lembreteThread;
    protected final Invoker invoker;
    protected final TenantService tenantService;
    protected final AgendamentoRepository agendamentoRepository;

    protected SchedulerManager(LembreteThread lembreteThread, Invoker invoker, TenantService tenantService, AgendamentoRepository agendamentoRepository) {
        super();
        this.lembreteThread = lembreteThread;
        this.invoker = invoker;
        this.tenantService = tenantService;
        this.agendamentoRepository = agendamentoRepository;
    }

    protected SchedulerManager() {
        super();
        this.lembreteThread = null;
        this.invoker = null;
        this.tenantService = null;
        this.agendamentoRepository = null;
    }
}
