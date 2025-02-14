package app.api.agendaFacil.business.pessoa.controller;

import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.service.PessoaService;
import app.core.helpers.trace.exception.ControllerManager;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;


@Path("/pessoa")
public class PessoaController extends ControllerManager {

    final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService, @HeaderParam("X-Tenant") String tenant) {
        this.pessoaService = pessoaService;
        this.pessoaService.setTenant(tenant);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})
    public Response getById(@PathParam("id") Long pId) {

        return pessoaService.findById(pId);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario", "bot"})

    public Response count(@QueryParam("id") Long id,
                          @QueryParam("nome") String nome,
                          @QueryParam("generoId") Long generoId,
                          @QueryParam("dataNascimento") LocalDate dataNascimento,
                          @QueryParam("telefone") String telefone,
                          @QueryParam("celular") String celular,
                          @QueryParam("email") String email,
                          @QueryParam("cpf") String cpf,
                          @QueryParam("telegramId") Long telegramId,
                          @QueryParam("whatsappId") Long whatsappId,
                          @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
                          @QueryParam("page") @DefaultValue("1") int pageIndex,
                          @QueryParam("size") @DefaultValue("20") int pageSize,
                          @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                          @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return pessoaService.count(id, nome, generoId, dataNascimento, telefone, celular, email, cpf, telegramId, whatsappId, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response list(
            @QueryParam("id") Long id,
            @QueryParam("nome") String nome,
            @QueryParam("generoId") Long generoId,
            @QueryParam("dataNascimento") LocalDate dataNascimento,
            @QueryParam("telefone") String telefone,
            @QueryParam("celular") String celular,
            @QueryParam("email") String email,
            @QueryParam("cpf") String cpf,
            @QueryParam("telegramId") Long telegramId,
            @QueryParam("whatsappId") Long whatsappId,
            @QueryParam("sort") @DefaultValue("desc") @NotNull String sortQuery,
            @QueryParam("page") @DefaultValue("1") int pageIndex,
            @QueryParam("size") @DefaultValue("20") int pageSize,
            @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
            @QueryParam("strgOrder") @DefaultValue("id") String strgOrder) {

        return pessoaService.list(id, nome, generoId, dataNascimento, telefone, celular, email, cpf, telegramId, whatsappId, sortQuery, pageIndex, pageSize, ativo, strgOrder);
    }

    @GET
    @Path("/cpf")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listByCPF(@QueryParam("page") @DefaultValue("0") int pageIndex,
                              @QueryParam("size") @DefaultValue("20") int pageSize,
                              @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                              @QueryParam("cpf") String cpf) {

        return pessoaService.listByCPF(pageIndex, pageSize, ativo, cpf);
    }

    @GET
    @Path("/phone")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listByPhone(@QueryParam("page") @DefaultValue("0") int pageIndex,
                                @QueryParam("size") @DefaultValue("20") int pageSize,
                                @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                                @QueryParam("telefone") String telefone) {

        return pessoaService.listByPhone(pageIndex, pageSize, ativo, telefone);
    }

    @GET
    @Path("/ident")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response listByIdent(@QueryParam("page") @DefaultValue("0") int pageIndex,
                                @QueryParam("size") @DefaultValue("20") int pageSize,
                                @QueryParam("ativo") @DefaultValue("true") Boolean ativo,
                                @QueryParam("ident") String ident) {

        return pessoaService.listByIdent(pageIndex, pageSize, ativo, ident);
    }

    @GET
    @Path("/telegram")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response getByTelegram(@QueryParam("telegramId") Long telegramId,
                                  @QueryParam("ativo") @DefaultValue("true") Boolean ativo) {

        return pessoaService.getByTelegram(telegramId, ativo);
    }

    @GET
    @Path("/whatsapp")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response getByWhatsapp(@QueryParam("whatsappId") Long whatsappId,
                                  @QueryParam("ativo") @DefaultValue("true") Boolean ativo) {

        return pessoaService.getByWhatsapp(whatsappId, ativo);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response add(EntidadeDTO entity) {
        return pessoaService.addPessoa(entity);
    }

    @POST
    @Path("/bot/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response addByBot(EntidadeDTO entity) {
        return pessoaService.addPessoa(entity);
    }

    @POST
    @Path("/telegram")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response updateByTelegram(@QueryParam("telegramId") Long telegramId,
                                     @QueryParam("ativo") @DefaultValue("true") Boolean ativo, EntidadeDTO pessoa) {

        return pessoaService.addTelegramIdPessoa(pessoa, telegramId, ativo);
    }

    @POST
    @Path("/telegram/{enable}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response configureTelegramNotifications(@QueryParam("ativo") @DefaultValue("true") Boolean ativo, EntidadeDTO pessoa, @PathParam("enable") Boolean notificationEnabled) {

        return pessoaService.configureTelegramNotifications(pessoa, ativo, notificationEnabled);
    }


    @DELETE
    @Path("/telegram")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response removeByTelegram(@QueryParam("telegramId") Long telegramId,
                                     @QueryParam("ativo") @DefaultValue("true") Boolean ativo) {

        return pessoaService.removeTelegramIdPessoa(telegramId, ativo);
    }

    @POST
    @Path("/whatsapp")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response updateByWhatsapp(@QueryParam("whatsappId") Long whatsappId,
                                     @QueryParam("ativo") @DefaultValue("true") Boolean ativo, EntidadeDTO pessoa) {

        return pessoaService.addWhatsappIdPessoa(pessoa, whatsappId, ativo);
    }


    @POST
    @Path("/whatsapp/{enable}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response configureWhatsappNotifications(@QueryParam("ativo") @DefaultValue("true") Boolean ativo, EntidadeDTO pessoa, @PathParam("enable") Boolean notificationEnabled) {

        return pessoaService.configureWhatsappNotifications(pessoa, ativo, notificationEnabled);
    }

    @DELETE
    @Path("/whatsapp")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @PermitAll
    public Response removeByWhatsapp(@QueryParam("whatsappId") Long whatsappId,
                                     @QueryParam("ativo") @DefaultValue("true") Boolean ativo) {

        return pessoaService.removeWhatsappIdPessoa(whatsappId, ativo);
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response update(EntidadeDTO entity) {

        return pessoaService.updatePessoa(entity);
    }

    @DELETE
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response deleteList(List<Long> listaPessoa) {

        return pessoaService.deletePessoa(listaPessoa);
    }

    @PUT
    @Path("/reactivate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @RolesAllowed({"usuario"})
    public Response reactivateList(List<Long> listaPessoa) {

        return pessoaService.reactivatePessoa(listaPessoa);
    }
}
