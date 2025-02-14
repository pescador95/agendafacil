package app.api.agendaFacil.business.agendamento.validator;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.agendamento.loader.AgendamentoLoader;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgendamentoValidator {

    final AgendamentoValidator agendamentoValidator;
    final AgendamentoAutomaticoValidator agendamentoAutomaticoValidator;
    final AgendamentoLoader agendamentoLoader;
    private Responses responses;

    public AgendamentoValidator(AgendamentoValidator agendamentoValidator, AgendamentoAutomaticoValidator agendamentoAutomaticoValidator, AgendamentoLoader agendamentoLoader) {
        this.agendamentoValidator = agendamentoValidator;
        this.agendamentoLoader = agendamentoLoader;
        this.agendamentoAutomaticoValidator = agendamentoAutomaticoValidator;
    }

    public Boolean checkDataRemarcacaoInvalida(Responses responses, AgendamentoDTO agendamentoNew) {
        List<Agendamento> agendamentosProfissional;
        agendamentosProfissional = agendamentoLoader
                .loadListAgendamentosByUsuarioDataAgenda(agendamentoNew);

        Boolean checkDatahorario = checkDatahorarioDisponivelByDataHorarioProfissional(agendamentosProfissional,
                agendamentoNew);

        if (checkDatahorario) {

            responses.setStatus(400);
            responses.setMessages(new ArrayList<>());
            responses.setMessages(
                    "Não foi possível remarcar o Agendamento, pois o Profissional já possui um Agendamento para a mesma data e horário.");
            return true;
        }
        return false;
    }

    public Boolean checkDatahorarioDisponivelByDataHorarioProfissional(List<Agendamento> agendamentosProfissional,
                                                                       AgendamentoDTO agendamentoNew) {
        if (BasicFunctions.isEmpty(agendamentosProfissional)) {
            return Boolean.FALSE;
        }
        return agendamentosProfissional.stream()
                .anyMatch(agendamento -> agendamento.getHorarioAgendamento()
                        .equals(agendamentoNew.getHorarioAgendamento())
                        && agendamento.getDataAgendamento().isEqual(agendamentoNew.getDataAgendamento())
                        && agendamento.getStatusAgendamento().agendado() && agendamento.getProfissionalAgendamento().getId().equals(agendamentoNew.getProfissionalAgendamento().getId()));
    }

    public void checkDataInvalidaAgendamento(@NotNull AgendamentoDTO pAgendamento, Usuario profissional, Boolean reagendar,
                                             Responses responses) {

        Responses dataValida = agendamentoAutomaticoValidator.validarDataAgendamento(pAgendamento, reagendar);

        if (BasicFunctions.isNotNull(dataValida) && !dataValida.getOk()) {
            String dia = StringFunctions.nomeSemana(pAgendamento.getDataAgendamento());

            responses.setStatus(400);
            responses.setMessages(new ArrayList<>());
            responses.getMessages()
                    .add("Não foi possível atualizar o Agendamento, pois o profissional " + profissional.getLogin()
                            + " não está disponível para atendimento nesse " + dia + ": "
                            + pAgendamento.getDataAgendamento());
        }
    }

    public void checkDataHorarioDisponivelByProfissionalAgendamento(@NotNull AgendamentoDTO pAgendamento, Responses responses) {
        List<Agendamento> agendamentosProfissional;

        agendamentosProfissional = agendamentoLoader
                .loadListAgendamentosByUsuarioDataAgenda(pAgendamento);

        Boolean checkDatahorario = agendamentoValidator.checkDatahorarioDisponivelByDataHorarioProfissional(agendamentosProfissional,
                pAgendamento);

        if (checkDatahorario) {

            responses.setStatus(400);
            responses.setMessages(new ArrayList<>());
            responses.setMessages(
                    "Não foi possível atualizar o Agendamento, pois o Profissional já possui um Agendamento para a mesma data e horário.");
        }
    }

    public Boolean checkAgendamentoDataHorarioPessoaDisponivel(AgendamentoDTO pAgendamento, Boolean reagendar) {

        Agendamento agendamentoExistente = agendamentoLoader.loadAgendamentoByPessoaDataAgendaHorario(pAgendamento);

        if (BasicFunctions.isNotEmpty(agendamentoExistente) && !agendamentoExistente.getId().equals(pAgendamento.getId()) && agendamentoExistente.getStatusAgendamento().agendado() && !reagendar) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void validaAgendamento(AgendamentoDTO pAgendamento, Agendamento agendamento, Usuario profissional, Organizacao organizacao, Boolean reagendar) {

        responses = new Responses();

        if (BasicFunctions.isEmpty(agendamento) || BasicFunctions.isInvalid(agendamento)
                || BasicFunctions.isNotEmpty(agendamento, agendamento.getOrganizacaoAgendamento(), agendamento.getDataAgendamento(), agendamento.getProfissionalAgendamento(), agendamento.getHorarioAgendamento(), agendamento.getStatusAgendamento())
                && !pAgendamento.getHorarioAgendamento().equals(agendamento.getHorarioAgendamento())
                ||
                !pAgendamento.getDataAgendamento().isEqual(agendamento.getDataAgendamento()) ||
                !agendamento.getProfissionalAgendamento().getId()
                        .equals(pAgendamento.getProfissionalAgendamento().getId())
                ||
                !agendamento.getOrganizacaoAgendamento().getId()
                        .equals(pAgendamento.getOrganizacaoAgendamento().getId())) {

            agendamentoValidator.checkDataHorarioDisponivelByProfissionalAgendamento(pAgendamento, responses);

        }
        if (BasicFunctions.isEmpty(pAgendamento) || BasicFunctions.isEmpty(pAgendamento.getDataAgendamento())
                && BasicFunctions.isEmpty(pAgendamento.getProfissionalAgendamento())
                && BasicFunctions.isEmpty(pAgendamento.getPessoaAgendamento())
                && BasicFunctions.isEmpty(pAgendamento.getOrganizacaoAgendamento())) {
            responses.setStatus(400);
            responses.setMessages("Informe os dados para remarcar o Agendamento.");

        }

        if (BasicFunctions.isValid(pAgendamento.getDataAgendamento())) {

            agendamentoValidator.checkDataInvalidaAgendamento(pAgendamento, profissional, reagendar, responses);
        }
        if (BasicFunctions.isInvalid(pAgendamento.getDataAgendamento())) {
            responses.setStatus(400);
            responses.setMessages("Por favor, informe a Data da Consulta corretamente!");
        }
        if (BasicFunctions.isInvalid(pAgendamento.getHorarioAgendamento())) {
            responses.setStatus(400);
            responses.setMessages("Por favor, informe o horário da Consulta corretamente!");
        }
        if (BasicFunctions.isEmpty(organizacao)) {
            responses.setStatus(400);
            responses.setMessages("Por favor, selecione o Local do Atendimento corretamente!");
        }
    }

    public boolean validarDataAgendamento(AgendamentoDTO pAgendamento, Boolean dataValida) {

        responses = new Responses();

        if (!dataValida) {
            responses = new Responses(400);
            responses.setData(pAgendamento);
            responses.setMessages("Não será possível agendar na data " + pAgendamento.getDataAgendamento());
            return false;
        }
        return true;
    }

    public Response checkDataValida(AgendamentoDTO pAgendamento, Boolean reagendar) {

        try {

            responses = new Responses();

            Responses dataValida = agendamentoAutomaticoValidator.validarDataAgendamento(pAgendamento, reagendar);

            Boolean ok = validarDataAgendamento(pAgendamento, dataValida.getOk());

            if (ok) {
                responses = new Responses();
                responses.setData(pAgendamento);
                responses.setMessages(new ArrayList<>());

            }

            if (!ok) {
                responses = new Responses(400);
                responses.setData(pAgendamento);
                responses.setMessages(new ArrayList<>());
                responses.setMessages("a data não está disponível.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }


}
