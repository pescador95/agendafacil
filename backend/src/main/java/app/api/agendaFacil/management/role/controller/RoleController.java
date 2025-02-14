package app.api.agendaFacil.management.role.controller;


import app.api.agendaFacil.management.role.DTO.RoleDTO;
import app.api.agendaFacil.management.role.service.RoleService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Path("/role")
public class RoleController extends ControllerManager {

    final RoleService roleService;

    public RoleController(RoleService roleService, @HeaderParam("X-Tenant") String tenant) {
        this.roleService = roleService;
        this.roleService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response getById(@PathParam("id") Long pId) {

        return roleService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count(
            @QueryParam("id") Long id,
            @QueryParam("nome") String nome,
            @QueryParam("privilegio") String privilegio,
            @QueryParam("admin") Boolean admin,
            @QueryParam("usuarioId") Long usuarioId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder
    ) {

        return roleService.count(id, nome, privilegio, admin, usuarioId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("nome") String nome,
            @QueryParam("privilegio") String privilegio,
            @QueryParam("admin") Boolean admin,
            @QueryParam("usuarioId") Long usuarioId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return roleService.list(id, nome, privilegio, admin, usuarioId, sortQuery, pageIndex, pageSize, strgOrder);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(RoleDTO pRole) {

        return roleService.addRole(pRole);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(RoleDTO pRole) {

        return roleService.updateRole(pRole);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> pListIdRole) {

        return roleService.deleteRole(pListIdRole);
    }
}
