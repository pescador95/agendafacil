package app.api.agendaFacil.business.statusAgendamento.manager;

import app.api.agendaFacil.business.statusAgendamento.loader.StatusAgendamentoLoader;
import app.api.agendaFacil.business.statusAgendamento.repository.StatusAgendamentoRepository;
import app.core.application.ContextManager;
import app.core.application.DTO.Responses;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class StatusAgendamentoManager extends ContextManager {

    protected final SecurityContext context;
    protected final StatusAgendamentoRepository statusAgendamentoRepository;
    protected final StatusAgendamentoLoader statusAgendamentoLoader;

    protected Responses responses;
    protected String query;

    protected StatusAgendamentoManager(StatusAgendamentoRepository statusAgendamentoRepository, StatusAgendamentoLoader statusAgendamentoLoader, SecurityContext context) {
        super(context);
        this.context = context;
        this.statusAgendamentoRepository = statusAgendamentoRepository;
        this.statusAgendamentoLoader = statusAgendamentoLoader;
    }

    protected StatusAgendamentoManager() {
        super();
        this.statusAgendamentoRepository = null;
        this.statusAgendamentoLoader = null;
        this.context = null;
    }
}
