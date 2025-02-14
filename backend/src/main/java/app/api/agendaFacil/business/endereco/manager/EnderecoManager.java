package app.api.agendaFacil.business.endereco.manager;

import app.api.agendaFacil.business.endereco.loader.EnderecoLoader;
import app.api.agendaFacil.business.endereco.repository.EnderecoRepository;
import app.api.agendaFacil.business.endereco.validator.EnderecoValidator;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class EnderecoManager extends ContextManager {

    protected final SecurityContext context;
    protected final EnderecoRepository enderecoRepository;
    protected final EnderecoValidator enderecoValidator;
    protected final EnderecoLoader enderecoLoader;

    protected EnderecoManager(SecurityContext context, EnderecoRepository enderecoRepository, EnderecoValidator enderecoValidator, EnderecoLoader enderecoLoader) {
        super(context);
        this.context = context;
        this.enderecoRepository = enderecoRepository;
        this.enderecoValidator = enderecoValidator;
        this.enderecoLoader = enderecoLoader;
    }

    protected EnderecoManager() {
        super();
        this.context = null;
        this.enderecoRepository = null;
        this.enderecoValidator = null;
        this.enderecoLoader = null;
    }
}
