package app.core.application.entity;

import app.core.helpers.utils.Contexto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;

@RequestScoped
@MappedSuperclass
public abstract class Audit extends EntityBase {

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdAt;

    @Column()
    Long createdBy;

    @Column()
    LocalDateTime updatedAt;

    @Column()
    Long updatedBy;

    @Column()
    LocalDateTime deletedAt;

    @Column()
    Long deletedBy;

    protected Audit() {
        super();
    }

    @Inject
    protected Audit(SecurityContext context) {
        super(context);
    }

    public Audit createAudit(SecurityContext context) {
        this.createdBy = Contexto.getContextUserId(context);
        this.createdAt = currentDateTime;
        return this;
    }

    public Audit updateAudit(SecurityContext context) {
        this.updatedBy = Contexto.getContextUserId(context);
        this.updatedAt = currentDateTime;
        return this;
    }

    public Audit deleteAudit(SecurityContext context) {
        this.deletedBy = Contexto.getContextUserId(context);
        this.deletedAt = currentDateTime;
        return this;
    }

    public Audit restoreAudit(SecurityContext context) {
        this.updatedBy = Contexto.getContextUserId(context);
        this.updatedAt = currentDateTime;
        this.deletedBy = null;
        this.deletedAt = null;
        return this;
    }

    public abstract Long getId();

    public abstract void setId(Long id);
}
