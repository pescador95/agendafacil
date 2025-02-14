package app.core.application.tenant;

import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

@RequestScoped
@PersistenceUnitExtension
public class CustomTenantResolver implements TenantResolver {
    @Context
    HttpHeaders headers;

    @Override
    public String getDefaultTenantId() {
        return "config";
    }

    @Override
    public String resolveTenantId() {

        try {

            String tenant = headers.getHeaderString("X-Tenant");

            if (BasicFunctions.isNull(tenant)) {
                tenant = getDefaultTenantId();
            }
            return tenant;
        } catch (Exception e) {
            return getDefaultTenantId();
        }
    }
}