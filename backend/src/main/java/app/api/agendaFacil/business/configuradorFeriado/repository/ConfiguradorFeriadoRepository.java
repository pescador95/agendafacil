package app.api.agendaFacil.business.configuradorFeriado.repository;

import app.api.agendaFacil.business.configuradorFeriado.entity.ConfiguradorFeriado;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class ConfiguradorFeriadoRepository extends PersistenceRepository<ConfiguradorFeriado> {

    public ConfiguradorFeriadoRepository() {
        super();
    }

    public static ConfiguradorFeriado findById(Long id) {
        return ConfiguradorFeriado.findById(id);
    }

    public static ConfiguradorFeriado findByDataFeriado(LocalDate dataFeriado) {
        return ConfiguradorFeriado.find("dataFeriado = ?1", dataFeriado).firstResult();
    }

    public static PanacheQuery<ConfiguradorFeriado> find(String query) {
        return ConfiguradorFeriado.find(query);
    }

    public static List<ConfiguradorFeriado> listByIds(List<Long> pListIdConfiguradorFeriado) {
        return ConfiguradorFeriado.list("id in ?1", pListIdConfiguradorFeriado);
    }

    public List<ConfiguradorFeriado> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, ConfiguradorFeriado.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
