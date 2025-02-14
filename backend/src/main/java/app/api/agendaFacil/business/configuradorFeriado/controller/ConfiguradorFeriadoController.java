package app.api.agendaFacil.business.configuradorFeriado.controller;

import app.api.agendaFacil.business.configuradorFeriado.DTO.ConfiguradorFeriadoDTO;
import app.api.agendaFacil.business.configuradorFeriado.service.ConfiguradorFeriadoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SuppressWarnings("RestParamTypeInspection")
@Path("/configuradorFeriado")
public class ConfiguradorFeriadoController extends ControllerManager {

    final ConfiguradorFeriadoService configuradorFeriadoService;

    public ConfiguradorFeriadoController(ConfiguradorFeriadoService configuradorFeriadoService, @HeaderParam("X-Tenant") String tenant) {
        this.configuradorFeriadoService = configuradorFeriadoService;
        this.configuradorFeriadoService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response getById(@PathParam("id") Long pId) {

        return configuradorFeriadoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(
            @QueryParam("nomeFeriado") String nomeFeriado,
            @QueryParam("dataFeriado") LocalDate dataFeriado,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("horaInicio") LocalTime horaInicio,
            @QueryParam("horaFim") LocalTime horaFim,
            @QueryParam("organizacaoId") Long organizacaoId,
            @QueryParam("observacao") String observacao,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorFeriadoService.count(nomeFeriado, dataFeriado, dataInicio, dataFim, horaInicio, horaFim, organizacaoId, observacao, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("nomeFeriado") String nomeFeriado,
            @QueryParam("dataFeriado") LocalDate dataFeriado,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("horaInicio") LocalTime horaInicio,
            @QueryParam("horaFim") LocalTime horaFim,
            @QueryParam("organizacaoId") Long organizacaoId,
            @QueryParam("observacao") String observacao,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorFeriadoService.list(nomeFeriado, dataFeriado, dataInicio, dataFim, horaInicio, horaFim, organizacaoId, observacao, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        return configuradorFeriadoService.addConfiguradorFeriado(pConfiguradorFeriado);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response update(ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        return configuradorFeriadoService.updateConfiguradorFeriado(pConfiguradorFeriado);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdConfiguradorFeriado) {

        return configuradorFeriadoService.deleteConfiguradorFeriado(pListIdConfiguradorFeriado);
    }

}
