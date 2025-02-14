package app.api.agendaFacil.business.organizacao.manager;

import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class OrganizacaoManager extends ContextManager {

    protected final SecurityContext context;
    protected final OrganizacaoRepository organizacaoRepository;
    protected final OrganizacaoLoader organizacaoLoader;

    protected OrganizacaoManager(SecurityContext context, OrganizacaoRepository organizacaoRepository, OrganizacaoLoader organizacaoLoader) {
        super(context);
        this.context = context;
        this.organizacaoRepository = organizacaoRepository;
        this.organizacaoLoader = organizacaoLoader;
    }

    protected OrganizacaoManager() {
        super();
        this.context = null;
        this.organizacaoRepository = null;
        this.organizacaoLoader = null;
    }
}
