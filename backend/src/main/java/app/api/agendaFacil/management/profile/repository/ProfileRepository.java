package app.api.agendaFacil.management.profile.repository;

import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.api.agendaFacil.management.profile.entity.Profile;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class ProfileRepository extends PersistenceRepository<Profile> {

    public ProfileRepository() {
        super();
    }

    public static Profile findByHistoricoPessoa(HistoricoPessoa historicoPessoa) {
        return Profile.find("historicoPessoa = ?1", historicoPessoa).firstResult();
    }

    public static Profile findById(Long id) {
        return Profile.findById(id);
    }

    public static PanacheQuery<Profile> find(String query) {
        return Profile.find(query);
    }

    public static List<Profile> listByIds(List<Long> pListIdProfile) {
        return Profile.list("id in ?1", pListIdProfile);
    }

    public List<Profile> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Profile.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}