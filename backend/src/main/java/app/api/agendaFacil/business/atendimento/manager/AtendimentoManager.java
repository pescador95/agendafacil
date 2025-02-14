package app.api.agendaFacil.business.atendimento.manager;

import app.api.agendaFacil.business.atendimento.loader.AtendimentoLoader;
import app.api.agendaFacil.business.atendimento.repository.AtendimentoRepository;
import app.api.agendaFacil.business.atendimento.validator.AtendimentoValidator;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class AtendimentoManager extends ContextManager {

    protected final SecurityContext context;
    protected final AtendimentoRepository atendimentoRepository;
    protected final AtendimentoValidator atendimentoValidator;
    protected final AtendimentoLoader atendimentoLoader;
    protected final PessoaLoader pessoaLoader;

    protected AtendimentoManager(AtendimentoRepository atendimentoRepository, AtendimentoValidator atendimentoValidator, SecurityContext context, AtendimentoLoader atendimentoLoader, PessoaLoader pessoaLoader) {
        super(context);
        this.atendimentoRepository = atendimentoRepository;
        this.atendimentoValidator = atendimentoValidator;
        this.context = context;
        this.atendimentoLoader = atendimentoLoader;
        this.pessoaLoader = pessoaLoader;
    }

    protected AtendimentoManager() {
        super();
        this.atendimentoRepository = null;
        this.atendimentoValidator = null;
        this.context = null;
        this.atendimentoLoader = null;
        this.pessoaLoader = null;
    }
}
