package app.core.health;

import app.core.helpers.utils.Info;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/health")
public class HealthController {

    @GET
    @Path("/")
    @PermitAll
    @Produces("text/plain")
    public String health() {
        return Info.projectInfo(true);
    }
}
