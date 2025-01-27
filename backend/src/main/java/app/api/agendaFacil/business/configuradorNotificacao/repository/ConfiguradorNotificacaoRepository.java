package app.api.agendaFacil.business.configuradorNotificacao.repository;

import app.api.agendaFacil.business.configuradorNotificacao.entity.ConfiguradorNotificacao;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class ConfiguradorNotificacaoRepository extends PersistenceRepository<ConfiguradorNotificacao> {

    public ConfiguradorNotificacaoRepository() {
        super();
    }

    public static ConfiguradorNotificacao findById(Long id) {
        return ConfiguradorNotificacao.findById(id);
    }

    public List<ConfiguradorNotificacao> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, ConfiguradorNotificacao.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
