package app.api.agendaFacil.business.configuradorFeriado.loader;

import app.api.agendaFacil.business.configuradorFeriado.DTO.ConfiguradorFeriadoDTO;
import app.api.agendaFacil.business.configuradorFeriado.entity.ConfiguradorFeriado;
import app.api.agendaFacil.business.configuradorFeriado.repository.ConfiguradorFeriadoRepository;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ApplicationScoped
public class ConfiguradorFeriadoLoader {

    final ConfiguradorFeriadoLoader configuradorFeriadoLoader;
    final ConfiguradorFeriadoRepository configuradorFeriadoRepository;

    public ConfiguradorFeriadoLoader(ConfiguradorFeriadoLoader configuradorFeriadoLoader, ConfiguradorFeriadoRepository configuradorFeriadoRepository) {
        this.configuradorFeriadoLoader = configuradorFeriadoLoader;
        this.configuradorFeriadoRepository = configuradorFeriadoRepository;
    }

    public static PanacheQuery<ConfiguradorFeriado> find(String query) {
        return ConfiguradorFeriadoRepository.find(query);
    }

    public List<ConfiguradorFeriado> listByIds(@NotNull List<Long> pListIdConfiguradorFeriado) {
        if (BasicFunctions.isNotEmpty(pListIdConfiguradorFeriado)) {
            return ConfiguradorFeriadoRepository.listByIds(pListIdConfiguradorFeriado);

        }
        return null;
    }

    public ConfiguradorFeriado findByDataFeriado(ConfiguradorFeriadoDTO pConfiguradorFeriado) {
        if (BasicFunctions.isNotEmpty(pConfiguradorFeriado, pConfiguradorFeriado.getDataFeriado())) {
            return ConfiguradorFeriadoRepository.findByDataFeriado(pConfiguradorFeriado.getDataFeriado());
        }
        return null;
    }

    public ConfiguradorFeriado findById(ConfiguradorFeriadoDTO pConfiguradorFeriado) {
        if (BasicFunctions.isNotEmpty(pConfiguradorFeriado) && BasicFunctions.isValid(pConfiguradorFeriado)) {
            return ConfiguradorFeriadoRepository.findById(pConfiguradorFeriado.getId());
        }
        return null;
    }

    public ConfiguradorFeriado findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return ConfiguradorFeriadoRepository.findById(pId);
        }
        return null;
    }

    public List<ConfiguradorFeriado> list(QueryFilter queryFilter) {
        return configuradorFeriadoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return configuradorFeriadoRepository.count(queryFilter);
    }
}
