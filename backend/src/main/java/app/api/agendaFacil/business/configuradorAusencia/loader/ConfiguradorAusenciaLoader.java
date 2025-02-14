package app.api.agendaFacil.business.configuradorAusencia.loader;

import app.api.agendaFacil.business.configuradorAusencia.DTO.ConfiguradorAusenciaDTO;
import app.api.agendaFacil.business.configuradorAusencia.entity.ConfiguradorAusencia;
import app.api.agendaFacil.business.configuradorAusencia.repository.ConfiguradorAusenciaRepository;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ConfiguradorAusenciaLoader {

    final ConfiguradorAusenciaLoader configuradorAusenciaLoader;
    final ConfiguradorAusenciaRepository configuradorAusenciaRepository;

    public ConfiguradorAusenciaLoader(ConfiguradorAusenciaLoader configuradorAusenciaLoader, ConfiguradorAusenciaRepository configuradorAusenciaRepository) {
        this.configuradorAusenciaLoader = configuradorAusenciaLoader;
        this.configuradorAusenciaRepository = configuradorAusenciaRepository;
    }

    public static PanacheQuery<ConfiguradorAusencia> find(String query) {
        return ConfiguradorAusenciaRepository.find(query);
    }

    public List<ConfiguradorAusencia> listByIds(List<Long> pListIdConfiguradorAusencia) {
        return ConfiguradorAusenciaRepository.listByIds(pListIdConfiguradorAusencia);
    }

    public ConfiguradorAusencia findById(ConfiguradorAusenciaDTO pConfiguradorAusencia) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAusencia, pConfiguradorAusencia.getId())) {
            return ConfiguradorAusenciaRepository.findById(pConfiguradorAusencia.getId());
        }
        return null;
    }

    public ConfiguradorAusencia findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return ConfiguradorAusenciaRepository.findById(pId);
        }
        return null;
    }

    public ConfiguradorAusencia findByDataAusencia(ConfiguradorAusenciaDTO pConfiguradorAusencia) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAusencia, pConfiguradorAusencia.getDataInicioAusencia(), pConfiguradorAusencia.getDataFimAusencia())) {
            return ConfiguradorAusenciaRepository.findByDataAusencia(new ConfiguradorAusencia(pConfiguradorAusencia));
        }
        return null;
    }

    public List<ConfiguradorAusencia> list(QueryFilter queryFilter) {
        return configuradorAusenciaRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return configuradorAusenciaRepository.count(queryFilter);
    }
}
