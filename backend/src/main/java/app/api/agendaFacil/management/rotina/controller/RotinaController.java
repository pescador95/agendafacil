package app.api.agendaFacil.management.rotina.controller;

import app.api.agendaFacil.management.rotina.DTO.RotinaDTO;
import app.api.agendaFacil.management.rotina.service.RotinaService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/rotina")
public class RotinaController extends ControllerManager {

    final RotinaService rotinaService;

    public RotinaController(RotinaService rotinaService, @HeaderParam("X-Tenant") String tenant) {
        this.rotinaService = rotinaService;
        this.rotinaService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response getById(@PathParam("id") Long pId) {

        return rotinaService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})

    public Response count(
            @QueryParam("id") Long id,
            @QueryParam("nome") String nome,
            @QueryParam("icon") String icon,
            @QueryParam("path") String path,
            @QueryParam("titulo") String titulo,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder
    ) {

        return rotinaService.count(id, nome, icon, path, titulo, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("nome") String nome,
            @QueryParam("icon") String icon,
            @QueryParam("path") String path,
            @QueryParam("titulo") String titulo,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return rotinaService.list(id, nome, icon, path, titulo, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(RotinaDTO pRotina) {

        return rotinaService.addRotina(pRotina);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(RotinaDTO pRotina) {

        return rotinaService.updateRotina(pRotina);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListRotina) {

        return rotinaService.deleteRotina(pListRotina);
    }

}
