package app.api.agendaFacil.business.tipoAgendamento.service;

import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.filter.TipoAgendamentoFilters;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.tipoAgendamento.manager.TipoAgendamentoManager;
import app.api.agendaFacil.business.tipoAgendamento.repository.TipoAgendamentoRepository;
import app.api.agendaFacil.business.tipoAgendamento.validator.TipoAgendamentoValidator;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


@RequestScoped
public class TipoAgendamentoService extends TipoAgendamentoManager {

    private TipoAgendamento tipoAgendamento;
    private List<Organizacao> organizacoes;

    public TipoAgendamentoService() {
        super();
    }

    @Inject
    public TipoAgendamentoService(TipoAgendamentoRepository tipoAgendamentoRepository, TipoAgendamentoValidator tipoAgendamentoValidator, TipoAgendamentoLoader tipoAgendamentoLoader, OrganizacaoLoader organizacaoLoader) {
        super(tipoAgendamentoRepository, tipoAgendamentoValidator, tipoAgendamentoLoader, organizacaoLoader);
    }

    public Response addTipoAgendamento(@NotNull TipoAgendamentoDTO pTipoAgendamento) {

        try {

            TipoAgendamentoDTO entity;

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadTipoAgendamentoByTipoAgendamento(pTipoAgendamento);

            validaTipoAgendamento();

            entity = loadByTipoAgendamento(pTipoAgendamento);

            if (!responses.hasMessages()) {

                tipoAgendamento = new TipoAgendamento(entity);

                tipoAgendamentoRepository.create(tipoAgendamento);

                responses.setData(new TipoAgendamentoDTO(tipoAgendamento));
                responses.setMessages("Tipo do Agendamento cadastrado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateTipoAgendamento(@NotNull TipoAgendamentoDTO pTipoAgendamento) {

        try {

            TipoAgendamentoDTO entity;

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadTipoAgendamentoById(pTipoAgendamento);
            if (BasicFunctions.isNotEmpty(tipoAgendamento)) {

                entity = loadByTipoAgendamento(pTipoAgendamento);

                if (!responses.hasMessages()) {

                    tipoAgendamento = tipoAgendamento.tipoAgendamento(tipoAgendamento, entity, organizacoes);

                    tipoAgendamentoRepository.update(tipoAgendamento);

                    responses.setData(new TipoAgendamentoDTO(tipoAgendamento));
                    responses.setMessages("Tipo do Agendamento atualizado com sucesso!");
                    responses.setStatus(200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteTipoAgendamento(@NotNull List<Long> pListIdTipoAgendamento) {

        try {

            List<TipoAgendamento> tipoAgendamentos;
            List<TipoAgendamento> tipoAgendamentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            tipoAgendamentos = tipoAgendamentoLoader.listByIds(pListIdTipoAgendamento);
            int count = tipoAgendamentos.size();

            if (tipoAgendamentos.isEmpty()) {

                responses.setMessages("Tipos de Agendamentos não localizados ou já excluídos.");
                responses.setStatus(400);
                return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
            }

            tipoAgendamentos.forEach((TipoAgendamento) -> {
                tipoAgendamentosAux.add(TipoAgendamento);
                tipoAgendamentoRepository.remove(TipoAgendamento);
            });

            responses.setMessages(Responses.DELETED, count);
            responses.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }


    private TipoAgendamentoDTO loadByTipoAgendamento(@NotNull TipoAgendamentoDTO pTipoAgendamento) {

        List<Long> organizacoesId = new ArrayList<>();
        List<OrganizacaoDTO> organizacaoDTOS = new ArrayList<>();

        TipoAgendamentoDTO entity = null;

        if (BasicFunctions.isNotEmpty(pTipoAgendamento)) {

            entity = new TipoAgendamentoDTO();

            if (BasicFunctions.isNotEmpty(pTipoAgendamento.getOrganizacoes())) {
                pTipoAgendamento.getOrganizacoes().forEach(organizacao -> organizacoesId.add(organizacao.getId()));
            }
            organizacoes = organizacaoLoader.list(organizacoesId);

            if (BasicFunctions.isNotEmpty(pTipoAgendamento.getTipoAgendamento())) {
                entity.setTipoAgendamento(pTipoAgendamento.getTipoAgendamento());
            }

            if (BasicFunctions.isNotEmpty(organizacoes)) {
                organizacoes.forEach(organizacao -> {
                    organizacaoDTOS.add(new OrganizacaoDTO(organizacao));
                });
                entity.setOrganizacoes(organizacaoDTOS);
            }

            if (!tipoAgendamentoValidator.validaTipoAgendamento(pTipoAgendamento)) {
                addErrorValidaTipoAgendamento();
            }
        }
        return entity;
    }

    public void addErrorValidaTipoAgendamento() {
        responses.setMessages("Informe os dados para atualizar o cadastro do Tipo de Agendamento.");
        responses.setStatus(400);
    }

    private void loadTipoAgendamentoById(TipoAgendamentoDTO pTipoAgendamento) {

        tipoAgendamento = tipoAgendamentoLoader.findByTipoAgendamento(pTipoAgendamento);
    }

    private void loadTipoAgendamentoByTipoAgendamento(TipoAgendamentoDTO pTipoAgendamento) {

        List<Long> organizacoesId = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(pTipoAgendamento, pTipoAgendamento.getOrganizacoes())) {

            pTipoAgendamento.getOrganizacoes().forEach(organizacao -> {
                if (BasicFunctions.isNotEmpty(organizacao) && BasicFunctions.isValid(organizacao)) {
                    organizacoesId.add(organizacao.getId());
                }
            });

            tipoAgendamento = tipoAgendamentoLoader.findByTipoAgendamento(pTipoAgendamento);
        }
    }

    public Response listByScheduler(List<Long> organizacoes) {

        try {

            responses = new Responses();

            List<TipoAgendamento> tiposAgendamentosFiltrados = tipoAgendamentoLoader.tiposAgendamentosByOrganizacaoId(organizacoes);

            return Response.ok(tiposAgendamentosFiltrados).status(responses.getStatus()).build();
        } catch (Exception e) {
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(Long id, String tipoAgendamento, List<Long> organizacaoId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryFilter = TipoAgendamentoFilters.makeTipoAgendamentoQueryStringByFilters(id, tipoAgendamento, organizacaoId, sortQuery, strgOrder, pageIndex, pageSize);

            List<TipoAgendamentoDTO> tipoAgendamentosDTO = tipoAgendamentoLoader.list(queryFilter);

            return Response.ok(tipoAgendamentosDTO).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response findById(Long pId) {

        try {

            tipoAgendamento = tipoAgendamentoLoader.findById(pId);
            return Response.ok(new TipoAgendamentoDTO(tipoAgendamento)).status(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(Long id, String tipoAgendamento, List<Long> organizacaoId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryFilter = TipoAgendamentoFilters.makeTipoAgendamentoQueryStringByFilters(id, tipoAgendamento, organizacaoId, sortQuery, strgOrder, pageIndex, pageSize);

            Integer count = tipoAgendamentoLoader.count(queryFilter);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaTipoAgendamento() {

        if (BasicFunctions.isNotEmpty(tipoAgendamento)) {
            responses.setData(tipoAgendamento);
            responses.setMessages("Tipo do Agendamento já cadastrado!");
            responses.setStatus(400);
        }
    }

}
