package app.api.agendaFacil.business.contrato.entity;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contrato", schema = "config")
@JsonIgnoreProperties({"ativo"})

public class Contrato extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "tipoContratoId")
    TipoContrato tipoContrato;
    @Column()
    @JsonIgnore
    LocalDateTime systemDateDeleted;
    @Column()
    @SequenceGenerator(name = "contratoIdSequence", sequenceName = "contrato_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column()
    private String tenant;
    @Column()
    private Integer numeroMaximoSessoes;
    @Column()
    private String consideracoes;
    @Column()
    private LocalDate dataContrato;
    @Column()
    private LocalDate dataTerminoContrato;
    @Column()
    @JsonIgnore
    private Boolean ativo;

    public Contrato() {
        super();
        this.dataTerminoContrato = Contexto.dataContexto().plusYears(1);
    }

    @Inject
    public Contrato(SecurityContext context, ContratoDTO entityDTO, TipoContrato tipoContrato) {
        super(context);

        if (BasicFunctions.isNotEmpty(entityDTO)) {
            if (BasicFunctions.isEmpty(entityDTO.getDataTerminoContrato())) {
                this.dataTerminoContrato = Contexto.dataContexto().plusYears(1);
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getTenant())) {
                this.setTenant(entityDTO.getTenant());
            }
            if (BasicFunctions.isValid(entityDTO.getNumeroMaximoSessoes())) {
                this.setNumeroMaximoSessoes(entityDTO.getNumeroMaximoSessoes());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getConsideracoes())) {
                this.setConsideracoes(entityDTO.getConsideracoes());
            }
            if (BasicFunctions.isValid(entityDTO.getDataContrato())) {
                this.setDataContrato(entityDTO.getDataContrato());
            }
            if (BasicFunctions.isNotEmpty(tipoContrato)) {
                this.setTipoContrato(tipoContrato);
            }
        }
    }

    @Inject
    public Contrato(SecurityContext context) {
        super(context);
        this.dataTerminoContrato = Contexto.dataContexto().plusYears(1);
    }

    public Contrato contrato(Contrato entityOld, ContratoDTO entity, TipoContrato tipoContrato) {

        if (BasicFunctions.isNotEmpty(entityOld, entity)) {
            if (BasicFunctions.isEmpty(entity.getDataTerminoContrato())) {
                entityOld.dataTerminoContrato = Contexto.dataContexto().plusYears(1);
            }
            if (BasicFunctions.isNotEmpty(entity.getTenant())) {
                entityOld.setTenant(entity.getTenant());
            }
            if (BasicFunctions.isValid(entity.getNumeroMaximoSessoes())) {
                entityOld.setNumeroMaximoSessoes(entity.getNumeroMaximoSessoes());
            }
            if (BasicFunctions.isNotEmpty(entity.getConsideracoes())) {
                entityOld.setConsideracoes(entity.getConsideracoes());
            }
            if (BasicFunctions.isValid(entity.getDataContrato())) {
                entityOld.setDataContrato(entity.getDataContrato());
            }
            if (BasicFunctions.isNotEmpty(tipoContrato)) {
                this.setTipoContrato(tipoContrato);
            }
        }
        return entityOld;
    }

    public Contrato deletarContrato(Contrato pContrato) {

        pContrato.setAtivo(Boolean.FALSE);
        pContrato.systemDateDeleted = Contexto.dataHoraContexto();
        return pContrato;
    }

    public Contrato reativarContrato(Contrato pContrato) {

        pContrato.setAtivo(Boolean.TRUE);
        pContrato.systemDateDeleted = null;
        return pContrato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroMaximoSessoes() {
        return numeroMaximoSessoes;
    }

    public void setNumeroMaximoSessoes(Integer numeroMaximoSessoes) {
        this.numeroMaximoSessoes = numeroMaximoSessoes;
    }

    public String getConsideracoes() {
        return consideracoes;
    }

    public void setConsideracoes(String consideracoes) {
        this.consideracoes = consideracoes;
    }

    public LocalDate getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(LocalDate dataContrato) {
        this.dataContrato = dataContrato;
    }

    public LocalDate getDataTerminoContrato() {
        return dataTerminoContrato;
    }

    public void setDataTerminoContrato(LocalDate dataTerminoContrato) {
        this.dataTerminoContrato = dataTerminoContrato;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public Boolean contratoAtivo(Organizacao organizacao) {
        return BasicFunctions.isEmpty(this.dataTerminoContrato) || this.dataTerminoContrato.isAfter(Contexto.dataContexto(organizacao.getZoneId()));
    }
}
