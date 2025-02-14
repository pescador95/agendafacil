package app.api.agendaFacil.business.configuradorAgendamento.controller;

import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.service.ConfiguradorAgendamentoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.List;

@SuppressWarnings("RestParamTypeInspection")
@Path("/configuradorAgendamento")
public class ConfiguradorAgendamentoController extends ControllerManager {

    final ConfiguradorAgendamentoService configuradorAgendamentoService;

    public ConfiguradorAgendamentoController(ConfiguradorAgendamentoService configuradorAgendamentoService, @HeaderParam("X-Tenant") String tenant) {
        this.configuradorAgendamentoService = configuradorAgendamentoService;
        this.configuradorAgendamentoService.setTenant(tenant);
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response getById(@PathParam("id") Long pId) {

        return configuradorAgendamentoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(
            @QueryParam("nome") String nome,
            @QueryParam("profissionalId") Long profissionalId,
            @QueryParam("organizacaoId") Long organizacaoId,
            @QueryParam("configuradorOrganizacao") Boolean configuradorOrganizacao,
            @QueryParam("horarioInicioManha") LocalTime horarioInicioManha,
            @QueryParam("horarioFimManha") LocalTime horarioFimManha,
            @QueryParam("horarioInicioTarde") LocalTime horarioInicioTarde,
            @QueryParam("horarioFimTarde") LocalTime horarioFimTarde,
            @QueryParam("horarioInicioNoite") LocalTime horarioInicioNoite,
            @QueryParam("horarioFimNoite") LocalTime horarioFimNoite,
            @QueryParam("horaMinutoIntervalo") LocalTime horaMinutoIntervalo,
            @QueryParam("horaMinutoTolerancia") LocalTime horaMinutoTolerancia,
            @QueryParam("agendaManha") Boolean agendaManha,
            @QueryParam("agendaTarde") Boolean agendaTarde,
            @QueryParam("agendaNoite") Boolean agendaNoite,
            @QueryParam("atendeSabado") Boolean atendeSabado,
            @QueryParam("atendeDomingo") Boolean atendeDomingo,
            @QueryParam("agendaSabadoManha") Boolean agendaSabadoManha,
            @QueryParam("agendaSabadoTarde") Boolean agendaSabadoTarde,
            @QueryParam("agendaSabadoNoite") Boolean agendaSabadoNoite,
            @QueryParam("agendaDomingoManha") Boolean agendaDomingoManha,
            @QueryParam("agendaDomingoTarde") Boolean agendaDomingoTarde,
            @QueryParam("agendaDomingoNoite") Boolean agendaDomingoNoite,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorAgendamentoService.count(nome, profissionalId, organizacaoId, configuradorOrganizacao, horarioInicioManha, horarioFimManha, horarioInicioTarde, horarioFimTarde, horarioInicioNoite, horarioFimNoite, horaMinutoIntervalo, horaMinutoTolerancia, agendaManha, agendaTarde, agendaNoite, atendeSabado, atendeDomingo, agendaSabadoManha, agendaSabadoTarde, agendaSabadoNoite, agendaDomingoManha, agendaDomingoTarde, agendaDomingoNoite, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("nome") String nome,
            @QueryParam("profissionalId") Long profissionalId,
            @QueryParam("organizacaoId") Long organizacaoId,
            @QueryParam("configuradorOrganizacao") Boolean configuradorOrganizacao,
            @QueryParam("horarioInicioManha") LocalTime horarioInicioManha,
            @QueryParam("horarioFimManha") LocalTime horarioFimManha,
            @QueryParam("horarioInicioTarde") LocalTime horarioInicioTarde,
            @QueryParam("horarioFimTarde") LocalTime horarioFimTarde,
            @QueryParam("horarioInicioNoite") LocalTime horarioInicioNoite,
            @QueryParam("horarioFimNoite") LocalTime horarioFimNoite,
            @QueryParam("horaMinutoIntervalo") LocalTime horaMinutoIntervalo,
            @QueryParam("horaMinutoTolerancia") LocalTime horaMinutoTolerancia,
            @QueryParam("agendaManha") Boolean agendaManha,
            @QueryParam("agendaTarde") Boolean agendaTarde,
            @QueryParam("agendaNoite") Boolean agendaNoite,
            @QueryParam("atendeSabado") Boolean atendeSabado,
            @QueryParam("atendeDomingo") Boolean atendeDomingo,
            @QueryParam("agendaSabadoManha") Boolean agendaSabadoManha,
            @QueryParam("agendaSabadoTarde") Boolean agendaSabadoTarde,
            @QueryParam("agendaSabadoNoite") Boolean agendaSabadoNoite,
            @QueryParam("agendaDomingoManha") Boolean agendaDomingoManha,
            @QueryParam("agendaDomingoTarde") Boolean agendaDomingoTarde,
            @QueryParam("agendaDomingoNoite") Boolean agendaDomingoNoite,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return configuradorAgendamentoService.list(nome, profissionalId, organizacaoId, configuradorOrganizacao, horarioInicioManha, horarioFimManha, horarioInicioTarde, horarioFimTarde, horarioInicioNoite, horarioFimNoite, horaMinutoIntervalo, horaMinutoTolerancia, agendaManha, agendaTarde, agendaNoite, atendeSabado, atendeDomingo, agendaSabadoManha, agendaSabadoTarde, agendaSabadoNoite, agendaDomingoManha, agendaDomingoTarde, agendaDomingoNoite, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {

        return configuradorAgendamentoService.addConfiguradorAgendamento(pConfiguradorAgendamento);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response update(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {

        return configuradorAgendamentoService.updateConfiguradorAgendamento(pConfiguradorAgendamento);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdConfiguradorAgendamento) {

        return configuradorAgendamentoService.deleteConfiguradorAgendamento(pListIdConfiguradorAgendamento);
    }

}
