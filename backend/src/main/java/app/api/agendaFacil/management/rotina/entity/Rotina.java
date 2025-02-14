package app.api.agendaFacil.management.rotina.entity;

import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.management.rotina.DTO.RotinaDTO;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "rotina")

public class Rotina extends EntityBase {

    @Column()
    @SequenceGenerator(name = "rotinaIdSequence", sequenceName = "rotina_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String nome;

    @Column()
    private String icon;

    @Column()
    private String path;

    @Column()
    private String titulo;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "usuarioAcaoId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Usuario usuarioAcao;

    @Column()
    @JsonIgnore
    private LocalDateTime dataAcao;

    public Rotina() {
        super();
    }

    @Inject
    protected Rotina(SecurityContext context) {
        super(context);
    }

    public Rotina(RotinaDTO entityDTO) {
        super();
        if (BasicFunctions.isNotEmpty(entityDTO)) {
            this.id = entityDTO.getId();
            this.icon = entityDTO.getIcon();
            this.nome = entityDTO.getNome();
            this.path = entityDTO.getPath();
            this.titulo = entityDTO.getTitulo();
        }
    }

    public Rotina(Rotina entity) {
        super();
        if (BasicFunctions.isNotEmpty(entity)) {
            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                this.setNome(entity.getNome());
            }
            if (BasicFunctions.isNotEmpty(entity.getIcon())) {
                this.setIcon(entity.getIcon());
            }
            if (BasicFunctions.isNotEmpty(entity.getPath())) {
                this.setPath(entity.getPath());
            }
            if (BasicFunctions.isNotEmpty(entity.getTitulo())) {
                this.setTitulo(entity.getTitulo());
            }
            this.setUsuario(usuarioAuth);
            this.setUsuarioAcao(usuarioAuth);
            this.setDataAcao(Contexto.dataHoraContexto());
        }
    }

    public Rotina rotina(Rotina entityOld, RotinaDTO entity, Usuario usuarioAuth) {

        if (BasicFunctions.isEmpty(entityOld, entity)) {

            if (BasicFunctions.isNotEmpty(entity.getPath())) {
                if (!entityOld.getPath().equals(entity.getPath())) {
                    entityOld.setPath(entity.getPath());
                }
            }

            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                if (!Objects.equals(entityOld.getNome(), entity.getNome())) {
                    entityOld.setNome(entity.getNome());
                }
            }

            if (BasicFunctions.isNotEmpty(entity.getTitulo())) {
                if (BasicFunctions.isNotEmpty(entityOld.getTitulo()) && !entityOld.getTitulo().equals(entity.getTitulo())) {
                    entityOld.setTitulo(entity.getTitulo());
                }
            }
            if (BasicFunctions.isNotEmpty(entity.getIcon())) {
                if (!entityOld.getIcon().equals(entity.getIcon())) {
                    entityOld.setIcon(entity.getIcon());
                }
            }

            entityOld.setUsuarioAcao(usuarioAuth);
            entityOld.setDataAcao(Contexto.dataHoraContexto());
        }
        return entityOld;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
