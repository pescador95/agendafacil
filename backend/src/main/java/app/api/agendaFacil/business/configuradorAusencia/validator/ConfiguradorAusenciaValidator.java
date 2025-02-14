package app.api.agendaFacil.business.configuradorAusencia.validator;

import app.api.agendaFacil.business.configuradorAusencia.DTO.ConfiguradorAusenciaDTO;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfiguradorAusenciaValidator {

    final ConfiguradorAusenciaValidator configuradorAusenciaValidator;

    public ConfiguradorAusenciaValidator(ConfiguradorAusenciaValidator configuradorAusenciaValidator) {
        this.configuradorAusenciaValidator = configuradorAusenciaValidator;
    }

    public Boolean validarConfiguradorAusencia(ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        if (BasicFunctions.isEmpty(pConfiguradorAusencia)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
