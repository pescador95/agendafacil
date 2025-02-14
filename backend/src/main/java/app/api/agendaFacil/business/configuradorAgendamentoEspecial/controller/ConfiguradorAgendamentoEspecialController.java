package app.api.agendaFacil.business.configuradorAgendamentoEspecial.controller;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.service.ConfiguradorAgendamentoEspecialService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("RestParamTypeInspection")
@Path("/configuradorAgendamentoEspecial")
public class ConfiguradorAgendamentoEspecialController extends ControllerManager {


    final ConfiguradorAgendamentoEspecialService configuradorAgendamentoEspecialService;

    public ConfiguradorAgendamentoEspecialController(ConfiguradorAgendamentoEspecialService configuradorAgendamentoEspecialService, @HeaderParam("X-Tenant") String tenant) {
        this.configuradorAgendamentoEspecialService = configuradorAgendamentoEspecialService;
        this.configuradorAgendamentoEspecialService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response getById(@PathParam("id") Long pId) {

        return configuradorAgendamentoEspecialService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(
            @QueryParam("nome") String nome,
            @QueryParam("profissionalId") Long profissionalId,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("organizacaoId") Long organizacaoId,
            @QueryParam("tipoAgendamentoId") Long tipoAgendamentoId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorAgendamentoEspecialService.count(nome, profissionalId, tipoAgendamentoId, dataInicio, dataFim, organizacaoId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("nome") String nome,
            @QueryParam("profissionalId") Long profissionalId,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("organizacaoId") Long organizacaoId,
            @QueryParam("tipoAgendamentoId") Long tipoAgendamentoId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorAgendamentoEspecialService.list(nome, profissionalId, tipoAgendamentoId, dataInicio, dataFim, organizacaoId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {

        return configuradorAgendamentoEspecialService.addConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response update(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {

        return configuradorAgendamentoEspecialService.updateConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdConfiguradorAgendamento) {

        return configuradorAgendamentoEspecialService.deleteConfiguradorAgendamentoEspecial(pListIdConfiguradorAgendamento);
    }

}
