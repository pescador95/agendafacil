package app.api.agendaFacil.business.configuradorAusencia.manager;

import app.api.agendaFacil.business.configuradorAusencia.loader.ConfiguradorAusenciaLoader;
import app.api.agendaFacil.business.configuradorAusencia.repository.ConfiguradorAusenciaRepository;
import app.api.agendaFacil.business.configuradorAusencia.validator.ConfiguradorAusenciaValidator;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class ConfiguradorAusenciaManager extends ContextManager {

    protected final SecurityContext context;
    protected final ConfiguradorAusenciaRepository configuradorAusenciaRepository;
    protected final ConfiguradorAusenciaValidator configuradorAusenciaValidator;
    protected final ConfiguradorAusenciaLoader configuradorAusenciaLoader;
    protected final UsuarioLoader usuarioLoader;

    protected ConfiguradorAusenciaManager(SecurityContext context, ConfiguradorAusenciaRepository configuradorAusenciaRepository, ConfiguradorAusenciaValidator configuradorAusenciaValidator, ConfiguradorAusenciaLoader configuradorAusenciaLoader, UsuarioLoader usuarioLoader) {
        super(context);
        this.context = context;
        this.configuradorAusenciaRepository = configuradorAusenciaRepository;
        this.configuradorAusenciaValidator = configuradorAusenciaValidator;
        this.configuradorAusenciaLoader = configuradorAusenciaLoader;
        this.usuarioLoader = usuarioLoader;
    }

    protected ConfiguradorAusenciaManager() {
        super();
        this.context = null;
        this.configuradorAusenciaRepository = null;
        this.configuradorAusenciaValidator = null;
        this.configuradorAusenciaLoader = null;
        this.usuarioLoader = null;
    }

}
