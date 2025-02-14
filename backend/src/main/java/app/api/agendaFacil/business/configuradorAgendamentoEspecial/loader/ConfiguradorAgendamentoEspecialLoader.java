package app.api.agendaFacil.business.configuradorAgendamentoEspecial.loader;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.entity.ConfiguradorAgendamentoEspecial;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.repository.ConfiguradorAgendamentoEspecialRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ConfiguradorAgendamentoEspecialLoader {

    final ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoEspecialLoader;
    final ConfiguradorAgendamentoEspecialRepository configuradorAgendamentoEspecialRepository;

    public ConfiguradorAgendamentoEspecialLoader(ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoLoader, ConfiguradorAgendamentoEspecialRepository configuradorAgendamentoRepository) {
        this.configuradorAgendamentoEspecialLoader = configuradorAgendamentoLoader;
        this.configuradorAgendamentoEspecialRepository = configuradorAgendamentoRepository;
    }

    public static PanacheQuery<ConfiguradorAgendamentoEspecial> find(String query) {
        return ConfiguradorAgendamentoEspecialRepository.find(query);
    }

    public List<ConfiguradorAgendamentoEspecial> listByIds(List<Long> pListIdConfiguradorAgendamento) {

        if (BasicFunctions.isNotEmpty(pListIdConfiguradorAgendamento)) {
            return ConfiguradorAgendamentoEspecialRepository.listByIds(pListIdConfiguradorAgendamento);
        }
        return null;
    }

    public ConfiguradorAgendamentoEspecial findById(ConfiguradorAgendamentoEspecial pConfiguradorAgendamentoEspecial) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial, pConfiguradorAgendamentoEspecial.getOrganizacaoConfigurador())
                && BasicFunctions.isValid(pConfiguradorAgendamentoEspecial.getOrganizacaoConfigurador())) {
            return ConfiguradorAgendamentoEspecialRepository.findById(pConfiguradorAgendamentoEspecial.getId());
        }
        return null;
    }

    public ConfiguradorAgendamentoEspecial findByConfiguradorAgendamentoEspecial(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial, pConfiguradorAgendamentoEspecial.getOrganizacaoConfigurador())
                && BasicFunctions.isValid(pConfiguradorAgendamentoEspecial.getOrganizacaoConfigurador())) {
            return ConfiguradorAgendamentoEspecialRepository.findByConfiguradorAgendamentoEspecial(new ConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial));
        }
        return null;
    }

    public ConfiguradorAgendamentoEspecial findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return ConfiguradorAgendamentoEspecialRepository.findById(pId);
        }
        return null;
    }

    public List<ConfiguradorAgendamentoEspecial> listByProfissional(Usuario pUsuario) {
        if (BasicFunctions.isNotEmpty(pUsuario, pUsuario.getId())) {
            return ConfiguradorAgendamentoEspecialRepository.listByProfissional(pUsuario);
        }
        return null;
    }

    public List<ConfiguradorAgendamentoEspecial> list(QueryFilter queryFilter) {
        return configuradorAgendamentoEspecialRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return configuradorAgendamentoEspecialRepository.count(queryFilter);
    }
}