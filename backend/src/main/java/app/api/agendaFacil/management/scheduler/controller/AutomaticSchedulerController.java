package app.api.agendaFacil.management.scheduler.controller;

import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/schedulerServices")
@RequestScoped
public class AutomaticSchedulerController extends ControllerManager {

    final SchedulerController schedulerController;

    public AutomaticSchedulerController(SchedulerController schedulerController) {
        this.schedulerController = schedulerController;
    }

    @POST
    @Path("/enviarLembrete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response enviarLembrete(@QueryParam("mensagem") String mensagem,
                                   @QueryParam("whatsppId") Long whatsppId,
                                   @QueryParam("telegramId") Long telegramId) {

        return schedulerController.enviarLembrete(mensagem, whatsppId, telegramId);
    }

    @POST
    @Path("/execute")
    @PermitAll
    @Transactional
    public Response execute(@HeaderParam("X-Tenant") String tenant) {
        return schedulerController.execute(tenant);
    }

}
