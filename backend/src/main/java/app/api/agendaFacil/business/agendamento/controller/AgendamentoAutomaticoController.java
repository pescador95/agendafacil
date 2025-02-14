package app.api.agendaFacil.business.agendamento.controller;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.service.AgendamentoAutomaticoService;
import app.api.agendaFacil.business.agendamento.service.AgendamentoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/agendamento")
public class AgendamentoAutomaticoController extends ControllerManager {

    final AgendamentoService agendamentoService;
    final AgendamentoAutomaticoService agendamentoAutomaticoService;

    public AgendamentoAutomaticoController(AgendamentoService agendamentoService, AgendamentoAutomaticoService agendamentoAutomaticoService, @HeaderParam("X-Tenant") String tenant) {
        this.agendamentoService = agendamentoService;
        this.agendamentoAutomaticoService = agendamentoAutomaticoService;
        this.agendamentoService.setTenant(tenant);
        this.agendamentoAutomaticoService.setTenant(tenant);
    }

    @POST
    @Path("/bot/listar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listFreeAppointmentByUsuario(@QueryParam("reagendar") @DefaultValue("false") Boolean reagendar, AgendamentoDTO pAgendamento) {

        return agendamentoAutomaticoService.listAgendamentosLivres(pAgendamento, reagendar);
    }

    @POST
    @Path("/bot/listar/meusAgendamentos")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listAppointmentsByPessoa(@QueryParam("reagendar") @DefaultValue("false") Boolean reagendar, AgendamentoDTO pAgendamento) {

        return agendamentoService.listAgendamentosByPessoa(pAgendamento, reagendar);
    }

    @POST
    @Path("/bot/marcar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response schedule(AgendamentoDTO pAgendamento) {

        return agendamentoService.marcarAgendamento(pAgendamento);
    }


    @POST
    @Path("/bot/remarcar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response reschedule(List<AgendamentoDTO> pListAgendamento) {

        return agendamentoService.remarcarAgendamento(pListAgendamento);
    }

    @POST
    @Path("/bot/verificarData")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response checkDate(AgendamentoDTO pAgendamento,
                              @QueryParam("reagendar") @DefaultValue("false") Boolean reagendar) {

        return agendamentoService.checkDataValida(pAgendamento, reagendar);
    }

    @DELETE
    @Path("/bot/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdAgendamento) {

        return agendamentoService.deleteAgendamento(pListIdAgendamento);
    }
}
