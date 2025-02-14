package app.api.agendaFacil.management.recoveryPassword.controller;

import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.management.auth.DTO.AuthDTO;
import app.api.agendaFacil.management.recoveryPassword.service.RecoveryPasswordService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/recoverPassword")

public class RecoveryPasswordController extends ControllerManager {

    final RecoveryPasswordService recoveryPasswordService;

    public RecoveryPasswordController(RecoveryPasswordService recoveryPasswordService, @HeaderParam("X-Tenant") String tenant) {
        this.recoveryPasswordService = recoveryPasswordService;
        this.recoveryPasswordService.setTenant(tenant);
    }

    @POST
    @Path("{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response sendMail(@PathParam("login") String login) {

        return recoveryPasswordService.sendEmail(login);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response update(AuthDTO authDTO) {

        Usuario pUsuario = new Usuario(authDTO);

        return recoveryPasswordService.updatePassword(pUsuario);
    }

    @POST
    @Path("/crypt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin"})
    public Response returnCryptPassword(@QueryParam("password") String password) {

        return recoveryPasswordService.returnCryptPassword(password);
    }
}