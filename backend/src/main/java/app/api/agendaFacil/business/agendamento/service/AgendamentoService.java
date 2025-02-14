package app.api.agendaFacil.business.agendamento.service;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.agendamento.loader.AgendamentoAutomaticoLoader;
import app.api.agendaFacil.business.agendamento.loader.AgendamentoLoader;
import app.api.agendaFacil.business.agendamento.manager.AgendamentoManager;
import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.agendamento.validator.AgendamentoAutomaticoValidator;
import app.api.agendaFacil.business.agendamento.validator.AgendamentoValidator;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.configuradorAgendamento.validator.ConfiguradorAgendamentoValidator;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.api.agendaFacil.business.statusAgendamento.loader.StatusAgendamentoLoader;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static app.api.agendaFacil.business.agendamento.filter.AgendamentoFilters.makeAgendamentoQueryStringByFilters;

@RequestScoped
public class AgendamentoService extends AgendamentoManager {

    protected Agendamento agendamento;
    protected Agendamento agendamentoOld;
    protected Usuario profissional;
    protected Pessoa pessoa;
    protected Organizacao organizacao;
    protected TipoAgendamento tipoAgendamento;
    protected StatusAgendamento statusAgendamento;
    protected ConfiguradorAgendamento configuradorAgendamento;

    public AgendamentoService() {
        super();
    }

    @Inject
    public AgendamentoService(UsuarioLoader usuarioLoader,
                              AgendamentoRepository agendamentoRepository,
                              ConfiguradorAgendamentoLoader configuradorAgendamentoLoader,
                              AgendamentoAutomaticoValidator agendamentoAutomaticoValidator,
                              AgendamentoValidator agendamentoValidator,
                              ConfiguradorAgendamentoValidator configuradorAgendamentoValidator,
                              SecurityContext context,
                              AgendamentoLoader agendamentoLoader,
                              AgendamentoAutomaticoLoader agendamentoAutomaticoLoader,
                              TipoAgendamentoLoader tipoAgendamentoLoader,
                              PessoaLoader pessoaLoader,
                              OrganizacaoLoader organizacaoLoader,
                              StatusAgendamentoLoader statusAgendamentoLoader) {
        super(usuarioLoader,
                agendamentoRepository,
                configuradorAgendamentoLoader,
                agendamentoAutomaticoValidator,
                agendamentoValidator,
                configuradorAgendamentoValidator,
                context, agendamentoLoader,
                agendamentoAutomaticoLoader,
                tipoAgendamentoLoader,
                pessoaLoader,
                organizacaoLoader,
                statusAgendamentoLoader);
    }

    public static void setStatusAgendamentoAtendidoByAgendamento(Agendamento pAgendamento) {

        pAgendamento.setStatusAgendamento(StatusAgendamentoLoader.findById(StatusAgendamento.ATENDIDO));
    }

    public void create(Agendamento agendamento) {
        agendamentoRepository.create(agendamento);
    }

    public void update(Agendamento agendamento) {
        agendamentoRepository.update(agendamento);
    }

    public void delete(Agendamento agendamento) {
        agendamentoRepository.delete(agendamento);
    }

    public Response addAgendamento(AgendamentoDTO pAgendamentoDTO, Boolean reagendar) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());
            agendamento = new Agendamento();

            Agendamento pAgendamento = new Agendamento(pAgendamentoDTO, context);

            loadAgendamentoByPessoaData(pAgendamento);

            validaAgendamentoJaRealizado();

            loadPessoaProfissionalOrganizacaoTipoAgendamentoByAgendamento(pAgendamentoDTO, reagendar);

            if (!responses.hasMessages()) {

                agendamento = new Agendamento(pAgendamento, null, tipoAgendamento, pessoa, profissional,
                        statusAgendamento, organizacao, context);

                create(agendamento);

                responses.setData(new AgendamentoDTO(agendamento));
                responses.setMessages("Agendamento cadastrado com sucesso!");
                responses.setStatus(201);

            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateAgendamento(@NotNull AgendamentoDTO agendamentoDTO, Boolean reagendar) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Agendamento pAgendamento = new Agendamento(agendamentoDTO, context);

            loadAgendamentoById(pAgendamento);

            loadPessoaProfissionalOrganizacaoTipoAgendamentoByAgendamento(agendamentoDTO, reagendar);

            loadAgendamentoOldById(pAgendamento);

            agendamento = agendamento.agendamento(agendamentoOld, pAgendamento, tipoAgendamento, pessoa, profissional,
                    statusAgendamento, organizacao, context);

            update(agendamento);

            responses.setData(new AgendamentoDTO(agendamento));
            responses.setMessages("Agendamento remarcado com sucesso!");
            responses.setStatus(200);

        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteAgendamento(@NotNull List<Long> pListIdAgendamento) {

        try {

            List<Agendamento> agendamentos;
            List<Agendamento> agendamentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            agendamentos = AgendamentoLoader.listByIds(pListIdAgendamento);
            int count = agendamentos.size();

            if (agendamentosDisponiveis(agendamentos)) {

                agendamentos.forEach((agendamento) -> {

                    Agendamento agendamentoDeleted = agendamento.cancelarAgendamento(agendamento, context);

                    delete(agendamentoDeleted);

                    agendamentosAux.add(agendamentoDeleted);
                });

                responses.setMessages(Responses.DELETED, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response reactivateAgendamento(@NotNull List<Long> pListIdAgendamento) {

        try {


            List<Agendamento> agendamentos;
            List<Agendamento> agendamentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            agendamentos = AgendamentoLoader.listByIds(pListIdAgendamento);
            int count = agendamentos.size();

            if (agendamentosDisponiveis(agendamentos)) {

                agendamentos.forEach((agendamento) -> {

                    Agendamento agendamentoReactivated = agendamento.marcarComoLivre(agendamento, context);

                    update(agendamentoReactivated);

                    agendamentosAux.add(agendamentoReactivated);

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

    public Response marcarAgendamento(@NotNull AgendamentoDTO pAgendamento) {

        try {

            responses = new Responses();

            if (agendamentoValidator.checkDataRemarcacaoInvalida(responses, pAgendamento)) {
                return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
            }

            loadProfissionalConfiguradorAgendamentoPessoaOrganizacaoByAgendamento(pAgendamento);

            agendamento = new Agendamento(pAgendamento);

            validaDisponibilidadeAgendamento(pAgendamento, Boolean.FALSE);

            if (!responses.hasMessages()) {

                agendamento = new Agendamento(agendamento, null, tipoAgendamento, pessoa, profissional, statusAgendamento,
                        organizacao, context);

                create(agendamento);
                responses.setData(new AgendamentoDTO(agendamento));
                responses.setDatas(new ArrayList<>());
                responses.getDatas().add(new AgendamentoDTO(agendamento));
                responses.setMessages(new ArrayList<>());
                responses.setMessages("Agendamento marcado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response remarcarAgendamento(@NotNull List<AgendamentoDTO> pListAgendamento) {

        try {

            responses = new Responses();

            AgendamentoDTO agendamentoOldDTO = new AgendamentoDTO();
            AgendamentoDTO agendamentoNewDTO = new AgendamentoDTO();
            Agendamento agendamentoOld = new Agendamento();
            Agendamento agendamentoNew = new Agendamento();
            Agendamento agendamentoOldAux;

            if (BasicFunctions.isNotEmpty(pListAgendamento)) {

                agendamentoNewDTO = pListAgendamento.stream().filter(agendamento -> BasicFunctions.isInvalid(agendamento.getId())).findFirst().orElse(null);
                agendamentoOldDTO = pListAgendamento.stream().filter(agendamento -> BasicFunctions.isValid(agendamento.getId())).findFirst().orElse(null);

                if (BasicFunctions.isValid(agendamentoOldDTO)) {
                    agendamentoOld = AgendamentoLoader.findById(agendamentoOldDTO.getId());
                    if (BasicFunctions.isNotEmpty(agendamentoOld) && agendamentoOld.hasAgendamentoOld()) {
                        agendamentoOldAux = agendamentoOld.getAgendamentoOld();
                        validaRemarcacaoExistente(agendamentoOldAux);
                    }
                }
            }

            if (agendamentoValidator.checkDataRemarcacaoInvalida(responses, agendamentoNewDTO)) {
                responses.setStatus(400);
            }

            loadProfissionalConfiguradorAgendamentoPessoaOrganizacaoByAgendamento(agendamentoNewDTO);

            agendamento = new Agendamento(agendamentoNewDTO);

            validaDisponibilidadeAgendamento(agendamentoNewDTO, Boolean.TRUE);

            if (BasicFunctions.isNotEmpty(agendamentoOldDTO)) {
                alterarStatusAgendamentoRemarcado(agendamentoOldDTO);
            }
            if (!responses.hasMessages()) {

                agendamento = new Agendamento(agendamento, agendamentoOld, tipoAgendamento, pessoa, profissional,
                        statusAgendamento, organizacao, context);

                create(agendamento);
                responses.setData(new AgendamentoDTO(agendamento));
                responses.setDatas(new ArrayList<>());
                responses.getDatas().add(new AgendamentoDTO(agendamento));
                responses.getDatas().add(agendamentoOldDTO);
                responses.setMessages(new ArrayList<>());
                responses.setMessages("Agendamento remarcado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public void alterarStatusAgendamentoRemarcado(AgendamentoDTO pAgendamento) {
        Agendamento agendamento;
        if (BasicFunctions.isValid(pAgendamento)) {
            agendamento = AgendamentoLoader.findById(pAgendamento.getId());
            if (BasicFunctions.isValid(agendamento)) {
                agendamento.setStatusAgendamento(StatusAgendamento.statusRemarcado());
                delete(agendamento);
            }
        }
    }

    public Response checkDataValida(AgendamentoDTO pAgendamento, Boolean reagendar) {
        return agendamentoValidator.checkDataValida(pAgendamento, reagendar);
    }

    private void loadProfissionalConfiguradorAgendamentoPessoaOrganizacaoByAgendamento(
            @NotNull AgendamentoDTO pAgendamento) {

        if (BasicFunctions.isNotEmpty(pAgendamento.getProfissionalAgendamento())
                && BasicFunctions.isValid(pAgendamento.getProfissionalAgendamento())) {
            profissional = usuarioLoader.loadUsuarioByOrganizacao(pAgendamento);
            configuradorAgendamento = configuradorAgendamentoService
                    .loadConfiguradorByUsuarioOrganizacao(pAgendamento);
        }

        pessoa = pessoaLoader.loadPessoaByAgendamento(pAgendamento);

        organizacao = organizacaoLoader.loadByOrganizacao(pAgendamento);

        tipoAgendamento = tipoAgendamentoLoader.loadTipoAgendamentoByAgendamento(pAgendamento);
    }

    private void loadAgendamentoById(Agendamento pAgendamento) {

        agendamento = agendamentoLoader.loadAgendamentoById(pAgendamento);
    }

    private void loadAgendamentoByPessoaData(Agendamento pAgendamento) {


        agendamento = agendamentoLoader.findByPessoaDataAgendamento(pAgendamento);
    }

    private void loadPessoaProfissionalOrganizacaoTipoAgendamentoByAgendamento(@NotNull AgendamentoDTO pAgendamento,
                                                                               Boolean reagendar) {

        statusAgendamento = statusAgendamentoLoader.loadByAgendamento(pAgendamento);

        profissional = usuarioLoader.loadByAgendamento(pAgendamento);

        pessoa = pessoaLoader.loadPessoaByAgendamento(pAgendamento);

        organizacao = organizacaoLoader.loadByOrganizacao(pAgendamento);

        tipoAgendamento = tipoAgendamentoLoader.loadTipoAgendamentoByAgendamento(pAgendamento);

        agendamentoValidator.validaAgendamento(pAgendamento, agendamento, profissional, organizacao, reagendar);
    }

    private void loadAgendamentoOldById(Agendamento pAgendamento) {

        agendamentoOld = new Agendamento();

        agendamentoOld = agendamentoLoader.findById(pAgendamento);

    }


    public Response listAgendamentosByPessoa(AgendamentoDTO pAgendamento, Boolean reagendar) {

        try {

            responses = new Responses();

            List<Agendamento> agendamentos = agendamentoLoader.listByPessoaDataAgendamentoStatusAgendadoAndReagendar(pAgendamento, reagendar);

            List<AgendamentoDTO> agendamentoDTO = toDTO(agendamentos);
            if (agendamentosDTODisponiveis(agendamentoDTO)) {
                responses.setDatas(Collections.singletonList(agendamentoDTO));
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(LocalDate dataAgendamento, LocalDate dataInicio, LocalDate dataFim, LocalTime horarioAgendamento, LocalTime horarioInicio, LocalTime horarioFim, Long pessoaId, String nomePessoa, String nomeProfissional, Long idStatus, Long organizacaoId, Long tipoAgendamentoId, Long profissionalId, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryFilter = makeAgendamentoQueryStringByFilters(dataAgendamento, dataInicio, dataFim, horarioAgendamento, horarioInicio, horarioFim, pessoaId, nomePessoa, nomeProfissional, idStatus, organizacaoId, tipoAgendamentoId, profissionalId, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            List<Agendamento> agendamentos = agendamentoLoader.list(queryFilter);

            return Response.ok(toDTO(agendamentos)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(LocalDate dataAgendamento, LocalDate dataInicio, LocalDate dataFim, LocalTime horarioAgendamento, LocalTime horarioInicio, LocalTime horarioFim, Long pessoaId, String nomePessoa, String nomeProfissional, Long idStatus, Long organizacaoId, Long tipoAgendamentoId, Long profissionalId, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryFilter = makeAgendamentoQueryStringByFilters(dataAgendamento, dataInicio, dataFim, horarioAgendamento, horarioInicio, horarioFim, pessoaId, nomePessoa, nomeProfissional, idStatus, organizacaoId, tipoAgendamentoId, profissionalId, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            Integer count = agendamentoLoader.count(queryFilter);

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

            agendamento = AgendamentoLoader.findById(pId);

            if (BasicFunctions.isEmpty(agendamento)) {
                responses.setMessages("Agendamento não localizado.");
                responses.setStatus(400);
            }
            return Response.ok(new AgendamentoDTO(agendamento)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaDisponibilidadeAgendamento(AgendamentoDTO pAgendamento, Boolean remarcacao) {

        if (BasicFunctions.isValid(pAgendamento.getDataAgendamento())) {

            Responses dataValida = agendamentoAutomaticoValidator.validarDataAgendamento(pAgendamento, remarcacao);

            if (BasicFunctions.isNotNull(dataValida) && !dataValida.getOk()) {

                String dia = StringFunctions.nomeSemana(pAgendamento.getDataAgendamento());

                profissional = usuarioLoader.loadByAgendamento(pAgendamento);

                responses.setMessages(new ArrayList<>());

                if (remarcacao) {

                    responses.getMessages()
                            .add("Não foi possível remarcar o Agendamento, pois o profissional " + profissional.nomeProfissional()
                                    + " não está disponível para atendimento nesse " + dia + ": " + pAgendamento.getDataAgendamento());
                }

                if (!remarcacao) {
                    responses.getMessages()
                            .add("Não foi possível marcar o Agendamento, pois o profissional " + profissional.nomeProfissional()
                                    + " não está disponível para atendimento nesse " + dia + ": "
                                    + agendamento.getDataAgendamento());
                }
                responses.setStatus(400);
            }
        }

        if (BasicFunctions.isInvalid(pAgendamento.getDataAgendamento())) {
            responses.setMessages("Por favor, informe a Data da Consulta corretamente!");
        }
        if (BasicFunctions.isEmpty(pAgendamento.getHorarioAgendamento())) {
            responses.setMessages("Por favor, informe o horário da Consulta corretamente!");
        }

        if (BasicFunctions.isEmpty(organizacao)) {
            responses.setMessages("Por favor, selecione o Local do Atendimento corretamente!");
        }
    }

    private void validaAgendamentoJaRealizado() {

        if (BasicFunctions.isNotEmpty(agendamento)) {
            responses.setData(agendamento);
            responses.setMessages("Agendamento já realizado!");
            responses.setStatus(400);
        }
    }

    public Boolean agendamentosDTODisponiveis(List<AgendamentoDTO> agendamentosDTO) {

        List<Agendamento> agendamentos = new ArrayList<>();

        agendamentosDTO.forEach(agendamentoDTO -> agendamentos.add(new Agendamento(agendamentoDTO, context)));

        return agendamentosDisponiveis(agendamentos);
    }

    public Boolean agendamentosDisponiveis(List<Agendamento> agendamentos) {

        if (BasicFunctions.isEmpty(agendamentos)) {
            responses.setMessages("Agendamentos não localizados ou já excluídos.");
            responses.setStatus(400);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void validaRemarcacaoExistente(Agendamento agendamentoOldAux) {
        if (agendamentoOldAux.getStatusAgendamento().remarcado()) {

            responses.setMessages(new ArrayList<>());
            responses.getMessages()
                    .add("Não foi possível remarcar o Agendamento, pois o mesmo já possui uma remarcação.");
            responses.setStatus(400);
        }
    }

    private List<AgendamentoDTO> toDTO(List<Agendamento> entityList) {

        List<AgendamentoDTO> entityDTOList = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(entityList)) {
            entityList.forEach(entity -> {
                entityDTOList.add(new AgendamentoDTO(entity));
            });
        }
        return entityDTOList;
    }
}
