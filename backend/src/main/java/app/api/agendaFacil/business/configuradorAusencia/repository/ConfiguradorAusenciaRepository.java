package app.api.agendaFacil.business.configuradorAusencia.repository;

import app.api.agendaFacil.business.configuradorAusencia.entity.ConfiguradorAusencia;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class ConfiguradorAusenciaRepository extends PersistenceRepository<ConfiguradorAusencia> {

    public ConfiguradorAusenciaRepository() {
        super();
    }

    public static ConfiguradorAusencia findById(Long id) {
        return ConfiguradorAusencia.findById(id);
    }

    public static ConfiguradorAusencia findByDataAusencia(ConfiguradorAusencia pConfiguradorAusencia) {
        return ConfiguradorAusencia.find("dataInicioAusencia = ?1 and dataFimAusencia = ?2",
                        pConfiguradorAusencia.getDataInicioAusencia(), pConfiguradorAusencia.getDataFimAusencia())
                .firstResult();
    }

    public static PanacheQuery<ConfiguradorAusencia> find(String query) {
        return ConfiguradorAusencia.find(query);
    }

    public static List<ConfiguradorAusencia> listByIds(List<Long> pListIdConfiguradorAusencia) {
        return ConfiguradorAusencia.list("id in ?1", pListIdConfiguradorAusencia);
    }

    public List<ConfiguradorAusencia> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, ConfiguradorAusencia.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
