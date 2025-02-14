package app.api.agendaFacil.management.timeZone.controller;

import app.api.agendaFacil.management.timeZone.DTO.TimeZoneDTO;
import app.api.agendaFacil.management.timeZone.service.TimeZoneService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/timezones")
public class TimeZoneController extends ControllerManager {

    final TimeZoneService timeZoneService;

    public TimeZoneController(TimeZoneService timeZoneService) {
        this.timeZoneService = timeZoneService;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response list() {

        return timeZoneService.list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response get(@PathParam("id") Long id) {

        return timeZoneService.get(id);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin"})
    @Transactional
    public Response create(TimeZoneDTO pTimeZone) {

        return timeZoneService.create(pTimeZone);
    }

    @POST
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin"})
    @Transactional
    public Response createByList(List<TimeZoneDTO> timeZones) {

        return timeZoneService.createByList(timeZones);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin"})
    @Transactional
    public Response update(TimeZoneDTO timeZone) {

        return timeZoneService.update(timeZone);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"admin"})
    @Transactional
    public Response delete(@PathParam("id") List<Long> id) {

        return timeZoneService.delete(id);
    }
}