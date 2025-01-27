package app.api.agendaFacil.management.recoveryPassword.manager;

import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.core.application.ContextManager;
import app.core.application.DTO.Responses;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class RecoveryPasswordManager extends ContextManager {

    protected final Mailer mailer;
    protected final SecurityContext context;
    protected final UsuarioLoader usuarioLoader;
    protected final PessoaLoader pessoaLoader;
    protected final OrganizacaoLoader organizacaoLoader;

    protected Responses responses;

    protected RecoveryPasswordManager() {
        super();
        this.mailer = null;
        this.context = null;
        this.usuarioLoader = null;
        this.pessoaLoader = null;
        this.organizacaoLoader = null;
    }

    protected RecoveryPasswordManager(SecurityContext context, Mailer mailer, UsuarioLoader usuarioLoader, PessoaLoader pessoaLoader, OrganizacaoLoader organizacaoLoader) {
        super(context);
        this.mailer = mailer;
        this.context = context;
        this.usuarioLoader = usuarioLoader;
        this.pessoaLoader = pessoaLoader;
        this.organizacaoLoader = organizacaoLoader;
    }
}
