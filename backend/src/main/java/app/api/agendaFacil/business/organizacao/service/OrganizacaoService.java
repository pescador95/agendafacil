package app.api.agendaFacil.business.organizacao.service;

import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.organizacao.manager.OrganizacaoManager;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.organizacao.filter.OrganizacaoFilters.makeOrganizacaoQueryStringByFilters;

@RequestScoped
public class OrganizacaoService extends OrganizacaoManager {

    private Organizacao organizacao = new Organizacao();

    public OrganizacaoService() {
        super();
    }

    @Inject
    public OrganizacaoService(SecurityContext context, OrganizacaoRepository organizacaoRepository, OrganizacaoLoader organizacaoLoader) {
        super(context, organizacaoRepository, organizacaoLoader);
    }

    public static List<OrganizacaoDTO> toDTO(List<Organizacao> organizacaos) {

        List<OrganizacaoDTO> organizacaoDTOS = new ArrayList<>();

        organizacaos.forEach(organizacao -> {

            OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(organizacao);

            organizacaoDTOS.add(organizacaoDTO);
        });
        return organizacaoDTOS;
    }

    public Response addOrganizacao(@NotNull EntidadeDTO entity) {

        try {

            responses = new Responses();

            Organizacao organizacaoValidate = new Organizacao(entity, context, getTenant());

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadOrganizacaoByCnpj(organizacaoValidate);

            validaOrganizacao();

            if (BasicFunctions.isEmpty(organizacao)) {

                if (!responses.hasMessages()) {

                    organizacao = new Organizacao(entity, context, getTenant());

                    organizacaoRepository.create(organizacao);

                    responses.setData(new EntidadeDTO(organizacao));
                    responses.setMessages("Organização cadastrada com sucesso!");
                    responses.setStatus(201);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateOrganizacao(@NotNull EntidadeDTO entity) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Organizacao organizacaoValidate = new Organizacao(entity, context, getTenant());

            loadOrganizacaoById(organizacaoValidate);

            if (BasicFunctions.isNotEmpty(organizacao) && !responses.hasMessages()) {

                organizacao = organizacao.organizacao(organizacao, entity, context);

                organizacaoRepository.update(organizacao);

                responses.setData(new EntidadeDTO(organizacao));
                responses.setMessages("Cadastro de Organização atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteOrganizacao(@NotNull List<Long> pListIdOrganizacao) {

        try {

            List<Organizacao> organizacoes;
            List<Organizacao> organizacoesAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            organizacoes = organizacaoLoader.listByIds(pListIdOrganizacao, Boolean.TRUE);
            int count = organizacoes.size();

            validaOrganizacoes(organizacoes);

            if (BasicFunctions.isNotEmpty(organizacoes)) {

                organizacoes.forEach((organizacao) -> {

                    Organizacao organizacaoDeleted = organizacao.deletarOrganizacao(organizacao, context);

                    organizacaoRepository.delete(organizacaoDeleted);
                    organizacoesAux.add(organizacaoDeleted);
                });

                responses = new Responses(200);
                responses.setMessages(Responses.DELETED, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response reactivateOrganizacao(@NotNull List<Long> pListIdOrganizacao) {

        try {

            List<Organizacao> organizacoes;
            List<Organizacao> organizacoesAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            organizacoes = organizacaoLoader.listByIds(pListIdOrganizacao, Boolean.FALSE);
            int count = organizacoes.size();


            validaOrganizacoesReativadas(organizacoes);

            if (BasicFunctions.isNotEmpty(organizacoes)) {

                organizacoes.forEach((organizacao) -> {

                    Organizacao organizacaoReactivated = organizacao.reativarOrganizacao(organizacao, context);

                    organizacaoRepository.restore(organizacaoReactivated);
                    organizacoesAux.add(organizacaoReactivated);
                });

                responses = new Responses(200);
                responses.setMessages(Responses.REACTIVATED, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void loadOrganizacaoByCnpj(Organizacao pOrganizacao) {

        organizacao = organizacaoLoader.findByOrganizacao(pOrganizacao);

        validaOrganizacao(pOrganizacao);
    }

    private void loadOrganizacaoById(Organizacao pOrganizacao) {

        organizacao = organizacaoLoader.findByOrganizacao(pOrganizacao);

        validaOrganizacao(pOrganizacao);
    }

    private void validaOrganizacao(Organizacao pOrganizacao) {

        if (pOrganizacao.cnpjJaUtilizado(pOrganizacao)) {
            responses.setMessages("Já existe uma organização cadastrada com o CNPJ informado!");
            responses.setStatus(409);
        }
        if (BasicFunctions.isEmpty(pOrganizacao.getNome())
                && BasicFunctions.isEmpty(pOrganizacao.getCnpj())) {
            responses.setMessages("Informe os dados para o cadastro da Organização.");
            responses.setStatus(409);
        }
    }

    private void validaOrganizacao() {

        if (BasicFunctions.isNotEmpty(organizacao)) {
            responses.setData(organizacao);
            responses.setMessages("Organização já cadastrada!");
            responses.setStatus(409);
        }
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            organizacao = organizacaoLoader.findById(pId);
            return Response.ok(new OrganizacaoDTO(organizacao)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(String nome, String cnpj, String telefone, String celular, String email, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeOrganizacaoQueryStringByFilters(nome, cnpj, telefone, celular, email, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            Integer count = organizacaoLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(String nome, String cnpj, String telefone, String celular, String email, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeOrganizacaoQueryStringByFilters(nome, cnpj, telefone, celular, email, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            List<Organizacao> organizacoes = organizacaoLoader.list(queryString);

            return Response.ok(toDTO(organizacoes)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response listByBot(String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeOrganizacaoQueryStringByFilters(null, null, null, null, null, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            List<Organizacao> organizacoes = organizacaoLoader.list(queryString);

            return Response.ok(organizacoes).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaOrganizacoes(List<Organizacao> organizacoes) {

        if (organizacoes.isEmpty()) {
            responses.setMessages("Organizações não localizadas ou já excluídas.");
            responses.setStatus(404);
        }
    }

    private void validaOrganizacoesReativadas(List<Organizacao> organizacoes) {

        if (organizacoes.isEmpty()) {
            responses.setMessages("Organizações não localizadas ou já reativadas.");
            responses.setStatus(404);
        }
    }
}
