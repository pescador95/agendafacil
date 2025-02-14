package app.api.agendaFacil.business.configuradorFeriado.validator;

import app.api.agendaFacil.business.configuradorFeriado.DTO.ConfiguradorFeriadoDTO;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfiguradorFeriadoValidator {

    final ConfiguradorFeriadoValidator configuradorFeriadoValidator;

    public ConfiguradorFeriadoValidator(ConfiguradorFeriadoValidator configuradorFeriadoValidator) {
        this.configuradorFeriadoValidator = configuradorFeriadoValidator;
    }

    public Boolean validaConfiguradorFeriado(ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        if (BasicFunctions.isEmpty(pConfiguradorFeriado)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
