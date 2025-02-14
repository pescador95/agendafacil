package app.api.agendaFacil.business.agendamento.manager;

import app.api.agendaFacil.business.agendamento.loader.AgendamentoAutomaticoLoader;
import app.api.agendaFacil.business.agendamento.loader.AgendamentoLoader;
import app.api.agendaFacil.business.agendamento.validator.AgendamentoAutomaticoValidator;
import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.business.usuario.service.UsuarioService;
import app.api.agendaFacil.business.usuario.validator.UsuarioValidator;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class AgendamentoAutomaticoManager extends ContextManager {

    protected final SecurityContext context;
    protected final AgendamentoLoader agendamentoLoader;
    protected final AgendamentoAutomaticoLoader agendamentoAutomaticoLoader;
    protected final AgendamentoAutomaticoValidator agendamentoAutomaticoValidator;
    protected final ConfiguradorAgendamentoLoader configuradorAgendamentoLoader;
    protected final UsuarioService usuarioService;
    protected final UsuarioValidator usuarioValidator;
    protected final UsuarioLoader usuarioLoader;
    protected final TipoAgendamentoLoader tipoAgendamentoLoader;
    protected final PessoaLoader pessoaLoader;
    protected final OrganizacaoLoader organizacaoLoader;

    protected AgendamentoAutomaticoManager(UsuarioService usuarioService, ConfiguradorAgendamentoLoader configuradorAgendamentoLoader, AgendamentoAutomaticoValidator agendamentoAutomaticoValidator, UsuarioValidator usuarioValidator, SecurityContext context, AgendamentoLoader agendamentoLoader, AgendamentoAutomaticoLoader agendamentoAutomaticoLoader, UsuarioLoader usuarioLoader, TipoAgendamentoLoader tipoAgendamentoLoader, PessoaLoader pessoaLoader, OrganizacaoLoader organizacaoLoader) {
        super(context);
        this.usuarioService = usuarioService;
        this.configuradorAgendamentoLoader = configuradorAgendamentoLoader;
        this.agendamentoAutomaticoValidator = agendamentoAutomaticoValidator;
        this.usuarioValidator = usuarioValidator;
        this.context = context;
        this.agendamentoLoader = agendamentoLoader;
        this.agendamentoAutomaticoLoader = agendamentoAutomaticoLoader;
        this.usuarioLoader = usuarioLoader;
        this.tipoAgendamentoLoader = tipoAgendamentoLoader;
        this.pessoaLoader = pessoaLoader;
        this.organizacaoLoader = organizacaoLoader;
    }

    protected AgendamentoAutomaticoManager() {
        super();
        this.usuarioService = null;
        this.configuradorAgendamentoLoader = null;
        this.agendamentoAutomaticoValidator = null;
        this.usuarioValidator = null;
        this.context = null;
        this.agendamentoLoader = null;
        this.agendamentoAutomaticoLoader = null;
        this.usuarioLoader = null;
        this.tipoAgendamentoLoader = null;
        this.pessoaLoader = null;
        this.organizacaoLoader = null;
    }


}
