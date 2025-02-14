package app.api.agendaFacil.business.endereco.controller;

import app.api.agendaFacil.business.endereco.DTO.EnderecoDTO;
import app.api.agendaFacil.business.endereco.service.EnderecoService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/endereco")
public class EnderecoController extends ControllerManager {

    final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService, @HeaderParam("X-Tenant") String tenant) {
        this.enderecoService = enderecoService;
        this.enderecoService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response getById(@PathParam("id") Long pId) {

        return enderecoService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(@QueryParam("cep") String cep,
                          @QueryParam("logradouro") String logradouro,
                          @QueryParam("numero") Long numero,
                          @QueryParam("complemento") String complemento,
                          @QueryParam("cidade") String cidade,
                          @QueryParam("estado") String estado,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return enderecoService.count(cep, logradouro, numero, complemento, cidade, estado, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("cep") String cep,
            @QueryParam("logradouro") String logradouro,
            @QueryParam("numero") Long numero,
            @QueryParam("complemento") String complemento,
            @QueryParam("cidade") String cidade,
            @QueryParam("estado") String estado,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return enderecoService.list(cep, logradouro, numero, complemento, cidade, estado, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(EnderecoDTO pEndereco) {

        return enderecoService.addEndereco(pEndereco);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(EnderecoDTO pEndereco) {

        return enderecoService.updateEndereco(pEndereco);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListEndereco) {

        return enderecoService.deleteEndereco(pListEndereco);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response reactivateList(List<Long> pListEndereco) {

        return enderecoService.reactivateEndereco(pListEndereco);
    }
}
