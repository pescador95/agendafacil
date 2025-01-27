package app.api.agendaFacil.management.role.validator;

import app.api.agendaFacil.management.role.DTO.RoleDTO;
import app.api.agendaFacil.management.role.entity.Role;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleValidator {

    final RoleValidator roleValidator;

    public RoleValidator(RoleValidator roleValidator) {
        this.roleValidator = roleValidator;
    }

    public Boolean hasPrivilegio(Role pRole) {
        if (!pRole.hasPrivilegio()) {

            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean hasPrivilegio(RoleDTO pRole) {
        if (!pRole.hasPrivilegio()) {

            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}

