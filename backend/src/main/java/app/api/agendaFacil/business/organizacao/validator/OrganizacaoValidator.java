package app.api.agendaFacil.business.organizacao.validator;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.configuradorFeriado.entity.ConfiguradorFeriado;
import app.api.agendaFacil.business.configuradorFeriado.repository.ConfiguradorFeriadoRepository;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class OrganizacaoValidator {

    final OrganizacaoValidator organizacaoValidator;
    final ConfiguradorAgendamentoLoader configuradorAgendamentoLoader;

    public OrganizacaoValidator(OrganizacaoValidator organizacaoValidator, ConfiguradorAgendamentoLoader configuradorAgendamentoLoader) {
        this.organizacaoValidator = organizacaoValidator;
        this.configuradorAgendamentoLoader = configuradorAgendamentoLoader;
    }

    public Boolean organizacaoNaoPossuiConfiguracao(AgendamentoDTO pAgendamento) {

        Set<ConfiguradorAgendamento> configuradoresAgendamento = configuradorAgendamentoLoader.listConfiguradoresByOrganizacao(pAgendamento);

        return BasicFunctions.isEmpty(configuradoresAgendamento);
    }

    public Boolean organizacaoNaoAtendeFimDeSemana(AgendamentoDTO pAgendamento) {

        Set<ConfiguradorAgendamento> configuradoresAgendamento = configuradorAgendamentoLoader.listConfiguradoresByOrganizacao(pAgendamento);

        return BasicFunctions.isNotEmpty(configuradoresAgendamento) && configuradoresAgendamento.stream()
                .allMatch(x -> x.naoAtendeFimDeSemana(pAgendamento.getDataAgendamento()));
    }

    public Boolean organizacaoEmFeriado(AgendamentoDTO pAgendamento, Organizacao organizacao) {

        ConfiguradorFeriado configuradorFeriado = ConfiguradorFeriadoRepository
                .findByDataFeriado(pAgendamento.getDataAgendamento());

        return BasicFunctions.isNotEmpty(configuradorFeriado) && (configuradorFeriado.getOrganizacoesFeriado().isEmpty()
                || configuradorFeriado.getOrganizacoesFeriado().contains(organizacao));
    }
}
