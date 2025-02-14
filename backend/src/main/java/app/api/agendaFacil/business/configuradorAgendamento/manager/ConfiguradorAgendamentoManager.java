package app.api.agendaFacil.business.configuradorAgendamento.manager;

import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.configuradorAgendamento.repository.ConfiguradorAgendamentoRepository;
import app.api.agendaFacil.business.configuradorAgendamento.validator.ConfiguradorAgendamentoValidator;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class ConfiguradorAgendamentoManager extends ContextManager {

    protected final SecurityContext context;
    protected final ConfiguradorAgendamentoRepository configuradorAgendamentoRepository;
    protected final ConfiguradorAgendamentoValidator configuradorAgendamentoValidator;
    protected final ConfiguradorAgendamentoLoader configuradorAgendamentoLoader;
    protected final OrganizacaoLoader organizacaoLoader;
    protected final UsuarioLoader usuarioLoader;

    protected ConfiguradorAgendamentoManager(SecurityContext context, ConfiguradorAgendamentoRepository configuradorAgendamentoRepository, ConfiguradorAgendamentoValidator configuradorAgendamentoValidator, ConfiguradorAgendamentoLoader configuradorAgendamentoLoader, OrganizacaoLoader organizacaoLoader, UsuarioLoader usuarioLoader) {
        super(context);
        this.context = context;
        this.configuradorAgendamentoRepository = configuradorAgendamentoRepository;
        this.configuradorAgendamentoValidator = configuradorAgendamentoValidator;
        this.configuradorAgendamentoLoader = configuradorAgendamentoLoader;
        this.organizacaoLoader = organizacaoLoader;
        this.usuarioLoader = usuarioLoader;
    }

    protected ConfiguradorAgendamentoManager() {
        super();
        this.context = null;
        this.configuradorAgendamentoRepository = null;
        this.configuradorAgendamentoValidator = null;
        this.configuradorAgendamentoLoader = null;
        this.organizacaoLoader = null;
        this.usuarioLoader = null;
    }
}
