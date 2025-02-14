package app.api.agendaFacil.business.usuario.controller;

import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.usuario.DTO.CopyUserDTO;
import app.api.agendaFacil.business.usuario.service.UsuarioService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("RestParamTypeInspection")
@Path("/usuario")
public class UsuarioController extends ControllerManager {

    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, @HeaderParam("X-Tenant") String tenant) {
        this.usuarioService = usuarioService;
        this.usuarioService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response getById(@PathParam("id") Long pId) {

        return usuarioService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(@QueryParam("id") Long id,
                          @QueryParam("login") String login,
                          @QueryParam("pessoaId") Long pessoaId,
                          @QueryParam("organizacaoDefaultId") Long organizacaoDefaultId,
                          @QueryParam("roleId") List<Long> roleId,
                          @QueryParam("nomeprofissional") String nomeprofissional,
                          @QueryParam("organizacaoId") List<Long> organizacaoId,
                          @QueryParam("tipoAgendamentoId") List<Long> tipoAgendamentoId,
                          @QueryParam("bot") Boolean bot,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return usuarioService.count(id, login, pessoaId, organizacaoDefaultId, roleId, nomeprofissional, organizacaoId, tipoAgendamentoId, bot, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("login") String login,
            @QueryParam("pessoaId") Long pessoaId,
            @QueryParam("organizacaoDefaultId") Long organizacaoDefaultId,
            @QueryParam("roleId") List<Long> roleId,
            @QueryParam("nomeprofissional") String nomeprofissional,
            @QueryParam("organizacaoId") List<Long> organizacaoId,
            @QueryParam("tipoAgendamentoId") List<Long> tipoAgendamentoId,
            @QueryParam("bot") Boolean bot,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return usuarioService.list(id, login, pessoaId, organizacaoDefaultId, roleId, nomeprofissional, organizacaoId, tipoAgendamentoId, bot, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/bot")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listForScheduler(
            @QueryParam("dataAgendamento") @DefaultValue("1970-01-01") LocalDate dataAgendamento,
            @QueryParam("organizacao") @DefaultValue("1") Long organizaao,
            @QueryParam("tipoAgendamento") @DefaultValue("1") Long tipoAgendamento,
            @QueryParam("profissional") @DefaultValue("1") Long profissional,
            @QueryParam("comPreferencia") @DefaultValue("true") boolean comPreferencia) {

        return usuarioService.listForScheduler(dataAgendamento, organizaao, tipoAgendamento, profissional, comPreferencia);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(EntidadeDTO entity) {

        return usuarioService.addUser(entity);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(EntidadeDTO entity) {

        return usuarioService.updateUser(entity);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdUsuario) {

        return usuarioService.deleteUser(pListIdUsuario);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response reactivateList(List<Long> pListIdUsuario) {

        return usuarioService.reactivateUser(pListIdUsuario);
    }

    @POST
    @Path("/copy")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response copy(CopyUserDTO entity) {

        return usuarioService.copyUser(entity);
    }
}
