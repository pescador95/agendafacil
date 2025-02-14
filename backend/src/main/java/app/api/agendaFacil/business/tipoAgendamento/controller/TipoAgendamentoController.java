package app.api.agendaFacil.business.tipoAgendamento.controller;

import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.tipoAgendamento.service.TipoAgendamentoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/tipoAgendamento")
public class TipoAgendamentoController extends ControllerManager {

    final TipoAgendamentoService tipoAgendamentoService;

    public TipoAgendamentoController(TipoAgendamentoService tipoAgendamentoService) {
        this.tipoAgendamentoService = tipoAgendamentoService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response getById(@PathParam("id") Long pId) {

        return tipoAgendamentoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(@QueryParam("id") Long id,
                          @QueryParam("tipoAgendamento") String tipoAgendamento,
                          @QueryParam("organizacaoId") List<Long> organizacaoId,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return tipoAgendamentoService.count(id, tipoAgendamento, organizacaoId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("tipoAgendamento") String tipoAgendamento,
            @QueryParam("organizacaoId") List<Long> organizacaoId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return tipoAgendamentoService.list(id, tipoAgendamento, organizacaoId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/bot")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listByScheduler(@QueryParam("organizacoes") @NotNull List<Long> organizacoes) {

        return tipoAgendamentoService.listByScheduler(organizacoes);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(TipoAgendamentoDTO pTipoAgendamento) {

        return tipoAgendamentoService.addTipoAgendamento(pTipoAgendamento);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response update(TipoAgendamentoDTO pTipoAgendamento) {

        return tipoAgendamentoService.updateTipoAgendamento(pTipoAgendamento);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdTipoAgendamento) {

        return tipoAgendamentoService.deleteTipoAgendamento(pListIdTipoAgendamento);
    }
}
