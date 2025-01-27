package app.api.agendaFacil.business.agendamento.manager;

import app.api.agendaFacil.business.agendamento.loader.AgendamentoAutomaticoLoader;
import app.api.agendaFacil.business.agendamento.loader.AgendamentoLoader;
import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.agendamento.validator.AgendamentoAutomaticoValidator;
import app.api.agendaFacil.business.agendamento.validator.AgendamentoValidator;
import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.configuradorAgendamento.validator.ConfiguradorAgendamentoValidator;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.statusAgendamento.loader.StatusAgendamentoLoader;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class AgendamentoManager extends ContextManager {

    protected final SecurityContext context;
    protected final AgendamentoRepository agendamentoRepository;
    protected final AgendamentoValidator agendamentoValidator;
    protected final AgendamentoLoader agendamentoLoader;
    protected final AgendamentoAutomaticoLoader agendamentoAutomaticoLoader;
    protected final AgendamentoAutomaticoValidator agendamentoAutomaticoValidator;
    protected final ConfiguradorAgendamentoLoader configuradorAgendamentoService;
    protected final ConfiguradorAgendamentoValidator configuradorAgendamentoValidator;
    protected final UsuarioLoader usuarioLoader;
    protected final TipoAgendamentoLoader tipoAgendamentoLoader;
    protected final PessoaLoader pessoaLoader;
    protected final OrganizacaoLoader organizacaoLoader;
    protected final StatusAgendamentoLoader statusAgendamentoLoader;

    protected AgendamentoManager(UsuarioLoader usuarioLoader,
                                 AgendamentoRepository agendamentoRepository,
                                 ConfiguradorAgendamentoLoader configuradorAgendamentoLoader,
                                 AgendamentoAutomaticoValidator agendamentoAutomaticoValidator,
                                 AgendamentoValidator agendamentoValidator,
                                 ConfiguradorAgendamentoValidator configuradorAgendamentoValidator,
                                 SecurityContext context,
                                 AgendamentoLoader agendamentoLoader,
                                 AgendamentoAutomaticoLoader agendamentoAutomaticoLoader,
                                 TipoAgendamentoLoader tipoAgendamentoLoader,
                                 PessoaLoader pessoaLoader,
                                 OrganizacaoLoader organizacaoLoader,
                                 StatusAgendamentoLoader statusAgendamentoLoader) {
        super(context);
        this.usuarioLoader = usuarioLoader;
        this.agendamentoRepository = agendamentoRepository;
        this.configuradorAgendamentoService = configuradorAgendamentoLoader;
        this.agendamentoAutomaticoValidator = agendamentoAutomaticoValidator;
        this.agendamentoValidator = agendamentoValidator;
        this.configuradorAgendamentoValidator = configuradorAgendamentoValidator;
        this.context = context;
        this.agendamentoLoader = agendamentoLoader;
        this.agendamentoAutomaticoLoader = agendamentoAutomaticoLoader;
        this.tipoAgendamentoLoader = tipoAgendamentoLoader;
        this.pessoaLoader = pessoaLoader;
        this.organizacaoLoader = organizacaoLoader;
        this.statusAgendamentoLoader = statusAgendamentoLoader;
    }

    protected AgendamentoManager() {
        super();
        this.context = null;
        this.agendamentoRepository = null;
        this.agendamentoValidator = null;
        this.agendamentoLoader = null;
        this.agendamentoAutomaticoLoader = null;
        this.agendamentoAutomaticoValidator = null;
        this.configuradorAgendamentoService = null;
        this.configuradorAgendamentoValidator = null;
        this.usuarioLoader = null;
        this.tipoAgendamentoLoader = null;
        this.pessoaLoader = null;
        this.organizacaoLoader = null;
        this.statusAgendamentoLoader = null;
    }
}
