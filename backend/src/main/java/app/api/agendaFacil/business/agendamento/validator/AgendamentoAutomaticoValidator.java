package app.api.agendaFacil.business.agendamento.validator;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.organizacao.validator.OrganizacaoValidator;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import app.core.helpers.utils.StringFunctions;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;

@ApplicationScoped
public class AgendamentoAutomaticoValidator {

    final AgendamentoAutomaticoValidator agendamentoAutomaticoValidator;
    final AgendamentoValidator agendamentoValidator;
    final OrganizacaoValidator organizacaoValidator;
    private Responses responses;
    private Organizacao organizacao;

    public AgendamentoAutomaticoValidator(AgendamentoAutomaticoValidator agendamentoAutomaticoValidator, OrganizacaoValidator organizacaoValidator, AgendamentoValidator agendamentoValidator) {
        this.agendamentoAutomaticoValidator = agendamentoAutomaticoValidator;
        this.organizacaoValidator = organizacaoValidator;
        this.agendamentoValidator = agendamentoValidator;
    }


    public Responses validarDataAgendamento(AgendamentoDTO pAgendamento, Boolean reagendar) {
        return validarDataAgendamento(pAgendamento, reagendar, null);
    }

    public Responses validarDataAgendamento(AgendamentoDTO pAgendamento, Boolean reagendar, Boolean comPreferencia) {

        responses = new Responses();

        LocalDate dataContexto = Contexto.dataContexto(pAgendamento.getOrganizacaoAgendamento());

        if (dataInvalida(pAgendamento)) {

            responses.setData(pAgendamento);
            responses.setMessages("A data escolhida :" + pAgendamento.getDataAgendamento() + " é anterior a data de hoje: "
                    + dataContexto);
            responses.setStatus(400);
            return responses;
        }

        if (organizacaoValidator.organizacaoEmFeriado(pAgendamento, organizacao)) {
            responses.setData(pAgendamento);
            responses.setMessages("A Organização " + organizacao.getNome() + " Está de feriado.");
            responses.setStatus(400);
            return responses;
        }
        if (organizacaoValidator.organizacaoNaoPossuiConfiguracao(pAgendamento)) {
            responses.setData(pAgendamento);
            responses.setMessages("A Organização " + organizacao.getNome() + " não possui atendimentos configurado.");
            responses.setStatus(400);
        }
        if (organizacaoValidator.organizacaoNaoAtendeFimDeSemana(pAgendamento)) {
            responses.setData(pAgendamento);
            responses.setMessages("A Organização " + organizacao.getNome() + " não atende " + StringFunctions.nomeSemana(pAgendamento.getDataAgendamento()) + ".");
            responses.setStatus(400);
            return responses;
        }
        if (agendamentoIndisponivel(pAgendamento, reagendar)) {
            responses.setData(pAgendamento);
            responses.setMessages("A data " + pAgendamento.getDataAgendamento() + " Está indisponível para agendamento.");
            responses.setStatus(400);
            return responses;
        }

        if (BasicFunctions.isNotNull(comPreferencia)) {
            checkPreferencia(pAgendamento, comPreferencia);
        }

        return responses;
    }

    public Boolean agendamentoIndisponivel(AgendamentoDTO pAgendamento, Boolean reagendar) {


        Boolean dataDisponivel = agendamentoValidator.checkAgendamentoDataHorarioPessoaDisponivel(pAgendamento, reagendar);

        return !dataDisponivel;

    }


    public Boolean dataInvalida(AgendamentoDTO pAgendamento) {

        organizacao = OrganizacaoRepository.findById(pAgendamento.getOrganizacaoAgendamento().getId());

        if (BasicFunctions.isNotEmpty(organizacao)) {

            LocalDate dataAgendamento = pAgendamento.getDataAgendamento();

            LocalDate dataContexto = Contexto.dataContexto(organizacao.getZoneId());

            return dataAgendamento.isBefore(dataContexto);
        }
        return Boolean.TRUE;
    }

    public void checkPreferencia(AgendamentoDTO pAgendamento, Boolean comPreferencia) {
        if (BasicFunctions.isNull(comPreferencia)) {
            responses.setData(pAgendamento);
            responses.setMessages("Por favor, informe se há preferência por usuário ou não no atendimento.");
            responses.setStatus(400);
        }
    }
}
