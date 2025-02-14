package app.api.agendaFacil.business.tipoAgendamento.validator;

import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoAgendamentoValidator {

    final TipoAgendamentoValidator tipoAgendamentoValidator;

    public TipoAgendamentoValidator(TipoAgendamentoValidator tipoAgendamentoValidator) {
        this.tipoAgendamentoValidator = tipoAgendamentoValidator;
    }

    public Boolean validaTipoAgendamento(TipoAgendamentoDTO pTipoAgendamento) {

        if (BasicFunctions.isInvalid(pTipoAgendamento) && BasicFunctions.isEmpty(pTipoAgendamento.getTipoAgendamento())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
