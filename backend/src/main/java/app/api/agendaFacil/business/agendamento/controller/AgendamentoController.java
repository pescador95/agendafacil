package app.api.agendaFacil.business.agendamento.controller;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.service.AgendamentoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Path("/agendamento")
public class AgendamentoController extends ControllerManager {

    final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService, @HeaderParam("X-Tenant") String tenant) {
        this.agendamentoService = agendamentoService;
        this.agendamentoService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response getById(@PathParam("id") Long pId) {
        return agendamentoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(@QueryParam("dataAgendamento") LocalDate dataAgendamento,
                          @QueryParam("dataInicio") LocalDate dataInicio,
                          @QueryParam("dataFim") LocalDate dataFim,
                          @QueryParam("horarioAgendamento") LocalTime horarioAgendamento,
                          @QueryParam("horarioInicio") LocalTime horarioInicio,
                          @QueryParam("HorarioFim") LocalTime horarioFim,
                          @QueryParam("pessoaId") Long pessoaId,
                          @QueryParam("nomePessoa") String nomePessoa,
                          @QueryParam("nomeProfissional") String nomeProfissional,
                          @QueryParam("idStatus") @DefaultValue("1") Long idStatus,
                          @QueryParam("organizacaoId") Long organizacaoId,
                          @QueryParam("tipoAgendamentoId") Long tipoAgendamentoId,
                          @QueryParam("profissionalId") Long profissionalId,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {
        return agendamentoService.count(dataAgendamento, dataInicio, dataFim, horarioAgendamento, horarioInicio, horarioFim, pessoaId, nomePessoa, nomeProfissional, idStatus, organizacaoId, tipoAgendamentoId, profissionalId, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("dataAgendamento") LocalDate dataAgendamento,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("horarioAgendamento") LocalTime horarioAgendamento,
            @QueryParam("horarioInicio") LocalTime horarioInicio,
            @QueryParam("HorarioFim") LocalTime horarioFim,
            @QueryParam("pessoaId") Long pessoaId,
            @QueryParam("nomePessoa") String nomePessoa,
            @QueryParam("nomeProfissional") String nomeProfissional,
            @QueryParam("idStatus") @DefaultValue("1") Long idStatus,
            @QueryParam("organizacaoId") Long organizacaoId,
            @QueryParam("tipoAgendamentoId") Long tipoAgendamentoId,
            @QueryParam("profissionalId") Long profissionalId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return agendamentoService.list(dataAgendamento, dataInicio, dataFim, horarioAgendamento, horarioInicio, horarioFim, pessoaId, nomePessoa, nomeProfissional, idStatus, organizacaoId, tipoAgendamentoId, profissionalId, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(AgendamentoDTO pAgendamentoDTO,
                        @QueryParam("reagendar") @DefaultValue("false") Boolean reagendar) {

        return agendamentoService.addAgendamento(pAgendamentoDTO, reagendar);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response update(AgendamentoDTO pAgendamentoDTO,
                           @QueryParam("reagendar") @DefaultValue("false") Boolean reagendar) {

        return agendamentoService.updateAgendamento(pAgendamentoDTO, reagendar);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdAgendamento) {

        return agendamentoService.deleteAgendamento(pListIdAgendamento);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response reactivateList(List<Long> pListIdAgendamento) {

        return agendamentoService.reactivateAgendamento(pListIdAgendamento);
    }
}
