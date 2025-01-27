package app.api.agendaFacil.management.profile.manager;

import app.core.application.ContextManager;
import app.core.application.DTO.Responses;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public abstract class ProfileManager extends ContextManager {

    protected final SecurityContext context;
    @Inject
    @ConfigProperty(name = "quarkus.http.body.uploads-directory")
    protected String directory;
    protected Responses responses;

    protected ProfileManager() {
        super();
        this.context = null;
    }

    protected ProfileManager(SecurityContext context) {
        super(context);
        this.context = context;
    }


}