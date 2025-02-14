package app.core.application.config;

import app.api.agendaFacil.business.contrato.entity.Contrato;
import app.core.application.persistence.PersistenceRepository;
import app.core.helpers.utils.Contexto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationConfigRepository extends PersistenceRepository<ApplicationConfig> {

    public ApplicationConfigRepository() {
        super();
    }

    public static ApplicationConfig findById(Long id) {
        return ApplicationConfig.findById(id);
    }

    public static PanacheQuery<ApplicationConfig> find(String query) {
        return Contrato.find(query);
    }

    public static ApplicationConfig findByProfile() {
        return ApplicationConfig.find("profile = ?1", Contexto.getProfile()).firstResult();
    }
}
