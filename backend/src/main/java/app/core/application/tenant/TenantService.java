package app.core.application.tenant;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
@Transactional
public class TenantService extends TenantManager {

    public TenantService() {
        super();
    }

    @Inject
    protected TenantService(TenantRepository tenantRepository) {
        super(tenantRepository);
    }

    public List<Tenant> createAndGetTenants() {
        tenantRepository.insertTenantFromContrato();
        return tenantRepository.listAllTenantsByContratoAtivo();
    }
}
