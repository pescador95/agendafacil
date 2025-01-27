package app.api.agendaFacil.management.role.manager;

import app.api.agendaFacil.management.role.loader.RoleLoader;
import app.api.agendaFacil.management.role.validator.RoleValidator;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class RoleManager extends ContextManager {


    protected final SecurityContext context;
    protected final RoleLoader roleLoader;
    protected final RoleValidator roleValidator;

    public RoleManager(SecurityContext context, RoleLoader roleLoader, RoleValidator roleValidator) {
        super(context);
        this.context = context;
        this.roleLoader = roleLoader;
        this.roleValidator = roleValidator;
    }

    public RoleManager() {
        super();
        this.context = null;
        this.roleLoader = null;
        this.roleValidator = null;
    }

}