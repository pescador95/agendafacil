package app.core.application.tenant;

import app.api.agendaFacil.business.contrato.entity.Contrato;
import app.core.application.entity.EntityBase;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Table(name = "tenant", schema = "config")
@Entity
public class Tenant extends EntityBase {


    @Column()
    @SequenceGenerator(name = "tenantIdSequence", sequenceName = "tenant_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String tenant;

    @ManyToOne
    @JoinColumn(name = "contratoId")
    private Contrato contrato;

    public Tenant() {
        super();
    }

    @Inject
    protected Tenant(SecurityContext context) {
        super(context);
    }

    public Tenant(String tenant) {
        super();
        this.tenant = tenant;
    }

    public Tenant(String tenant, Contrato contrato) {
        super();
        this.tenant = tenant;
        this.contrato = contrato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
