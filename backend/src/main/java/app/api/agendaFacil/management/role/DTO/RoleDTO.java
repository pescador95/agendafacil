package app.api.agendaFacil.management.role.DTO;

import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.management.role.entity.Role;
import app.core.helpers.utils.BasicFunctions;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDTO {

    private Long id;

    private String privilegio;

    private Boolean admin;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UsuarioDTO usuario;

    public RoleDTO() {

    }

    public RoleDTO(Role role) {

        this.id = role.getId();
        this.privilegio = role.getPrivilegio();
        this.admin = role.getAdmin();
        this.usuario = new UsuarioDTO(role.getUsuario());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Boolean hasPrivilegio() {
        return BasicFunctions.isNotEmpty(this.privilegio);
    }
}
