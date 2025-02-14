package app.api.agendaFacil.business.configuradorAusencia.controller;

import app.api.agendaFacil.business.configuradorAusencia.DTO.ConfiguradorAusenciaDTO;
import app.api.agendaFacil.business.configuradorAusencia.service.ConfiguradorAusenciaService;
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
@Path("/configuradorAusencia")
public class ConfiguradorAusenciaController extends ControllerManager {

    final ConfiguradorAusenciaService configuradorAusenciaService;

    public ConfiguradorAusenciaController(ConfiguradorAusenciaService configuradorAusenciaService, @HeaderParam("X-Tenant") String tenant) {
        this.configuradorAusenciaService = configuradorAusenciaService;
        this.configuradorAusenciaService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response getById(@PathParam("id") Long pId) {

        return configuradorAusenciaService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(
            @QueryParam("nomeAusencia") String nomeAusencia,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("horaInicio") LocalTime horaInicio,
            @QueryParam("horaFim") LocalTime horaFim,
            @QueryParam("usuarioId") Long usuarioId,
            @QueryParam("observacao") String observacao,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorAusenciaService.count(nomeAusencia, dataInicio, dataFim, horaInicio, horaFim, usuarioId, observacao, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("nomeAusencia") String nomeAusencia,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("horaInicio") LocalTime horaInicio,
            @QueryParam("horaFim") LocalTime horaFim,
            @QueryParam("usuarioId") Long usuarioId,
            @QueryParam("observacao") String observacao,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorAusenciaService.list(nomeAusencia, dataInicio, dataFim, horaInicio, horaFim, usuarioId, observacao, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        return configuradorAusenciaService.addConfiguradorAusencia(pConfiguradorAusencia);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response update(ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        return configuradorAusenciaService.updateConfiguradorAusencia(pConfiguradorAusencia);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdConfiguradorAusencia) {

        return configuradorAusenciaService.deleteConfiguradorAusencia(pListIdConfiguradorAusencia);
    }
}
