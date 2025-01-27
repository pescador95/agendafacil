package app.api.agendaFacil.business.pessoa.service;

import app.api.agendaFacil.business.genero.entity.Genero;
import app.api.agendaFacil.business.genero.loader.GeneroLoader;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.pessoa.manager.PessoaManager;
import app.api.agendaFacil.business.pessoa.validator.PessoaValidator;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static app.api.agendaFacil.business.pessoa.filter.PessoaFilters.makePessoaQueryStringByFilters;

@RequestScoped
@Transactional
public class PessoaService extends PessoaManager {

    private Pessoa pessoa;
    private Genero genero;

    public PessoaService() {
        super();
    }

    @Inject
    public PessoaService(SecurityContext context, PessoaValidator pessoaValidator, PessoaLoader pessoaLoader,
                         GeneroLoader generoLoader) {
        super(context, pessoaValidator, pessoaLoader, generoLoader);
    }

    public static List<PessoaDTO> toDTO(List<Pessoa> pessoas) {

        List<PessoaDTO> pessoaDTOS = new ArrayList<>();

        pessoas.forEach(pessoa -> {

            PessoaDTO pessoaDTO = new PessoaDTO(pessoa);

            pessoaDTOS.add(pessoaDTO);
        });
        return pessoaDTOS;
    }

    public Response addPessoa(@NotNull EntidadeDTO entity) {

        try {

            responses = new Responses();

            Pessoa pessoaValidate = new Pessoa(entity, context, getTenant());

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadPessoaByPessoa(pessoaValidate);

            validaPessoa();

            if (BasicFunctions.isEmpty(pessoa)) {

                pessoa = new Pessoa(context, getTenant());

                loadByPessoa(pessoaValidate);

                if (!responses.hasMessages()) {

                    pessoa = new Pessoa(entity, genero, context, getTenant());

                    responses.setMessages(new ArrayList<>());

                    pessoa.persist();

                    responses.setData(new EntidadeDTO(pessoa));
                    responses.setMessages("Pessoa cadastrada com sucesso!");
                    responses.setStatus(201);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updatePessoa(@NotNull EntidadeDTO entity) throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Pessoa pessoaValidate = new Pessoa(entity, context, getTenant());

            loadPessoaByPessoa(pessoaValidate);

            if (BasicFunctions.isNotEmpty(pessoa)) {
                loadByPessoa(pessoa);
            }

            if (!responses.hasMessages()) {

                pessoa = pessoa.pessoa(pessoa, entity, genero, context);

                pessoa.persistAndFlush();

                responses.setData(new EntidadeDTO(pessoa));
                responses.setMessages("Cadastro de Pessoa atualizada com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response addTelegramIdPessoa(EntidadeDTO pPessoa, Long telegramId, Boolean ativo)
            throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Pessoa pessoa = pessoaLoader.findById(pPessoa, ativo);

            if (BasicFunctions.isNotEmpty(pessoa)) {

                pessoa.setTelegramId(telegramId);
                pessoa.setRecebeNotifacaoTelegram(pPessoa.getRecebeNotifacaoTelegram());
                pessoa.persist();

                responses.setData(pessoa);
                responses.setMessages("Vínculo do Telegram efetuado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response configureTelegramNotifications(EntidadeDTO pPessoa, Boolean ativo, Boolean notificationEnabled)
            throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Pessoa pessoa = pessoaLoader.findById(pPessoa, ativo);

            if (BasicFunctions.isNotEmpty(pessoa)) {

                pessoa.setRecebeNotifacaoTelegram(notificationEnabled);
                pessoa.persist();

                responses.setData(pessoa);
                responses.setMessages(Responses.resolveActionForEnabled(notificationEnabled), 1,
                        "Notificação de Telegram");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response addWhatsappIdPessoa(EntidadeDTO pPessoa, Long whatsappId, Boolean ativo)
            throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Pessoa pessoa = pessoaLoader.findById(pPessoa, ativo);

            if (BasicFunctions.isNotEmpty(pessoa)) {

                pessoa.setWhatsappId(whatsappId);
                pessoa.setRecebeNotifacaoWhatsapp(pPessoa.getRecebeNotifacaoWhatsapp());
                pessoa.persist();

                responses.setData(pessoa);
                responses.setMessages("Vínculo do Whatsapp efetuado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response configureWhatsappNotifications(EntidadeDTO pPessoa, Boolean ativo, Boolean notificationEnabled)
            throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Pessoa pessoa = pessoaLoader.findById(pPessoa, ativo);

            if (BasicFunctions.isNotEmpty(pessoa)) {

                pessoa.setRecebeNotifacaoWhatsapp(notificationEnabled);
                pessoa.persist();

                responses.setData(pessoa);
                responses.setMessages(Responses.resolveActionForEnabled(notificationEnabled), 1,
                        "Notificação de Whatsapp");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response removeTelegramIdPessoa(Long telegramId, Boolean ativo) throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Pessoa pessoa = pessoaLoader.findByTelegramId(telegramId, ativo);

            if (BasicFunctions.isNotEmpty(pessoa)) {

                pessoa.setTelegramId(null);

                pessoa.persist();

                responses.setData(pessoa);
                responses.setMessages(Responses.DELETED, 1, "Vinculo do Telegram");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response removeWhatsappIdPessoa(Long whatsappId, Boolean ativo) throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Pessoa pessoa = pessoaLoader.findByWhatsappId(whatsappId, ativo);

            if (BasicFunctions.isNotEmpty(pessoa)) {

                pessoa.setWhatsappId(null);

                pessoa.persist();

                responses.setData(pessoa);
                responses.setMessages(Responses.DELETED, 1, "Vinculo do WhatsApp");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deletePessoa(@NotNull List<Long> pListPessoa) {

        try {

            List<Pessoa> pessoas;
            List<Pessoa> pessoasAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            pessoas = pessoaLoader.listByIds(pListPessoa, Boolean.TRUE);
            int count = pessoas.size();

            validaPessoas(pessoas);

            if (BasicFunctions.isNotEmpty(pessoas)) {

                pessoas.forEach((pessoa) -> {

                    Pessoa pessoaDeleted = pessoa.deletarPessoa(pessoa, context);

                    pessoaDeleted.persist();
                    pessoasAux.add(pessoaDeleted);
                });

                responses.setMessages(Responses.DELETED, count);
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response reactivatePessoa(@NotNull List<Long> pListPessoa) {

        try {

            List<Pessoa> pessoas;
            List<Pessoa> pessoasAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            pessoas = pessoaLoader.listByIds(pListPessoa, Boolean.FALSE);
            int count = pessoas.size();

            validaPessoasReativas(pessoas);

            if (BasicFunctions.isNotEmpty(pessoas)) {

                pessoas.forEach((pessoa) -> {

                    Pessoa pessoasReactivated = pessoa.reativarPessoa(pessoa, context);

                    pessoasReactivated.persist();
                    pessoasAux.add(pessoasReactivated);
                });

                responses.setMessages(Responses.REACTIVATED, count);
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void loadByPessoa(Pessoa pPessoa) {

        genero = generoLoader.findById(pPessoa);

        pessoaValidator.validaDadosPessoa(pPessoa, context, responses);
    }

    private void loadPessoaByPessoa(Pessoa pPessoa) {

        pessoa = new Pessoa(context, getTenant());

        pessoa = pessoaLoader.findByPessoa(pPessoa);

        pessoaValidator.validaDadosPessoa(pessoa, context, responses);
    }

    public Response findById(Long pId) {

        try {
            responses = new Responses();
            pessoa = pessoaLoader.findById(pId);
            return Response.ok(new PessoaDTO(pessoa)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(Long id, String nome, Long generoId, LocalDate dataNascimento, String telefone, String celular, String email, String cpf, Long telegramId, Long whatsappId, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makePessoaQueryStringByFilters(id, nome, generoId, dataNascimento, telefone, celular, email, cpf, telegramId, whatsappId, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            Integer count = pessoaLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(Long id, String nome, Long generoId, LocalDate dataNascimento, String telefone, String celular, String email, String cpf, Long telegramId, Long whatsappId, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makePessoaQueryStringByFilters(id, nome, generoId, dataNascimento, telefone, celular, email, cpf, telegramId, whatsappId, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            List<Pessoa> pessoas = pessoaLoader.list(queryString);

            return Response.ok(toDTO(pessoas)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response listByCPF(Integer pageIndex, Integer pageSize, Boolean ativo, String cpf) {

        try {

            responses = new Responses();

            query = "ativo = " + ativo + " and cpf = '" + cpf + "'";

            PanacheQuery<Pessoa> pessoa;
            pessoa = PessoaLoader.find(query);
            List<Pessoa> pessoasFiltrados = pessoa.page(Page.of(pageIndex, pageSize)).list().stream()
                    .filter(c -> (c.getAtivo().equals(ativo)))
                    .collect(Collectors.toList());

            return Response.ok(toDTO(pessoasFiltrados)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response listByPhone(Integer pageIndex, Integer pageSize, Boolean ativo, String telefone) {

        try {

            responses = new Responses();

            query = "ativo = " + ativo + " and telefone = '" + telefone + "' or celular = '" + telefone + "'";

            PanacheQuery<Pessoa> pessoa;
            pessoa = PessoaLoader.find(query);
            List<Pessoa> pessoasFiltrados = pessoa.page(Page.of(pageIndex, pageSize)).list().stream()
                    .filter(c -> (c.getAtivo().equals(ativo)))
                    .collect(Collectors.toList());

            return Response.ok(toDTO(pessoasFiltrados)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response listByIdent(Integer pageIndex, Integer pageSize, Boolean ativo, String ident) {

        try {

            responses = new Responses();
            query = "ativo = " + ativo + " and telefone = '" + ident + "' or celular = '" + ident + "'" + " or cpf = '"
                    + ident + "'";

            PanacheQuery<Pessoa> pessoa;
            pessoa = PessoaLoader.find(query);
            List<Pessoa> pessoasFiltrados = pessoa.page(Page.of(pageIndex, pageSize)).list().stream()
                    .filter(c -> (c.getAtivo().equals(ativo)))
                    .collect(Collectors.toList());

            return Response.ok(toDTO(pessoasFiltrados)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response getByTelegram(Long telegramId, Boolean ativo) {

        try {

            responses = new Responses();

            pessoa = pessoaLoader.findByTelegramId(telegramId, ativo);
            return Response.ok(new PessoaDTO(pessoa)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response getByWhatsapp(Long whatsappId, Boolean ativo) {

        try {

            responses = new Responses();
            pessoa = pessoaLoader.findByWhatsappId(whatsappId, ativo);
            return Response.ok(new PessoaDTO(pessoa)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaPessoa() {

        if (BasicFunctions.isNotEmpty(pessoa)) {
            responses.setData(pessoa);
            responses.setMessages("Entidade já cadastrada com o CPF informado!");
            responses.setStatus(400);
        }
    }

    private void validaPessoas(List<Pessoa> pessoas) {

        if (BasicFunctions.isEmpty(pessoas)) {
            responses.setMessages("Pessoas não localizadas ou já excuídas.");
            responses.setStatus(404);
        }
    }

    private void validaPessoasReativas(List<Pessoa> pessoas) {

        if (BasicFunctions.isEmpty(pessoas)) {
            responses.setMessages("Pessoas não localizadas ou já reativadas.");
            responses.setStatus(404);
        }
    }
}
