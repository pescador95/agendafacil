package app.api.agendaFacil.business.contrato.entity;

import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Entity
@Table(name = "tipocontrato", schema = "config")

public class TipoContrato extends EntityBase {

    public static final Long SESSAO_UNICA = 1L;
    public static final Long SESSAO_COMPARTILHADA = 2L;
    @Column()
    @SequenceGenerator(name = "tipoContratoIdSequence", sequenceName = "tipocontrato_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column()
    private String tipoContrato;
    @Column()
    private String descricao;

    public TipoContrato() {
        super();
    }

    @Inject
    protected TipoContrato(SecurityContext context) {
        super(context);
    }

    public Boolean sessaoUnica() {
        return BasicFunctions.isValid(this.id) && this.id.equals(TipoContrato.SESSAO_UNICA);
    }

    public Boolean sessaoCompartilhada() {
        return BasicFunctions.isValid(this.id) && this.id.equals(TipoContrato.SESSAO_COMPARTILHADA);
    }

    public void setTipoSessaoUnica() {
        this.id = TipoContrato.SESSAO_UNICA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipoSessao() {

        if (BasicFunctions.isValid(this.id) && this.id.equals(TipoContrato.SESSAO_UNICA)) {
            return "unique_";
        }
        return "shared_";
    }

}
