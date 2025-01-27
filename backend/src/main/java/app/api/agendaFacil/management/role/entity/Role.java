package app.api.agendaFacil.management.role.entity;

import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.management.role.DTO.RoleDTO;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.security.jpa.RolesValue;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Table(name = "role")
@Entity
public class Role extends EntityBase {

    @Column()
    @SequenceGenerator(name = "roleIdSequence", sequenceName = "role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    @RolesValue
    private String privilegio;

    @Column
    private Boolean admin;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    public Role() {
        super();
        this.admin = Boolean.FALSE;
    }

    @Inject
    protected Role(SecurityContext context) {
        super(context);
        this.admin = Boolean.FALSE;
    }

    public Role(Long id) {
        super();
        this.id = id;
    }

    public Role(RoleDTO roleDTO) {
        super();
        if (BasicFunctions.isNotEmpty(roleDTO)) {
            this.id = roleDTO.getId();
            this.privilegio = roleDTO.getPrivilegio();
            this.admin = roleDTO.getAdmin();
            this.usuario = new Usuario(roleDTO.getUsuario(), context);
        }
    }

    public Boolean hasPrivilegio() {
        return BasicFunctions.isNotEmpty(this.privilegio);
    }

    public Boolean admin() {
        return BasicFunctions.isNotEmpty(admin) && this.admin;
    }

    public void setUsuario() {
        this.id = Usuario.USUARIO;
    }

    public void setBot() {
        this.id = Usuario.BOT;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
