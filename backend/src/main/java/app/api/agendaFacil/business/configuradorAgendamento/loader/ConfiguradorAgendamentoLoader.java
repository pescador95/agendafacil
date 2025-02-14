package app.api.agendaFacil.business.configuradorAgendamento.loader;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.configuradorAgendamento.repository.ConfiguradorAgendamentoRepository;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ConfiguradorAgendamentoLoader {

    final ConfiguradorAgendamentoLoader configuradorAgendamentoLoader;
    final ConfiguradorAgendamentoRepository configuradorAgendamentoRepository;

    public ConfiguradorAgendamentoLoader(ConfiguradorAgendamentoLoader configuradorAgendamentoLoader, ConfiguradorAgendamentoRepository configuradorAgendamentoRepository) {
        this.configuradorAgendamentoLoader = configuradorAgendamentoLoader;
        this.configuradorAgendamentoRepository = configuradorAgendamentoRepository;
    }

    public static PanacheQuery<ConfiguradorAgendamento> find(String query) {
        return ConfiguradorAgendamentoRepository.find(query);
    }

    public Set<ConfiguradorAgendamento> listConfiguradoresByOrganizacao(AgendamentoDTO pAgendamento) {

        Set<ConfiguradorAgendamento> configuradorAgendamentoList = new HashSet<>();

        if (BasicFunctions.isNotEmpty(pAgendamento.getOrganizacaoAgendamento())
                && BasicFunctions.isValid(pAgendamento.getOrganizacaoAgendamento())) {

            return configuradorAgendamentoRepository.loadListConfiguradorAgendamentoByOrganizacaoSet(new Agendamento(pAgendamento));
        }
        return configuradorAgendamentoList;
    }

    public ConfiguradorAgendamento loadConfiguradorByUsuarioOrganizacao(AgendamentoDTO pAgendamento) {

        ConfiguradorAgendamento configuradorAgendamento = new ConfiguradorAgendamento();

        if (BasicFunctions.isNotEmpty(pAgendamento.getOrganizacaoAgendamento()) && BasicFunctions.isValid(pAgendamento.getOrganizacaoAgendamento())
                && BasicFunctions.isNotEmpty(pAgendamento.getProfissionalAgendamento()) && BasicFunctions.isValid(pAgendamento.getProfissionalAgendamento())) {
            return configuradorAgendamentoRepository.loadConfiguradorAgendamentoByOrganizacaoProfissional(new Agendamento(pAgendamento));
        }
        return configuradorAgendamento;
    }

    public List<ConfiguradorAgendamento> listByIds(List<Long> pListIdConfiguradorAgendamento) {
        if (BasicFunctions.isNotEmpty(pListIdConfiguradorAgendamento)) {
            return ConfiguradorAgendamentoRepository.listByIds(pListIdConfiguradorAgendamento);
        }
        return null;
    }

    public ConfiguradorAgendamento findByConfiguradorAgendamento(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamento, pConfiguradorAgendamento.getOrganizacaoConfigurador())
                && BasicFunctions.isValid(pConfiguradorAgendamento.getOrganizacaoConfigurador())) {
            return ConfiguradorAgendamentoRepository.findByOrganizacao(new Organizacao(pConfiguradorAgendamento.getOrganizacaoConfigurador()));
        }
        return null;
    }

    public ConfiguradorAgendamento findByOrganizacaoProfissional(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamento, pConfiguradorAgendamento.getOrganizacaoConfigurador(), pConfiguradorAgendamento.getProfissionalConfigurador())) {
            return ConfiguradorAgendamentoRepository.findByOrganizacaoProfissional(new ConfiguradorAgendamento(pConfiguradorAgendamento));
        }
        return null;
    }

    public ConfiguradorAgendamento findById(Long pId) {
        return ConfiguradorAgendamentoRepository.findById(pId);
    }

    public List<ConfiguradorAgendamento> listByProfissional(Usuario pUsuario) {
        if (BasicFunctions.isNotEmpty(pUsuario, pUsuario.getId())) {
            return ConfiguradorAgendamentoRepository.ListByProfissional(pUsuario);
        }
        return null;
    }

    public List<ConfiguradorAgendamento> list(QueryFilter queryFilter) {
        return configuradorAgendamentoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return configuradorAgendamentoRepository.count(queryFilter);
    }
}
