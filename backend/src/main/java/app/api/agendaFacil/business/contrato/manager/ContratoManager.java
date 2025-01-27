package app.api.agendaFacil.business.contrato.manager;

import app.api.agendaFacil.business.contrato.loader.ContratoLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class ContratoManager extends ContextManager {

    protected final SecurityContext context;
    protected final ContratoLoader contratoLoader;

    protected ContratoManager() {
        super();
        this.context = null;
        this.contratoLoader = null;
    }

    protected ContratoManager(SecurityContext context, ContratoLoader contratoLoader) {
        super(context);
        this.context = context;
        this.contratoLoader = contratoLoader;
    }
}
