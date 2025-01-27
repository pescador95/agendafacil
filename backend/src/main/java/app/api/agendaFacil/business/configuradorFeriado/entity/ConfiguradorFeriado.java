package app.api.agendaFacil.business.configuradorFeriado.entity;

import app.api.agendaFacil.business.configuradorFeriado.DTO.ConfiguradorFeriadoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
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
@Table(name = "configuradorFeriado", indexes = {
        @Index(name = "iconfiguradorFeriadoak1", columnList = "dataFeriado, horaInicioFeriado, horaFimFeriado")
})
public class ConfiguradorFeriado extends EntityBase {

    @Column()
    @SequenceGenerator(name = "configuradorFeriadoIdSequence", sequenceName = "configuradorFeriado_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String nomeFeriado;

    @Column()
    private LocalDate dataFeriado;

    @Column()
    private LocalTime horaInicioFeriado;

    @Column()
    private LocalTime horaFimFeriado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "configuradorferiadoorganizacao", joinColumns = {
            @JoinColumn(name = "configuradorFeriadoId")}, inverseJoinColumns = {
            @JoinColumn(name = "organizacaoId")})
    private List<Organizacao> organizacoesFeriado;

    @Column()
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "usuarioAcaoId")
    private Usuario usuarioAcao;

    @Column()
    private LocalDateTime dataAcao;

    public ConfiguradorFeriado() {
        super();
    }

    @Inject
    protected ConfiguradorFeriado(SecurityContext context) {
        super(context);
    }

    public ConfiguradorFeriado(ConfiguradorFeriadoDTO configuradorFeriadoDTO) {
        super();
        if (BasicFunctions.isNotEmpty(configuradorFeriadoDTO.getId())) {
            this.id = configuradorFeriadoDTO.getId();
            this.nomeFeriado = configuradorFeriadoDTO.getNomeFeriado();
            this.dataFeriado = configuradorFeriadoDTO.getDataFeriado();
            this.horaInicioFeriado = configuradorFeriadoDTO.getHoraInicioFeriado();
            this.horaFimFeriado = configuradorFeriadoDTO.getHoraFimFeriado();
            this.observacao = configuradorFeriadoDTO.getObservacao();

            if (BasicFunctions.isNotEmpty(configuradorFeriadoDTO.getOrganizacoesFeriado())) {
                this.organizacoesFeriado = new ArrayList<>();
                configuradorFeriadoDTO.getOrganizacoesFeriado().forEach(organizacao -> this.organizacoesFeriado.add(new Organizacao(organizacao)));
            }
        }
    }

    public ConfiguradorFeriado(ConfiguradorFeriadoDTO entity, List<Organizacao> organizacoesExcecoes, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(entity.getNomeFeriado())) {
            this.setNomeFeriado(entity.getNomeFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getDataFeriado())) {
            this.setDataFeriado(entity.getDataFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getHoraInicioFeriado())) {
            this.setHoraInicioFeriado(entity.getHoraInicioFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getHoraFimFeriado())) {
            this.setHoraFimFeriado(entity.getHoraFimFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getObservacao())) {
            this.setObservacao(entity.getObservacao());
        }
        if (BasicFunctions.isNotEmpty(organizacoesExcecoes)) {
            this.organizacoesFeriado = new ArrayList<>(organizacoesExcecoes);
        }
        this.setUsuarioAcao(usuarioAuth);
        this.setDataAcao(Contexto.dataHoraContexto());
    }

    public ConfiguradorFeriado configuradorFeriado(ConfiguradorFeriado entityOld, ConfiguradorFeriadoDTO entity, List<Organizacao> organizacoesExcecoes, Usuario usuarioAuth) {

        if (BasicFunctions.isNotEmpty(entity.getNomeFeriado())) {
            entityOld.setNomeFeriado(entity.getNomeFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getDataFeriado())) {
            entityOld.setDataFeriado(entity.getDataFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getHoraInicioFeriado())) {
            entityOld.setHoraInicioFeriado(entity.getHoraInicioFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getHoraFimFeriado())) {
            entityOld.setHoraFimFeriado(entity.getHoraFimFeriado());
        }
        if (BasicFunctions.isNotEmpty(entity.getObservacao())) {
            entityOld.setObservacao(entity.getObservacao());
        }
        if (BasicFunctions.isNotEmpty(organizacoesExcecoes)) {
            entityOld.setOrganizacoesFeriado(new ArrayList<>());
            entityOld.getOrganizacoesFeriado().addAll(organizacoesExcecoes);
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

    public List<Organizacao> getOrganizacoesFeriado() {
        return organizacoesFeriado;
    }

    public void setOrganizacoesFeriado(List<Organizacao> organizacoesFeriado) {
        this.organizacoesFeriado = organizacoesFeriado;
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
