package app.api.agendaFacil.business.pessoa.manager;

import app.api.agendaFacil.business.genero.loader.GeneroLoader;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.pessoa.validator.PessoaValidator;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class PessoaManager extends ContextManager {

    protected final SecurityContext context;
    protected final PessoaValidator pessoaValidator;
    protected final PessoaLoader pessoaLoader;
    protected final GeneroLoader generoLoader;

    protected PessoaManager(SecurityContext context, PessoaValidator pessoaValidator, PessoaLoader pessoaLoader, GeneroLoader generoLoader) {
        super(context);
        this.context = context;
        this.pessoaValidator = pessoaValidator;
        this.pessoaLoader = pessoaLoader;
        this.generoLoader = generoLoader;
    }

    protected PessoaManager() {
        super();
        this.context = null;
        this.pessoaValidator = null;
        this.pessoaLoader = null;
        this.generoLoader = null;
    }
}
