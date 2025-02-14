package app.api.agendaFacil.management.scheduler.controller;

import app.api.agendaFacil.business.lembrete.service.LembreteServices;
import app.api.agendaFacil.management.scheduler.service.SchedulerServices;
import app.core.application.DTO.Responses;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/scheduler")
public class SchedulerController extends ControllerManager {

    final SchedulerServices schedulerServices;

    final LembreteServices lembreteServices;

    Responses responses = new Responses();

    public SchedulerController(SchedulerServices schedulerServices, LembreteServices lembreteServices, @HeaderParam("X-Tenant") String tenant) {
        this.schedulerServices = schedulerServices;
        this.lembreteServices = lembreteServices;
        this.schedulerServices.setTenant(tenant);
        this.lembreteServices.setTenant(tenant);
    }

    @POST
    @Path("/enviarLembrete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response enviarLembrete(@QueryParam("mensagem") String mensagem,
                                   @QueryParam("whatsppId") Long whatsppId,
                                   @QueryParam("telegramId") Long telegramId) {

        return lembreteServices.enviarLembrete(mensagem, whatsppId, telegramId);
    }

    @POST
    @Path("/execute")
    @PermitAll
    @Transactional
    public Response execute(@HeaderParam("X-Tenant") String tenant) {

        try {

            this.schedulerServices.setTenant(tenant);
            this.lembreteServices.setTenant(tenant);

            responses = new Responses(200, "Scheduler executado com sucesso");

            schedulerServices.verifyAndRunScheduler(tenant);

        } catch (Exception e) {
            responses = new Responses(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }
}
