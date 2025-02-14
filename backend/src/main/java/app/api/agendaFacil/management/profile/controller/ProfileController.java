package app.api.agendaFacil.management.profile.controller;

import app.api.agendaFacil.management.profile.entity.MultiPartFormData;
import app.api.agendaFacil.management.profile.service.ProfileService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

@Path("/uploads")
@Produces({MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
@Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})

@Transactional
public class ProfileController extends ControllerManager {

    final ProfileService profileService;

    public ProfileController(ProfileService profileService, @HeaderParam("X-Tenant") String tenant) {
        this.profileService = profileService;
        this.profileService.setTenant(tenant);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response count() {

        return profileService.count();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response listUploads(@QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                                @QueryParam("page") @DefaultValue("1") int pageIndex,
                                @QueryParam("size") @DefaultValue("10") int pageSize,
                                @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return profileService.listUploads(sortQuery, pageIndex, pageSize, strgOrder);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response findOne(@PathParam("id") Long id) {

        return profileService.get(id);
    }

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    public Response sendUpload(MultiPartFormData file, @QueryParam("fileReference") String fileReference,
                               @QueryParam("idHistoricoPessoa") Long idHistoricoPessoa) throws IOException {

        return profileService.sendUpload(file, fileReference, idHistoricoPessoa);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response removeUpload(List<Long> pListIdProfile) {

        return profileService.removeUpload(pListIdProfile);
    }
}