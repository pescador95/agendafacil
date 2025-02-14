package app.api.agendaFacil.business.atendimento.service;

import app.api.agendaFacil.business.atendimento.DTO.AtendimentoDTO;
import app.api.agendaFacil.business.atendimento.entity.Atendimento;
import app.api.agendaFacil.business.atendimento.loader.AtendimentoLoader;
import app.api.agendaFacil.business.atendimento.manager.AtendimentoManager;
import app.api.agendaFacil.business.atendimento.repository.AtendimentoRepository;
import app.api.agendaFacil.business.atendimento.validator.AtendimentoValidator;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.atendimento.filter.AtendimentoFilters.makeAtendimentoQueryStringByFilters;

@RequestScoped
public class AtendimentoService extends AtendimentoManager {

    protected Atendimento atendimento;
    protected Pessoa pessoa;

    public AtendimentoService() {
        super();
    }

    @Inject
    public AtendimentoService(AtendimentoRepository atendimentoRepository, AtendimentoValidator atendimentoValidator,
                              SecurityContext context, AtendimentoLoader atendimentoLoader, PessoaLoader pessoaLoader) {
        super(atendimentoRepository, atendimentoValidator, context, atendimentoLoader, pessoaLoader);
    }

    public static List<AtendimentoDTO> toDTO(List<Atendimento> pAtendimento) {

        List<AtendimentoDTO> listAtendimentoDTO = new ArrayList<>();

        pAtendimento.forEach(atendimento -> {

            AtendimentoDTO atendimentoDTO = new AtendimentoDTO(atendimento);

            listAtendimentoDTO.add(atendimentoDTO);
        });

        return listAtendimentoDTO;

    }

    public Response addAtendimento(@NotNull AtendimentoDTO pAtendimentoDTO, Long agendamentoId) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Atendimento newAtendimento = new Atendimento(pAtendimentoDTO, context);

            loadAtendimentoByPessoaData(newAtendimento);

            validaAtendimentoRealizado();

            loadPessoaByAtendimento(newAtendimento);

            if (!responses.hasMessages()) {

                atendimento = new Atendimento(newAtendimento, agendamentoId, pessoa, context);
                atendimentoRepository.create(atendimento);

                responses.setData(new AtendimentoDTO(atendimento));
                responses.setMessages("Atendimento cadastrado com sucesso!");
                responses.setStatus(201);

            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateAtendimento(@NotNull AtendimentoDTO pAtendimentoDTO, Long agendamentoId) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Atendimento pAtendimento = new Atendimento(pAtendimentoDTO, context);

            loadAtendimentoById(pAtendimento);

            if (BasicFunctions.isNotEmpty(atendimento)) {

                loadPessoaByAtendimento(pAtendimento);

                atendimento = atendimento.atendimento(atendimento, pAtendimento, agendamentoId, pessoa, context);

                atendimentoRepository.update(atendimento);

                responses.setData(atendimento);
                responses.setMessages("Cadastro de Atendimento  atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteAtendimento(@NotNull List<Long> pListIdAtendimento) {

        try {

            List<Atendimento> atendimentos;
            List<Atendimento> atendimentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            atendimentos = AtendimentoLoader.listByIds(pListIdAtendimento, Boolean.TRUE);
            int count = atendimentos.size();

            if (atendimentosDisponiveis(atendimentos)) {

                atendimentos.forEach((atendimento) -> {

                    Atendimento atendimentoDeleted;

                    atendimentoDeleted = atendimento.deletarAtendimento(atendimento, context);

                    atendimentoRepository.delete(atendimentoDeleted);
                    atendimentosAux.add(atendimentoDeleted);
                });

                responses.setMessages(Responses.DELETED, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response reactivateAtendimento(@NotNull List<Long> pListIdAtendimento) {

        try {

            List<Atendimento> atendimentos;
            List<Atendimento> atendimentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            atendimentos = AtendimentoLoader.listByIds(pListIdAtendimento, Boolean.FALSE);
            int count = atendimentos.size();

            if (atendimentosDisponiveis(atendimentos)) {

                atendimentos.forEach((atendimento) -> {

                    Atendimento atendimentoReactivated = atendimento.reativarAgendimento(atendimento, context);

                    atendimentoRepository.restore(atendimentoReactivated);
                    atendimentosAux.add(atendimentoReactivated);
                });

                responses.setMessages(Responses.REACTIVATED, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void loadPessoaByAtendimento(Atendimento pAtendimento) {

        pessoa = pessoaLoader.loadPessoaByAtendimento(pAtendimento);
    }

    private void loadAtendimentoByPessoaData(Atendimento pAtendimento) {

        if (BasicFunctions.isNotEmpty(pAtendimento) && BasicFunctions.isValid(pAtendimento.getDataAtendimento())) {
            atendimento = atendimentoLoader.findByPessoaDataAtendimento(pAtendimento);
        }
        if (!atendimentoValidator.validarAtendimento(pAtendimento)) {
            addErrorValidaAtendimento();
        }

    }

    private void loadAtendimentoById(Atendimento pAtendimento) {

        atendimento = AtendimentoLoader.findById(pAtendimento);

        if (!atendimentoValidator.validarAtendimento(pAtendimento)) {
            addErrorValidaAtendimento();
        }
    }

    public void addErrorValidaAtendimento() {
        responses.setMessages("Informe os dados para atualizar o cadastro do Atendimento.");
        responses.setStatus(400);
    }

    public Response list(LocalDate dataAtendimento, LocalDate dataInicio, LocalDate dataFim, Long pessoaId,
                         Long usuarioId, String atividade, String evolucaoSintomas, String avaliacao, String sortQuery,
                         Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            QueryFilter queryString = makeAtendimentoQueryStringByFilters(dataAtendimento, dataInicio, dataFim, atividade,
                    evolucaoSintomas, avaliacao, usuarioId, pessoaId, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            responses = new Responses();

            List<Atendimento> atendimentos = atendimentoLoader.list(queryString);

            return Response.ok(toDTO(atendimentos)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(LocalDate dataAtendimento, LocalDate dataInicio, LocalDate dataFim, Long pessoaId,
                          Long usuarioId, String atividade, String evolucaoSintomas, String avaliacao, String sortQuery,
                          Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            QueryFilter queryString = makeAtendimentoQueryStringByFilters(dataAtendimento, dataInicio, dataFim, atividade,
                    evolucaoSintomas, avaliacao, usuarioId, pessoaId, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            responses = new Responses();

            Integer count = atendimentoLoader.count(queryString);
            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            atendimento = AtendimentoLoader.findById(pId);

            if (BasicFunctions.isEmpty(atendimento)) {
                responses.setMessages("Atendimento não localizado.");
                responses.setStatus(404);
            }
            return Response.ok(new AtendimentoDTO(atendimento)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaAtendimentoRealizado() {

        if (BasicFunctions.isNotEmpty(atendimento)) {
            responses.setData(atendimento);
            responses.setMessages("Atendimento já realizado!");
            responses.setStatus(400);
        }
    }

    public Boolean atendimentosDisponiveis(List<Atendimento> atendimentos) {

        if (BasicFunctions.isEmpty(atendimentos)) {
            responses.setMessages("Atendimentos não localizados ou já excluídos.");
            responses.setStatus(400);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
