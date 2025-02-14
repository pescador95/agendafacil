package app.api.agendaFacil.business.configuradorFeriado.manager;

import app.api.agendaFacil.business.configuradorFeriado.loader.ConfiguradorFeriadoLoader;
import app.api.agendaFacil.business.configuradorFeriado.repository.ConfiguradorFeriadoRepository;
import app.api.agendaFacil.business.configuradorFeriado.validator.ConfiguradorFeriadoValidator;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class ConfiguradorFeriadoManager extends ContextManager {

    protected final SecurityContext context;
    protected final ConfiguradorFeriadoRepository configuradorFeriadoRepository;
    protected final ConfiguradorFeriadoValidator configuradorFeriadoValidator;
    protected final ConfiguradorFeriadoLoader configuradorFeriadoLoader;
    protected final OrganizacaoLoader organizacaoLoader;

    protected ConfiguradorFeriadoManager(SecurityContext context, ConfiguradorFeriadoRepository configuradorFeriadoRepository, ConfiguradorFeriadoValidator configuradorFeriadoValidator, ConfiguradorFeriadoLoader configuradorFeriadoLoader, OrganizacaoLoader organizacaoLoader) {
        super(context);
        this.context = context;
        this.configuradorFeriadoRepository = configuradorFeriadoRepository;
        this.configuradorFeriadoValidator = configuradorFeriadoValidator;
        this.configuradorFeriadoLoader = configuradorFeriadoLoader;
        this.organizacaoLoader = organizacaoLoader;
    }

    protected ConfiguradorFeriadoManager() {
        super();
        this.context = null;
        this.configuradorFeriadoRepository = null;
        this.configuradorFeriadoValidator = null;
        this.configuradorFeriadoLoader = null;
        this.organizacaoLoader = null;
    }
}
