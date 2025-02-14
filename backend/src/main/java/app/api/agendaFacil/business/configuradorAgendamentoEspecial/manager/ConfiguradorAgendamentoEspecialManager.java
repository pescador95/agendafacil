package app.api.agendaFacil.business.configuradorAgendamentoEspecial.manager;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.loader.ConfiguradorAgendamentoEspecialLoader;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.repository.ConfiguradorAgendamentoEspecialRepository;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.validator.ConfiguradorAgendamentoEspecialValidator;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class ConfiguradorAgendamentoEspecialManager extends ContextManager {

    protected final SecurityContext context;
    protected final ConfiguradorAgendamentoEspecialRepository configuradorAgendamentoEspecialRepository;
    protected final ConfiguradorAgendamentoEspecialValidator configuradorAgendamentoEspecialValidator;
    protected final ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoEspecialLoader;
    protected final TipoAgendamentoLoader tipoAgendamentoLoader;
    protected final OrganizacaoLoader organizacaoLoader;
    protected final UsuarioLoader usuarioLoader;

    protected ConfiguradorAgendamentoEspecialManager(SecurityContext context, ConfiguradorAgendamentoEspecialRepository configuradorAgendamentoEspecialRepository, ConfiguradorAgendamentoEspecialValidator configuradorAgendamentoEspecialValidator, ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoEspecialLoader, TipoAgendamentoLoader tipoAgendamentoLoader, OrganizacaoLoader organizacaoLoader, UsuarioLoader usuarioLoader) {
        super(context);
        this.context = context;
        this.configuradorAgendamentoEspecialRepository = configuradorAgendamentoEspecialRepository;
        this.configuradorAgendamentoEspecialValidator = configuradorAgendamentoEspecialValidator;
        this.configuradorAgendamentoEspecialLoader = configuradorAgendamentoEspecialLoader;
        this.tipoAgendamentoLoader = tipoAgendamentoLoader;
        this.organizacaoLoader = organizacaoLoader;
        this.usuarioLoader = usuarioLoader;
    }

    protected ConfiguradorAgendamentoEspecialManager() {
        super();
        this.context = null;
        this.configuradorAgendamentoEspecialRepository = null;
        this.configuradorAgendamentoEspecialValidator = null;
        this.configuradorAgendamentoEspecialLoader = null;
        this.tipoAgendamentoLoader = null;
        this.organizacaoLoader = null;
        this.usuarioLoader = null;
    }

}
