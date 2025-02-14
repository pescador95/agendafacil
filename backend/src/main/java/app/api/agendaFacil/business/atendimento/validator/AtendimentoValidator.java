package app.api.agendaFacil.business.atendimento.validator;

import app.api.agendaFacil.business.atendimento.entity.Atendimento;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtendimentoValidator {

    final AtendimentoValidator atendimentoValidator;

    public AtendimentoValidator(AtendimentoValidator atendimentoValidator) {
        this.atendimentoValidator = atendimentoValidator;
    }

    public Boolean validarAtendimento(Atendimento pAtendimento) {
        if (BasicFunctions.isEmpty(pAtendimento) || BasicFunctions.isInvalid(pAtendimento)
                && BasicFunctions.isEmpty(pAtendimento.getAvaliacao(), pAtendimento.getEvolucaoSintomas())
                && BasicFunctions.isInvalid(pAtendimento.getDataAtendimento())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
