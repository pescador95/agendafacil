package app.core.application.tenant;

import app.core.application.ContextManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public abstract class TenantManager extends ContextManager {

    protected final TenantRepository tenantRepository;

    protected TenantManager(TenantRepository tenantRepository) {
        super();
        this.tenantRepository = tenantRepository;
    }

    public TenantManager() {
        super();
        this.tenantRepository = null;
    }
}
