package app.api.agendaFacil.management.role.loader;

import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.management.role.DTO.RoleDTO;
import app.api.agendaFacil.management.role.entity.Role;
import app.api.agendaFacil.management.role.repository.RoleRepository;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RoleLoader {

    final RoleLoader roleLoader;
    final RoleRepository roleRepository;

    public RoleLoader(RoleLoader roleLoader, RoleRepository roleRepository) {
        this.roleLoader = roleLoader;
        this.roleRepository = roleRepository;
    }

    public List<Role> listByListIdRole(EntidadeDTO entity) {
        if (BasicFunctions.isNotEmpty(entity, entity.getPrivilegios())) {
            return RoleRepository.listByListIdRole(entity.getPrivilegios());
        }
        return RoleRepository.listDefaultRole();
    }

    public List<Role> listByListIdRole(List<Long> pListIdRole) {
        if (BasicFunctions.isNotEmpty(pListIdRole)) {
            return RoleRepository.listByListIdRole(pListIdRole);
        }
        return null;
    }

    public Role findByPrivilegio(RoleDTO pRole) {
        if (BasicFunctions.isNotEmpty(pRole, pRole.getPrivilegio())) {
            return RoleRepository.findByPrivilegio(pRole.getPrivilegio());
        }
        return null;
    }

    public Role findById(RoleDTO pRole) {
        if (BasicFunctions.isNotEmpty(pRole, pRole.getId())) {
            return RoleRepository.findById(pRole.getId());
        }
        return null;
    }

    public List<Role> list(QueryFilter queryFilter) {
        return roleRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return roleRepository.count(queryFilter);
    }
}
