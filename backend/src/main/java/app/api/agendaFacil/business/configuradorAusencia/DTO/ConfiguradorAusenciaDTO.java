package app.api.agendaFacil.business.configuradorAusencia.DTO;

import app.api.agendaFacil.business.configuradorAusencia.entity.ConfiguradorAusencia;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.core.helpers.utils.BasicFunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConfiguradorAusenciaDTO {

    private Long id;

    private String nomeAusencia;

    private LocalDate dataInicioAusencia;

    private LocalDate dataFimAusencia;

    private LocalTime horaInicioAusencia;

    private LocalTime horaFimAusencia;

    private List<UsuarioDTO> profissionaisAusentes = new ArrayList<>();

    private String observacao;

    public ConfiguradorAusenciaDTO() {
    }

    public ConfiguradorAusenciaDTO(ConfiguradorAusencia configuradorAusencia) {

        if (BasicFunctions.isNotEmpty(configuradorAusencia)) {

            this.id = configuradorAusencia.getId();

            this.nomeAusencia = configuradorAusencia.getNomeAusencia();

            this.dataInicioAusencia = configuradorAusencia.getDataInicioAusencia();

            this.dataFimAusencia = configuradorAusencia.getDataFimAusencia();

            this.horaInicioAusencia = configuradorAusencia.getHoraInicioAusencia();

            this.horaFimAusencia = configuradorAusencia.getHoraFimAusencia();

            this.observacao = configuradorAusencia.getObservacao();

            if (BasicFunctions.isNotEmpty(configuradorAusencia.getProfissionaisAusentes())) {
                this.profissionaisAusentes = new ArrayList<>();
                configuradorAusencia.getProfissionaisAusentes().forEach(profissional -> {
                    this.profissionaisAusentes.add(new UsuarioDTO(profissional));
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

    public String getNomeAusencia() {
        return nomeAusencia;
    }

    public void setNomeAusencia(String nomeAusencia) {
        this.nomeAusencia = nomeAusencia;
    }

    public LocalDate getDataInicioAusencia() {
        return dataInicioAusencia;
    }

    public void setDataInicioAusencia(LocalDate dataInicioAusencia) {
        this.dataInicioAusencia = dataInicioAusencia;
    }

    public LocalDate getDataFimAusencia() {
        return dataFimAusencia;
    }

    public void setDataFimAusencia(LocalDate dataFimAusencia) {
        this.dataFimAusencia = dataFimAusencia;
    }

    public LocalTime getHoraInicioAusencia() {
        return horaInicioAusencia;
    }

    public void setHoraInicioAusencia(LocalTime horaInicioAusencia) {
        this.horaInicioAusencia = horaInicioAusencia;
    }

    public LocalTime getHoraFimAusencia() {
        return horaFimAusencia;
    }

    public void setHoraFimAusencia(LocalTime horaFimAusencia) {
        this.horaFimAusencia = horaFimAusencia;
    }

    public List<UsuarioDTO> getProfissionaisAusentes() {
        return profissionaisAusentes;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void addProfissionaisAusentes(UsuarioDTO profissional) {
        this.profissionaisAusentes.add(profissional);
    }
}
