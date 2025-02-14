package app.api.agendaFacil.business.organizacao.controller;

import app.api.agendaFacil.business.organizacao.service.OrganizacaoService;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/organizacao")
public class OrganizacaoController extends ControllerManager {

    OrganizacaoService organizacaoService;

    public OrganizacaoController(OrganizacaoService organizacaoService, @HeaderParam("X-Tenant") String tenant) {
        this.organizacaoService = organizacaoService;
        this.organizacaoService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})

    public Response getById(@PathParam("id") Long pId) {

        return organizacaoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response count(@QueryParam("nome") String nome,
                          @QueryParam("cnpj") String cnpj,
                          @QueryParam("telefone") String telefone,
                          @QueryParam("celular") String celular,
                          @QueryParam("email") String email,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return organizacaoService.count(nome, cnpj, telefone, celular, email, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response list(
            @QueryParam("nome") String nome,
            @QueryParam("cnpj") String cnpj,
            @QueryParam("telefone") String telefone,
            @QueryParam("celular") String celular,
            @QueryParam("email") String email,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return organizacaoService.list(nome, cnpj, telefone, celular, email, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/bot/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listByBot(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                              @QueryParam("page") @DefaultValue("1") int pageIndex,
                              @QueryParam("size") @DefaultValue("20") int pageSize,
                              @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                              @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return organizacaoService.listByBot(sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response add(EntidadeDTO entity) {

        return organizacaoService.addOrganizacao(entity);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})

    public Response update(EntidadeDTO entity) {

        return organizacaoService.updateOrganizacao(entity);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response deleteList(List<Long> pListIdOrganizacao) {

        return organizacaoService.deleteOrganizacao(pListIdOrganizacao);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response reactivateList(List<Long> pListIdOrganizacao) {

        return organizacaoService.reactivateOrganizacao(pListIdOrganizacao);
    }
}
