package app.core.liquibase.controller;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.core.helpers.trace.exception.ControllerManager;
import app.core.liquibase.service.LiquibaseService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import liquibase.exception.LiquibaseException;

@Path("/liquibase")
public class LiquibaseController extends ControllerManager {

    final LiquibaseService liquibaseService;

    public LiquibaseController(LiquibaseService liquibaseService, @HeaderParam("X-Tenant") String tenant) {
        this.liquibaseService = liquibaseService;
        this.liquibaseService.setTenant(tenant);
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin", "usuario"})
    public Response createMigrationsWithAuth(ContratoDTO contrato) {

        return liquibaseService.copyCreateAndExecuteMigrationsByContrato(contrato);
    }


    @POST
    @Path("/copy")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin", "usuario"})
    public Response copyMigrationsWithAuth() throws LiquibaseException {

        return liquibaseService.copyNewMigrationsAndRun();
    }


    @POST
    @Path("/execute")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin", "usuario"})
    public Response executeMigrationsWithAuth(@QueryParam("dropAll") @DefaultValue("false") Boolean dropAll,
                                              @QueryParam("validate") @DefaultValue("true") Boolean validate,
                                              @QueryParam("update") @DefaultValue("true") Boolean update) throws LiquibaseException {

        return liquibaseService.executeMigration(dropAll, validate, update);
    }
}
