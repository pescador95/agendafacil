package app.api.agendaFacil.business.contrato.controller;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.api.agendaFacil.business.contrato.service.ContratoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("RestParamTypeInspection")
@Path("/contrato")
public class ContratoController extends ControllerManager {

    final ContratoService contratoService;

    public ContratoController(ContratoService contratoService, @HeaderParam("X-Tenant") String tenant) {
        this.contratoService = contratoService;
        this.contratoService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})

    public Response getById(@PathParam("id") Long pId) {

        return contratoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response count(@QueryParam("tenant") String tenant,
                          @QueryParam("tipoContrato") Long tipoContrato,
                          @QueryParam("numeroMaximoSessoes") Integer numeroMaximoSessoes,
                          @QueryParam("consideracoes") String consideracoes,
                          @QueryParam("dataContrato") LocalDate dataContrato,
                          @QueryParam("dataTermino") LocalDate dataTermino,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return contratoService.count(tenant, tipoContrato, numeroMaximoSessoes, consideracoes, dataContrato, dataTermino, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response list(
            @QueryParam("tenant") String tenant,
            @QueryParam("tipoContrato") Long tipoContrato,
            @QueryParam("numeroMaximoSessoes") Integer numeroMaximoSessoes,
            @QueryParam("consideracoes") String consideracoes,
            @QueryParam("dataContrato") LocalDate dataContrato,
            @QueryParam("dataTermino") LocalDate dataTermino,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return contratoService.list(tenant, tipoContrato, numeroMaximoSessoes, consideracoes, dataContrato, dataTermino, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response add(ContratoDTO pContrato) {

        return contratoService.addContrato(pContrato);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})

    public Response update(ContratoDTO pContrato) {

        return contratoService.updateContrato(pContrato);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response deleteList(List<Long> pListIdContrato) {

        return contratoService.deleteContrato(pListIdContrato);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response reactivateList(List<Long> pListIdContrato) {

        return contratoService.reactivateContrato(pListIdContrato);
    }
}
