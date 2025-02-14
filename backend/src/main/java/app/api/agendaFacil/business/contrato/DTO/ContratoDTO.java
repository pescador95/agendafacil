package app.api.agendaFacil.business.contrato.DTO;

import app.api.agendaFacil.business.contrato.entity.Contrato;

import java.time.LocalDate;

public class ContratoDTO {

    private Long id;

    private String tenant;

    private String consideracoes;

    private Integer numeroMaximoSessoes;

    private Long tipoContrato;

    private LocalDate dataContrato;

    private LocalDate dataTerminoContrato;

    public ContratoDTO() {
    }

    public ContratoDTO(Contrato contrato) {
        this.id = contrato.getId();
        this.dataTerminoContrato = contrato.getDataTerminoContrato();
        this.tipoContrato = contrato.getTipoContrato().getId();
        this.dataContrato = contrato.getDataContrato();
        this.consideracoes = contrato.getConsideracoes();
        this.setTenant(contrato.getTenant());
        this.setNumeroMaximoSessoes(contrato.getNumeroMaximoSessoes());
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

    public String getConsideracoes() {
        return consideracoes;
    }

    public void setConsideracoes(String consideracoes) {
        this.consideracoes = consideracoes;
    }

    public Integer getNumeroMaximoSessoes() {
        return numeroMaximoSessoes;
    }

    public void setNumeroMaximoSessoes(Integer numeroMaximoSessoes) {
        this.numeroMaximoSessoes = numeroMaximoSessoes;
    }

    public Long getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(Long tipoContrato) {
        this.tipoContrato = tipoContrato;
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
}
