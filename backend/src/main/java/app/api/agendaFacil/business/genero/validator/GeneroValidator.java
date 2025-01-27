package app.api.agendaFacil.business.genero.validator;

import app.api.agendaFacil.business.genero.DTO.GeneroDTO;
import app.api.agendaFacil.business.genero.entity.Genero;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeneroValidator {

    final GeneroValidator generoValidator;

    public GeneroValidator(GeneroValidator generoValidator) {
        this.generoValidator = generoValidator;
    }

    public Boolean validarGenero(GeneroDTO pGenero) {
        if (BasicFunctions.isEmpty(pGenero) || BasicFunctions.isEmpty(pGenero.getGenero())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean validarGenero(Genero pGenero) {
        if (BasicFunctions.isEmpty(pGenero) || BasicFunctions.isEmpty(pGenero.getGenero())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
