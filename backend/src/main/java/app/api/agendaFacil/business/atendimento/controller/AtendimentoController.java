package app.api.agendaFacil.business.atendimento.controller;

import app.api.agendaFacil.business.atendimento.DTO.AtendimentoDTO;
import app.api.agendaFacil.business.atendimento.service.AtendimentoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("ALL")
@Path("/atendimento")
public class AtendimentoController extends ControllerManager {

    final AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoService atendimentoService, @HeaderParam("X-Tenant") String tenant) {
        this.atendimentoService = atendimentoService;
        this.atendimentoService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response getById(@PathParam("id") Long pId) {
        return atendimentoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(
            @QueryParam("dataAtendimento") LocalDate dataAtendimento,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("pessoaId") Long pessoaId,
            @QueryParam("usuarioId") Long usuarioId,
            @QueryParam("atividade") String atividade,
            @QueryParam("evolucaoSintomas") String evolucaoSintomas,
            @QueryParam("avaliacao") String avaliacao,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return atendimentoService.count(dataAtendimento, dataInicio, dataFim, pessoaId, usuarioId, atividade, evolucaoSintomas, avaliacao, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("dataAtendimento") LocalDate dataAtendimento,
            @QueryParam("dataInicio") LocalDate dataInicio,
            @QueryParam("dataFim") LocalDate dataFim,
            @QueryParam("pessoaId") Long pessoaId,
            @QueryParam("usuarioId") Long usuarioId,
            @QueryParam("atividade") String atividade,
            @QueryParam("evolucaoSintomas") String evolucaoSintomas,
            @QueryParam("avaliacao") String avaliacao,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return atendimentoService.list(dataAtendimento, dataInicio, dataFim, pessoaId, usuarioId, atividade, evolucaoSintomas, avaliacao, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(@QueryParam("agendamentoId") Long agendamentoId,
                        AtendimentoDTO pAtendimento) {

        return atendimentoService.addAtendimento(pAtendimento, agendamentoId);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(@QueryParam("agendamentoId") Long agendamentoId,
                           AtendimentoDTO pAtendimento) {

        return atendimentoService.updateAtendimento(pAtendimento, agendamentoId);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdAtendimento) {

        return atendimentoService.deleteAtendimento(pListIdAtendimento);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response reactivateList(List<Long> pListIdAtendimento) {

        return atendimentoService.reactivateAtendimento(pListIdAtendimento);
    }
}
