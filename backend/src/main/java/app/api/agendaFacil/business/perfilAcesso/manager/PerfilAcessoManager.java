package app.api.agendaFacil.business.perfilAcesso.manager;

import app.api.agendaFacil.business.perfilAcesso.repository.PerfilAcessoRepository;
import app.core.application.ContextManager;
import app.core.application.DTO.Responses;
import jakarta.ws.rs.core.SecurityContext;

public abstract class PerfilAcessoManager extends ContextManager {

    protected final SecurityContext context;
    protected final PerfilAcessoRepository perfilAcessoRepository;

    protected Responses responses;

    protected PerfilAcessoManager() {
        super();
        this.context = null;
        this.perfilAcessoRepository = null;
    }

    protected PerfilAcessoManager(SecurityContext context, PerfilAcessoRepository perfilAcessoRepository) {
        super(context);
        this.context = context;
        this.perfilAcessoRepository = perfilAcessoRepository;
    }
}
