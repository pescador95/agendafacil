package app.api.agendaFacil.business.lembrete.manager;

import app.api.agendaFacil.business.lembrete.thread.LembreteThread;
import app.core.application.ContextManager;
import app.core.application.DTO.Responses;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public abstract class LembreteManager extends ContextManager {

    protected final LembreteThread lembreteThread;
    protected Responses responses;

    protected LembreteManager() {
        this.lembreteThread = null;
    }

    protected LembreteManager(LembreteThread lembreteThread) {
        this.lembreteThread = lembreteThread;
    }


}
