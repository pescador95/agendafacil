package app.api.agendaFacil.business.historicoPessoa.controller;

import app.api.agendaFacil.business.historicoPessoa.DTO.HistoricoPessoaDTO;
import app.api.agendaFacil.business.historicoPessoa.service.HistoricoPessoaService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/historicoPessoa")
public class HistoricoPessoaController extends ControllerManager {

    final HistoricoPessoaService historicoPessoaService;

    public HistoricoPessoaController(HistoricoPessoaService historicoPessoaService, @HeaderParam("X-Tenant") String tenant) {
        this.historicoPessoaService = historicoPessoaService;
        this.historicoPessoaService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response getById(@PathParam("id") Long pId) {

        return historicoPessoaService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(@QueryParam("id") Long id,
                          @QueryParam("queixaPrincipal") String queixaPrincipal,
                          @QueryParam("medicamentos") String medicamentos,
                          @QueryParam("diagnosticoClinico") String diagnosticoClinico,
                          @QueryParam("comorbidades") String comorbidades,
                          @QueryParam("ocupacao") String ocupacao,
                          @QueryParam("responsavelContato") String responsavelContato,
                          @QueryParam("nomePessoa") String nomePessoa,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return historicoPessoaService.count(id, queixaPrincipal, medicamentos, diagnosticoClinico, comorbidades, ocupacao, responsavelContato, nomePessoa, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("queixaPrincipal") String queixaPrincipal,
            @QueryParam("medicamentos") String medicamentos,
            @QueryParam("diagnosticoClinico") String diagnosticoClinico,
            @QueryParam("comorbidades") String comorbidades,
            @QueryParam("ocupacao") String ocupacao,
            @QueryParam("responsavelContato") String responsavelContato,
            @QueryParam("nomePessoa") String nomePessoa,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return historicoPessoaService.list(id, queixaPrincipal, medicamentos, diagnosticoClinico, comorbidades, ocupacao, responsavelContato, nomePessoa, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(HistoricoPessoaDTO entity) {

        return historicoPessoaService.addHistoricoPessoa(entity);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(HistoricoPessoaDTO entity) {

        return historicoPessoaService.updateHistoricoPessoa(entity);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdHistoricoPessoa) {

        return historicoPessoaService.deleteHistoricoPessoa(pListIdHistoricoPessoa);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response reactivateList(List<Long> pListIdPessoa) {

        return historicoPessoaService.reactivateHistoricoPessoa(pListIdPessoa);
    }
}
