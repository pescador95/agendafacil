package app.api.agendaFacil.business.configuradorNotificacao.entity;

import app.api.agendaFacil.business.configuradorNotificacao.DTO.ConfiguradorNotificacaoDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "configuradorNotificacao")
public class ConfiguradorNotificacao extends EntityBase {

    @Column()
    @SequenceGenerator(name = "configuradorNotificacaoIdSequence", sequenceName = "configuradorNotificacao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @Column()
    private Long dataIntervalo;

    @Column()
    private LocalTime horaMinutoIntervalo;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "usuarioAcaoId")
    private Usuario usuarioAcao;

    @Column()
    private LocalDateTime dataAcao;

    public ConfiguradorNotificacao() {
        super();
    }

    @Inject
    protected ConfiguradorNotificacao(SecurityContext context) {
        super(context);
    }

    public ConfiguradorNotificacao(ConfiguradorNotificacaoDTO configuradorNotificacaoDTO) {
        super();
        if (BasicFunctions.isNotEmpty(configuradorNotificacaoDTO)) {
            this.id = configuradorNotificacaoDTO.getId();
            this.mensagem = configuradorNotificacaoDTO.getMensagem();
            this.dataIntervalo = configuradorNotificacaoDTO.getDataIntervalo();
            this.horaMinutoIntervalo = configuradorNotificacaoDTO.getHoraMinutoIntervalo();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Long getDataIntervalo() {
        return dataIntervalo;
    }

    public LocalTime getHoraMinutoIntervalo() {
        return horaMinutoIntervalo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
