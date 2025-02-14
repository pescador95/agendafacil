package app.api.agendaFacil.business.configuradorAusencia.entity;

import app.api.agendaFacil.business.configuradorAusencia.DTO.ConfiguradorAusenciaDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "configuradorAusencia", indexes = {
        @Index(name = "iconfiguradorAusenciaak1", columnList = "dataInicioAusencia, dataFimAusencia")
})
public class ConfiguradorAusencia extends EntityBase {

    @Column()
    @SequenceGenerator(name = "configuradorAusenciaIdSequence", sequenceName = "configuradorAusencia_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String nomeAusencia;

    @Column()
    private LocalDate dataInicioAusencia;

    @Column()
    private LocalDate dataFimAusencia;

    @Column()
    private LocalTime horaInicioAusencia;

    @Column()
    private LocalTime horaFimAusencia;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "configuradorausenciausuario", joinColumns = {
            @JoinColumn(name = "configuradorAusenciaId")}, inverseJoinColumns = {
            @JoinColumn(name = "usuarioId")})
    private List<Usuario> profissionaisAusentes;

    @Column()
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "usuarioAcaoId")
    private Usuario usuarioAcao;

    @Column()
    private LocalDateTime dataAcao;

    public ConfiguradorAusencia() {
        super();
    }

    @Inject
    protected ConfiguradorAusencia(SecurityContext context) {
        super(context);
    }

    public ConfiguradorAusencia(ConfiguradorAusenciaDTO configuradorAusenciaDTO) {
        super();
        if (BasicFunctions.isNotEmpty(configuradorAusenciaDTO)) {
            this.id = configuradorAusenciaDTO.getId();
            this.nomeAusencia = configuradorAusenciaDTO.getNomeAusencia();
            this.dataInicioAusencia = configuradorAusenciaDTO.getDataInicioAusencia();
            this.dataFimAusencia = configuradorAusenciaDTO.getDataFimAusencia();
            this.horaInicioAusencia = configuradorAusenciaDTO.getHoraInicioAusencia();
            this.horaFimAusencia = configuradorAusenciaDTO.getHoraFimAusencia();
            this.observacao = configuradorAusenciaDTO.getObservacao();
            if (BasicFunctions.isNotEmpty(configuradorAusenciaDTO.getProfissionaisAusentes())) {
                this.profissionaisAusentes = new ArrayList<>();
                configuradorAusenciaDTO.getProfissionaisAusentes().forEach(profissional -> this.getProfissionaisAusentes().add(new Usuario(profissional)));
            }


        }
    }

    public ConfiguradorAusencia(ConfiguradorAusenciaDTO entity, List<Usuario> profissionaisAusentes, SecurityContext context, Usuario usuarioAuth) {
        super(context);
        if (BasicFunctions.isNotEmpty(entity.getNomeAusencia())) {
            this.setNomeAusencia(entity.getNomeAusencia());
        }
        if (BasicFunctions.isValid(entity.getDataInicioAusencia())) {
            this.setDataInicioAusencia(entity.getDataInicioAusencia());
        }
        if (BasicFunctions.isValid(entity.getDataFimAusencia())) {
            this.setDataFimAusencia(entity.getDataFimAusencia());
        }
        if (BasicFunctions.isValid(entity.getHoraInicioAusencia())) {
            this.setHoraInicioAusencia(entity.getHoraInicioAusencia());
        }
        if (BasicFunctions.isValid(entity.getHoraFimAusencia())) {
            this.setHoraFimAusencia(entity.getHoraFimAusencia());
        }
        if (BasicFunctions.isNotEmpty(entity.getObservacao())) {
            this.setObservacao(entity.getObservacao());
        }
        if (BasicFunctions.isNotEmpty(profissionaisAusentes)) {
            this.setProfissionaisAusentes(new ArrayList<>());
            this.getProfissionaisAusentes().addAll(profissionaisAusentes);
        }
        if (BasicFunctions.isNotEmpty(entity.getObservacao())) {
            this.setObservacao(entity.getObservacao());
        }
        this.setUsuarioAcao(usuarioAuth);
        this.setDataAcao(Contexto.dataHoraContexto());
    }

    public ConfiguradorAusencia configuradorAusencia(ConfiguradorAusencia entityOld, ConfiguradorAusenciaDTO entity, List<Usuario> profissionaisAusentes, Usuario usuarioAuth) {

        if (BasicFunctions.isNotEmpty(entity.getNomeAusencia())) {
            entityOld.setNomeAusencia(entity.getNomeAusencia());
        }
        if (BasicFunctions.isValid(entity.getDataInicioAusencia())) {
            entityOld.setDataInicioAusencia(entity.getDataInicioAusencia());
        }
        if (BasicFunctions.isValid(entity.getDataFimAusencia())) {
            entityOld.setDataFimAusencia(entity.getDataFimAusencia());
        }
        if (BasicFunctions.isValid(entity.getHoraInicioAusencia())) {
            entityOld.setHoraInicioAusencia(entity.getHoraInicioAusencia());
        }
        if (BasicFunctions.isValid(entity.getHoraFimAusencia())) {
            entityOld.setHoraFimAusencia(entity.getHoraFimAusencia());
        }
        if (BasicFunctions.isNotEmpty(entity.getObservacao())) {
            entityOld.setObservacao(entity.getObservacao());
        }
        if (BasicFunctions.isNotEmpty(profissionaisAusentes)) {
            entityOld.setProfissionaisAusentes(new ArrayList<>());
            entityOld.getProfissionaisAusentes().addAll(profissionaisAusentes);
        }
        if (BasicFunctions.isNotEmpty(entity.getObservacao())) {
            entityOld.setObservacao(entity.getObservacao());
        }
        entityOld.setUsuarioAcao(usuarioAuth);
        entityOld.setDataAcao(Contexto.dataHoraContexto());

        return entityOld;
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

    public List<Usuario> getProfissionaisAusentes() {
        return profissionaisAusentes;
    }

    public void setProfissionaisAusentes(List<Usuario> profissionaisAusentes) {
        this.profissionaisAusentes = profissionaisAusentes;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getUsuarioAcao() {
        return usuarioAcao;
    }

    public void setUsuarioAcao(Usuario usuarioAcao) {
        this.usuarioAcao = usuarioAcao;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }
}
