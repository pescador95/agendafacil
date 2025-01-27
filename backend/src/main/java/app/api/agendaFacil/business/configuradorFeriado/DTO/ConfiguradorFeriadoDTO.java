package app.api.agendaFacil.business.configuradorFeriado.DTO;

import app.api.agendaFacil.business.configuradorFeriado.entity.ConfiguradorFeriado;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.core.helpers.utils.BasicFunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConfiguradorFeriadoDTO {

    private Long id;

    private String nomeFeriado;

    private LocalDate dataFeriado;

    private LocalTime horaInicioFeriado;

    private LocalTime horaFimFeriado;

    private List<OrganizacaoDTO> organizacoesFeriado = new ArrayList<>();

    private String observacao;

    public ConfiguradorFeriadoDTO() {
    }

    public ConfiguradorFeriadoDTO(ConfiguradorFeriado configuradorFeriado) {

        if (BasicFunctions.isNotEmpty(configuradorFeriado)) {

            this.id = configuradorFeriado.getId();

            this.nomeFeriado = configuradorFeriado.getNomeFeriado();

            this.dataFeriado = configuradorFeriado.getDataFeriado();

            this.horaInicioFeriado = configuradorFeriado.getHoraInicioFeriado();

            this.horaFimFeriado = configuradorFeriado.getHoraFimFeriado();

            this.observacao = configuradorFeriado.getObservacao();

            if (BasicFunctions.isNotEmpty(configuradorFeriado.getOrganizacoesFeriado())) {
                this.organizacoesFeriado = new ArrayList<>();
                configuradorFeriado.getOrganizacoesFeriado().forEach(organizacao -> {
                    this.organizacoesFeriado.add(new OrganizacaoDTO(organizacao));
                });
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFeriado() {
        return nomeFeriado;
    }

    public void setNomeFeriado(String nomeFeriado) {
        this.nomeFeriado = nomeFeriado;
    }

    public LocalDate getDataFeriado() {
        return dataFeriado;
    }

    public void setDataFeriado(LocalDate dataFeriado) {
        this.dataFeriado = dataFeriado;
    }

    public LocalTime getHoraInicioFeriado() {
        return horaInicioFeriado;
    }

    public void setHoraInicioFeriado(LocalTime horaInicioFeriado) {
        this.horaInicioFeriado = horaInicioFeriado;
    }

    public LocalTime getHoraFimFeriado() {
        return horaFimFeriado;
    }

    public void setHoraFimFeriado(LocalTime horaFimFeriado) {
        this.horaFimFeriado = horaFimFeriado;
    }

    public List<OrganizacaoDTO> getOrganizacoesFeriado() {
        return organizacoesFeriado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void addOrganizacoesFeriado(OrganizacaoDTO organizacaoDTO) {
        this.organizacoesFeriado.add(organizacaoDTO);
    }
}
