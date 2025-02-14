package app.api.agendaFacil.business.perfilAcesso.controller;

import app.api.agendaFacil.business.perfilAcesso.DTO.PerfilAcessoDTO;
import app.api.agendaFacil.business.perfilAcesso.service.PerfilAcessoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/perfilAcesso")
public class PerfilAcessoController extends ControllerManager {

    final PerfilAcessoService perfilAcessoService;

    public PerfilAcessoController(PerfilAcessoService perfilAcessoService, @HeaderParam("X-Tenant") String tenant) {
        this.perfilAcessoService = perfilAcessoService;
        this.perfilAcessoService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response getById(@PathParam("id") Long pId) {

        return perfilAcessoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response count(@QueryParam("id") Long id,
                          @QueryParam("nome") String nome,
                          @QueryParam("criar") Boolean criar,
                          @QueryParam("ler") Boolean ler,
                          @QueryParam("atualizar") Boolean atualizar,
                          @QueryParam("apagar") Boolean apagar,
                          @QueryParam("usuarioId") Long usuarioId,
                          @QueryParam("rotinaId") List<Long> rotinaId,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return perfilAcessoService.count(id, nome, criar, ler, atualizar, apagar, usuarioId, rotinaId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("nome") String nome,
            @QueryParam("criar") Boolean criar,
            @QueryParam("ler") Boolean ler,
            @QueryParam("atualizar") Boolean atualizar,
            @QueryParam("apagar") Boolean apagar,
            @QueryParam("usuarioId") Long usuarioId,
            @QueryParam("rotinaId") List<Long> rotinaId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return perfilAcessoService.list(id, nome, criar, ler, atualizar, apagar, usuarioId, rotinaId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(PerfilAcessoDTO pPerfilAcesso) {

        return perfilAcessoService.addPerfilAcesso(pPerfilAcesso);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(PerfilAcessoDTO pPerfilAcesso) {

        return perfilAcessoService.updatePerfilAcesso(pPerfilAcesso);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListPerfilAcesso) {

        return perfilAcessoService.deletePerfilAcesso(pListPerfilAcesso);
    }

}
