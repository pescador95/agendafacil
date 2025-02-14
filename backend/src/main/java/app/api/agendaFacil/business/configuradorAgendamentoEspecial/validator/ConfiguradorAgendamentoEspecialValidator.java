package app.api.agendaFacil.business.configuradorAgendamentoEspecial.validator;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfiguradorAgendamentoEspecialValidator {

    final ConfiguradorAgendamentoEspecialValidator configuradorAgendamentoEspecialValidator;

    public ConfiguradorAgendamentoEspecialValidator(ConfiguradorAgendamentoEspecialValidator configuradorAgendamentoEspecialValidator) {
        this.configuradorAgendamentoEspecialValidator = configuradorAgendamentoEspecialValidator;
    }

    public Responses validaConfiguradorEspecial(Responses responses, ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial, Organizacao organizacao, Usuario usuario) {

        if (BasicFunctions.isEmpty(organizacao)) {
            responses.setMessages("Por favor, informe a Organização do Configurador Especial corretamente!");
            responses.setStatus(400);
        }
        if (BasicFunctions.isEmpty(usuario)) {
            responses.setMessages("Por favor, informe o profissional do Configurador Especial corretamente!");
            responses.setStatus(400);
        }
        if (BasicFunctions.isEmpty(pConfiguradorAgendamentoEspecial)) {
            responses.setMessages("Informe os dados para atualizar o Configurador de Agendamento Especial.");
            responses.setStatus(400);
        }
        return responses;
    }
}
