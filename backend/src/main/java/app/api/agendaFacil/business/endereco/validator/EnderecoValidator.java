package app.api.agendaFacil.business.endereco.validator;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoValidator {

    final EnderecoValidator enderecoValidator;

    public EnderecoValidator(EnderecoValidator enderecoValidator) {
        this.enderecoValidator = enderecoValidator;
    }

    public Boolean validaEndereco(Endereco pEndereco) {

        if (BasicFunctions.isEmpty(pEndereco)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
