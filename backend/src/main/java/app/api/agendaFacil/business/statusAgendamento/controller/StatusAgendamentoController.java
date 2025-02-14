package app.api.agendaFacil.business.statusAgendamento.controller;

import app.api.agendaFacil.business.statusAgendamento.DTO.StatusAgendamentoDTO;
import app.api.agendaFacil.business.statusAgendamento.service.StatusAgendamentoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/statusAgendamento")
public class StatusAgendamentoController extends ControllerManager {

    final StatusAgendamentoService statusAgendamentoService;

    public StatusAgendamentoController(StatusAgendamentoService statusAgendamentoService, @HeaderParam("X-Tenant") String tenant) {
        this.statusAgendamentoService = statusAgendamentoService;
        this.statusAgendamentoService.setTenant(tenant);
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response getById(@PathParam("id") Long pId) {
        return statusAgendamentoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(@QueryParam("id") Long id,
                          @QueryParam("status") String status,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        return statusAgendamentoService.count(id, status, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("status") String status,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return statusAgendamentoService.list(id, status, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(StatusAgendamentoDTO pStatusAgendamento) {

        return statusAgendamentoService.addStatusAgendamento(pStatusAgendamento);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response update(StatusAgendamentoDTO pStatusAgendamento) {

        return statusAgendamentoService.updateStatusAgendamento(pStatusAgendamento);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdStatusAgendamento) {

        return statusAgendamentoService.deleteStatusAgendamento(pListIdStatusAgendamento);
    }
}
