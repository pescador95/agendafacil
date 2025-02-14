package app.api.agendaFacil.business.historicoPessoa.service;

import app.api.agendaFacil.business.historicoPessoa.DTO.HistoricoPessoaDTO;
import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.api.agendaFacil.business.historicoPessoa.loader.HistoricoPessoaLoader;
import app.api.agendaFacil.business.historicoPessoa.manager.HistoricoPessoaManager;
import app.api.agendaFacil.business.historicoPessoa.repository.HistoricoPessoaRepository;
import app.api.agendaFacil.business.historicoPessoa.validator.HistoricoPessoaValidator;
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

import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.historicoPessoa.filter.HistoricoPessoaFilters.makeHistoricoPessoaQueryStringByFilters;

@RequestScoped
public class HistoricoPessoaService extends HistoricoPessoaManager {

    private HistoricoPessoa historicoPessoa = new HistoricoPessoa();
    private Pessoa pessoa;

    public HistoricoPessoaService() {
        super();

    }

    @Inject
    public HistoricoPessoaService(HistoricoPessoaRepository historicoPessoaRepository, SecurityContext context,
                                  HistoricoPessoaValidator historicoPessoaValidator, HistoricoPessoaLoader historicoPessoaLoader,
                                  PessoaLoader pessoaLoader) {
        super(historicoPessoaRepository, context, historicoPessoaValidator, historicoPessoaLoader, pessoaLoader);
    }

    public static List<HistoricoPessoaDTO> toDTO(List<HistoricoPessoa> listHistoricoPessoa) {

        List<HistoricoPessoaDTO> historicoPessoaDTOSDTOS = new ArrayList<>();

        listHistoricoPessoa.forEach(object -> {

            HistoricoPessoaDTO objectDTO = new HistoricoPessoaDTO(object);

            historicoPessoaDTOSDTOS.add(objectDTO);
        });
        return historicoPessoaDTOSDTOS;
    }

    public Response addHistoricoPessoa(@NotNull HistoricoPessoaDTO entity) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadByHistoricoPessoaByPessoa(entity);

            validaHistoricoPessoa();

            if (BasicFunctions.isEmpty(historicoPessoa) && BasicFunctions.isNotEmpty(pessoa)) {

                loadByHistoricoPessoa(entity);

                if (!responses.hasMessages()) {

                    historicoPessoa = new HistoricoPessoa(entity, pessoa, context);

                    historicoPessoaRepository.create(historicoPessoa);

                    responses.setData(historicoPessoa);
                    responses.setMessages("HistoricoPessoa cadastrado com sucesso!");
                    responses.setStatus(201);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateHistoricoPessoa(@NotNull HistoricoPessoaDTO entity) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            HistoricoPessoa historicoPessoa = historicoPessoaLoader.findById(entity);

            loadHistoricoPessoaById(historicoPessoa);

            if (BasicFunctions.isNotEmpty(historicoPessoa)) {
                loadByHistoricoPessoa(entity);
            }

            if (!responses.hasMessages()) {

                historicoPessoa = historicoPessoa.historicoPessoa(historicoPessoa, entity, context);

                historicoPessoaRepository.update(historicoPessoa);

                responses.setData(new HistoricoPessoaDTO(historicoPessoa));
                responses.setMessages("Cadastro de Histórico da Pessoa atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteHistoricoPessoa(@NotNull List<Long> pListIdHistoricoPessoa) {

        try {

            List<HistoricoPessoa> historicoPessoas;
            List<HistoricoPessoa> pessoasAuxes = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            historicoPessoas = historicoPessoaLoader.listByIds(pListIdHistoricoPessoa, Boolean.TRUE);
            int count = historicoPessoas.size();

            validaHistoricos(historicoPessoas);

            if (BasicFunctions.isNotEmpty(historicoPessoas)) {
                historicoPessoas.forEach((historicoPessoa) -> {

                    HistoricoPessoa historicoPessoaDeleted = historicoPessoa.deletarHistoricoPessoa(historicoPessoa,
                            context);

                    historicoPessoaRepository.delete(historicoPessoaDeleted);
                    pessoasAuxes.add(historicoPessoaDeleted);
                });
            }

            responses.setMessages(Responses.DELETED, count);
            responses.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response reactivateHistoricoPessoa(@NotNull List<Long> pListIdHistoricoPessoa) {

        try {

            List<HistoricoPessoa> historicoPessoas;
            List<HistoricoPessoa> historicoPessoasAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            historicoPessoas = historicoPessoaLoader.listByIds(pListIdHistoricoPessoa, Boolean.FALSE);
            int count = historicoPessoas.size();

            validaHistoricosReativados(historicoPessoas);
            if (BasicFunctions.isNotEmpty(historicoPessoas)) {

                historicoPessoas.forEach((historicoPessoa) -> {

                    HistoricoPessoa historicoPessoaReactivated = historicoPessoa
                            .reativarHistoricoPessoa(historicoPessoa, context);

                    historicoPessoaRepository.restore(historicoPessoaReactivated);
                    historicoPessoasAux.add(historicoPessoaReactivated);
                });
            }

            responses.setMessages(Responses.REACTIVATED, count);
            responses.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void loadByHistoricoPessoa(HistoricoPessoaDTO entity) {

        pessoa = pessoaLoader.loadPessoaByHistoricoPessoa(entity);

        if (!historicoPessoaValidator.validarHistoricoPessoa(entity)) {
            addErrorValidarHistoricoPessoa();
        }
    }

    private void loadByHistoricoPessoaByPessoa(HistoricoPessoaDTO entity) {

        pessoa = pessoaLoader.loadPessoaByHistoricoPessoa(entity);

        historicoPessoa = historicoPessoaLoader.findById(pessoa);

        if (!historicoPessoaValidator.validarHistoricoPessoa(entity)) {
            addErrorValidarHistoricoPessoa();
        }
    }

    private void loadHistoricoPessoaById(HistoricoPessoa pHistoricoPessoa) {

        historicoPessoa = historicoPessoaLoader.findById(pHistoricoPessoa);

        if (!historicoPessoaValidator.validarHistoricoPessoa(pHistoricoPessoa)) {
            addErrorValidarHistoricoPessoa();
        }
    }

    void addErrorValidarHistoricoPessoa() {
        responses.setMessages("Informe os dados para atualizar o cadastro do HistoricoPessoa.");
        responses.setStatus(400);
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();
            historicoPessoa = historicoPessoaLoader.findById(pId);
            return Response.ok(new HistoricoPessoaDTO(historicoPessoa)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(Long id, String queixaPrincipal, String medicamentos, String diagnosticoClinico,
                          String comorbidades, String ocupacao, String responsavelContato, String nomePessoa, String sortQuery,
                          Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeHistoricoPessoaQueryStringByFilters(id, queixaPrincipal, medicamentos, diagnosticoClinico, comorbidades, ocupacao, responsavelContato, nomePessoa, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            Integer count = historicoPessoaLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(Long id, String queixaPrincipal, String medicamentos, String diagnosticoClinico,
                         String comorbidades, String ocupacao, String responsavelContato, String nomePessoa, String sortQuery,
                         Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeHistoricoPessoaQueryStringByFilters(id, queixaPrincipal, medicamentos, diagnosticoClinico, comorbidades, ocupacao, responsavelContato, nomePessoa, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            List<HistoricoPessoa> historicosPessoa = historicoPessoaLoader.list(queryString);

            return Response.ok(toDTO(historicosPessoa)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaHistoricoPessoa() {

        if (BasicFunctions.isNotEmpty(historicoPessoa)) {

            responses.setData(new HistoricoPessoaDTO(historicoPessoa));
            responses.setMessages("HistoricoPessoa já cadastrada!");
            responses.setStatus(400);
        }
    }

    private void validaHistoricos(List<HistoricoPessoa> historicoPessoas) {

        if (BasicFunctions.isEmpty(historicoPessoas)) {

            responses.setMessages("Históricos não localizados ou já excluídos.");
            responses.setStatus(400);
        }
    }

    private void validaHistoricosReativados(List<HistoricoPessoa> historicoPessoas) {

        if (BasicFunctions.isEmpty(historicoPessoas)) {
            responses.setMessages("Históricos não localizados ou já reativados.");
            responses.setStatus(400);
        }
    }
}
