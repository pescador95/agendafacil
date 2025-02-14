package app.api.agendaFacil.business.historicoPessoa.validator;

import app.api.agendaFacil.business.historicoPessoa.DTO.HistoricoPessoaDTO;
import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HistoricoPessoaValidator {

    final HistoricoPessoaValidator historicoPessoaValidator;

    public HistoricoPessoaValidator(HistoricoPessoaValidator historicoPessoaValidator) {
        this.historicoPessoaValidator = historicoPessoaValidator;
    }

    public Boolean validarHistoricoPessoa(HistoricoPessoa pHistoricoPessoa) {
        if (BasicFunctions.isInvalid(pHistoricoPessoa) && BasicFunctions.isEmpty(pHistoricoPessoa.getPessoa(), pHistoricoPessoa.getQueixaPrincipal(), pHistoricoPessoa.getMedicamentos(), pHistoricoPessoa.getDiagnosticoClinico(), pHistoricoPessoa.getComorbidades(), pHistoricoPessoa.getOcupacao(), pHistoricoPessoa.getResponsavelContato())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean validarHistoricoPessoa(HistoricoPessoaDTO pHistoricoPessoa) {
        if (BasicFunctions.isEmpty(pHistoricoPessoa.getId(), pHistoricoPessoa.getPessoa(), pHistoricoPessoa.getQueixaPrincipal(), pHistoricoPessoa.getMedicamentos(), pHistoricoPessoa.getDiagnosticoClinico(), pHistoricoPessoa.getComorbidades(), pHistoricoPessoa.getOcupacao(), pHistoricoPessoa.getResponsavelContato())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
