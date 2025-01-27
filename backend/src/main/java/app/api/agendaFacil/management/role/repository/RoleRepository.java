package app.api.agendaFacil.management.role.repository;

import app.api.agendaFacil.management.role.entity.Role;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class RoleRepository extends PersistenceRepository<Role> {

    public RoleRepository() {
        super();
    }

    public static Role findById(Long id) {
        return Role.findById(id);
    }

    public static Role findByPrivilegio(String privilegio) {
        return Role.find("privilegio = ?1", privilegio).firstResult();
    }

    public static PanacheQuery<Role> find(String query) {
        return Role.find(query);
    }

    public static List<Role> listByListIdRole(List<Long> pListIdRole) {
        return Role.list("id in ?1", pListIdRole);
    }

    public static List<Role> listAll() {
        return Role.listAll();
    }

    public static List<Role> listDefaultRole() {
        return Role.list("not admin");
    }

    public List<Role> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Role.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
