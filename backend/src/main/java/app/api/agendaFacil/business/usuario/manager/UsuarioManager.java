package app.api.agendaFacil.business.usuario.manager;

import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.configuradorAgendamento.service.ConfiguradorAgendamentoService;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.loader.ConfiguradorAgendamentoEspecialLoader;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.pessoa.repository.PessoaRepository;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.business.usuario.repository.UsuarioRepository;
import app.api.agendaFacil.business.usuario.validator.UsuarioValidator;
import app.api.agendaFacil.management.role.loader.RoleLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class UsuarioManager extends ContextManager {

    protected final SecurityContext context;
    protected final PessoaRepository pessoaRepository;
    protected final ConfiguradorAgendamentoService configuradorAgendamentoService;
    protected final UsuarioValidator usuarioValidator;
    protected final UsuarioRepository usuarioRepository;
    protected final UsuarioLoader usuarioLoader;
    protected final PessoaLoader pessoaLoader;
    protected final RoleLoader roleLoader;
    protected final OrganizacaoLoader organizacaoLoader;
    protected final TipoAgendamentoLoader tipoAgendamentoLoader;
    protected final ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoEspecialLoader;
    protected final ConfiguradorAgendamentoLoader configuradorAgendamentoLoader;

    protected UsuarioManager(UsuarioRepository usuarioRepository, PessoaRepository pessoaRepository, SecurityContext context, ConfiguradorAgendamentoService configuradorAgendamentoService, UsuarioValidator usuarioValidator, UsuarioLoader usuarioLoader, PessoaLoader pessoaLoader, RoleLoader roleLoader, OrganizacaoLoader organizacaoLoader, TipoAgendamentoLoader tipoAgendamentoLoader, ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoEspecialLoader, ConfiguradorAgendamentoLoader configuradorAgendamentoLoader) {
        super(context);
        this.context = context;
        this.configuradorAgendamentoService = configuradorAgendamentoService;
        this.usuarioValidator = usuarioValidator;
        this.usuarioRepository = usuarioRepository;
        this.pessoaRepository = pessoaRepository;
        this.usuarioLoader = usuarioLoader;
        this.pessoaLoader = pessoaLoader;
        this.roleLoader = roleLoader;
        this.organizacaoLoader = organizacaoLoader;
        this.tipoAgendamentoLoader = tipoAgendamentoLoader;
        this.configuradorAgendamentoEspecialLoader = configuradorAgendamentoEspecialLoader;
        this.configuradorAgendamentoLoader = configuradorAgendamentoLoader;
    }

    protected UsuarioManager() {
        super();
        this.context = null;
        this.configuradorAgendamentoService = null;
        this.usuarioValidator = null;
        this.usuarioRepository = null;
        this.pessoaRepository = null;
        this.usuarioLoader = null;
        this.pessoaLoader = null;
        this.roleLoader = null;
        this.organizacaoLoader = null;
        this.tipoAgendamentoLoader = null;
        this.configuradorAgendamentoLoader = null;
        this.configuradorAgendamentoEspecialLoader = null;
    }
}
