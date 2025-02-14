package app.api.agendaFacil.management.auth.controller;

import app.api.agendaFacil.management.auth.DTO.AuthDTO;
import app.api.agendaFacil.management.auth.service.AuthService;
import app.api.agendaFacil.management.database.redis.RedisService;
import app.core.helpers.trace.exception.ControllerManager;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@RequestScoped
public class AuthController extends ControllerManager {

    final AuthService authService;

    final RedisService redisService;

    @Context
    final HttpHeaders headers;

    public AuthController(AuthService authService, RedisService redisService, HttpHeaders headers, @HeaderParam("X-Tenant") String tenant) {
        this.authService = authService;
        this.redisService = redisService;
        this.headers = headers;
        this.authService.setTenant(tenant);
        this.redisService.setTenant(tenant);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response auth(AuthDTO data) throws ParseException {
        String userAgent = headers.getHeaderString("User-Agent");
        return authService.login(data, userAgent);
    }

    @POST
    @Path("/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response refreshToken(AuthDTO data) {
        String userAgent = headers.getHeaderString("User-Agent");
        return authService.refreshToken(data, userAgent);
    }

    @POST
    @Path("/logout")
    @PermitAll
    public Response logout() {
        String userAgent = headers.getHeaderString("User-Agent");
        return redisService.del(userAgent);
    }

    @POST
    @Path("/flush")
    @RolesAllowed({"admin"})
    public Response flush() {
        return redisService.flushRedis();
    }
}