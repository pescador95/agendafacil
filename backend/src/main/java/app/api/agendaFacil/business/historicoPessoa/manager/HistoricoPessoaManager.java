package app.api.agendaFacil.business.historicoPessoa.manager;

import app.api.agendaFacil.business.historicoPessoa.loader.HistoricoPessoaLoader;
import app.api.agendaFacil.business.historicoPessoa.repository.HistoricoPessoaRepository;
import app.api.agendaFacil.business.historicoPessoa.validator.HistoricoPessoaValidator;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class HistoricoPessoaManager extends ContextManager {

    protected final SecurityContext context;
    protected final HistoricoPessoaRepository historicoPessoaRepository;
    protected final HistoricoPessoaValidator historicoPessoaValidator;
    protected final HistoricoPessoaLoader historicoPessoaLoader;
    protected final PessoaLoader pessoaLoader;

    protected HistoricoPessoaManager(HistoricoPessoaRepository historicoPessoaRepository, SecurityContext context, HistoricoPessoaValidator historicoPessoaValidator, HistoricoPessoaLoader historicoPessoaLoader, PessoaLoader pessoaLoader) {
        super(context);
        this.context = context;
        this.historicoPessoaRepository = historicoPessoaRepository;
        this.historicoPessoaValidator = historicoPessoaValidator;
        this.historicoPessoaLoader = historicoPessoaLoader;
        this.pessoaLoader = pessoaLoader;
    }

    protected HistoricoPessoaManager() {
        super();
        this.context = null;
        this.historicoPessoaRepository = null;
        this.historicoPessoaValidator = null;
        this.historicoPessoaLoader = null;
        this.pessoaLoader = null;
    }

}
