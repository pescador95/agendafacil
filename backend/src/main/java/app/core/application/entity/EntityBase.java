package app.core.application.entity;

import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.helpers.utils.Contexto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;

@RequestScoped
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class EntityBase extends PanacheEntityBase {

    @Context
    protected final SecurityContext context;
    protected LocalDateTime currentDateTime;
    protected Long currentUserId;
    protected Usuario usuarioAuth;


    protected EntityBase(SecurityContext context) {
        this.context = context;
        this.usuarioAuth = Contexto.getContextUser(context);
        this.currentUserId = Contexto.getContextUserId(context);
        this.currentDateTime = Contexto.dataHoraContexto();
    }

    protected EntityBase() {
        this.context = null;
        this.usuarioAuth = null;
        this.currentUserId = null;
        this.currentDateTime = Contexto.dataHoraContexto();
    }

}