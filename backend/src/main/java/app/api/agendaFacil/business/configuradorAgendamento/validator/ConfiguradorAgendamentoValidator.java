package app.api.agendaFacil.business.configuradorAgendamento.validator;

import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.DayOfWeek;

@ApplicationScoped
public class ConfiguradorAgendamentoValidator {

    final ConfiguradorAgendamentoValidator configuradorAgendamentoValidator;

    public ConfiguradorAgendamentoValidator(ConfiguradorAgendamentoValidator configuradorAgendamentoValidator) {
        this.configuradorAgendamentoValidator = configuradorAgendamentoValidator;
    }

    public static Boolean verificaMakeHorarioByConfiguradorAgendamento(DayOfWeek agendamentoDayOfWeek, ConfiguradorAgendamento pConfigurador) {
        if ((agendamentoDayOfWeek.equals(DayOfWeek.SATURDAY) && pConfigurador.getAtendeSabado()) ||
                (agendamentoDayOfWeek.equals(DayOfWeek.SUNDAY) && pConfigurador.getAtendeDomingo()) ||
                (!agendamentoDayOfWeek.equals(DayOfWeek.SATURDAY) && !agendamentoDayOfWeek.equals(DayOfWeek.SUNDAY))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean validarConfiguradorAgendamento(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {

        if (BasicFunctions.isEmpty(pConfiguradorAgendamento)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean validarConfiguradorAgendamento(ConfiguradorAgendamento pConfiguradorAgendamento) {

        if (BasicFunctions.isEmpty(pConfiguradorAgendamento)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
