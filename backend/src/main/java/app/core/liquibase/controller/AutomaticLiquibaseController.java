package app.core.liquibase.controller;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.core.helpers.trace.exception.ControllerManager;
import app.core.liquibase.service.LiquibaseService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import liquibase.exception.LiquibaseException;

@Path("/automaticLiquibase")
@RequestScoped
public class AutomaticLiquibaseController extends ControllerManager {

    final LiquibaseService liquibaseService;

    public AutomaticLiquibaseController(LiquibaseService liquibaseService, @HeaderParam("X-Tenant") String tenant) {
        this.liquibaseService = liquibaseService;
        this.liquibaseService.setTenant(tenant);
    }

    @POST
    @Path("/createAutomatic")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response createAutomaticMigrations(ContratoDTO contrato) {

        return liquibaseService.copyCreateAndExecuteMigrationsByContrato(contrato);
    }

    @POST
    @Path("/copy")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response copyAutomaticMigrations() throws LiquibaseException {

        return liquibaseService.copyNewMigrationsAndRun();
    }

    @POST
    @Path("/executeAutomatic")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response executeAutomaticMigrations(@QueryParam("dropAll") @DefaultValue("false") Boolean dropAll,
                                               @QueryParam("validate") @DefaultValue("true") Boolean validate,
                                               @QueryParam("update") @DefaultValue("true") Boolean update) throws LiquibaseException {

        return liquibaseService.executeMigration(dropAll, validate, update);
    }
}
