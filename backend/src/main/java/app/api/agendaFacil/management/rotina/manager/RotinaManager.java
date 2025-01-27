package app.api.agendaFacil.management.rotina.manager;

import app.core.application.ContextManager;
import app.core.application.DTO.Responses;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class RotinaManager extends ContextManager {

    protected final SecurityContext context;
    protected Responses responses;
    protected String query;

    protected RotinaManager() {
        super();
        this.context = null;
    }

    protected RotinaManager(SecurityContext context) {
        super(context);
        this.context = context;
    }
}
