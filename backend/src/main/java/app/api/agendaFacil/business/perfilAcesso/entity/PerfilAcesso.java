package app.api.agendaFacil.business.perfilAcesso.entity;

import app.api.agendaFacil.business.perfilAcesso.DTO.PerfilAcessoDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.management.rotina.entity.Rotina;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perfilacesso")
public class PerfilAcesso extends EntityBase {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rotinaperfilacesso", joinColumns = {
            @JoinColumn(name = "perfilacessoId")}, inverseJoinColumns = {
            @JoinColumn(name = "rotinaId")})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final List<Rotina> rotinas = new ArrayList<>();
    @Column()
    @SequenceGenerator(name = "perfilacessoIdSequence", sequenceName = "perfilacesso_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column()
    private String nome;
    @Column()
    private Boolean criar = false;
    @Column()
    private Boolean ler = false;
    @Column()
    private Boolean atualizar = false;
    @Column()
    private Boolean apagar = false;
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

    public PerfilAcesso() {
        super();
    }

    @Inject
    protected PerfilAcesso(SecurityContext context) {
        super(context);
    }

    public PerfilAcesso(PerfilAcessoDTO entityDTO) {

        if (BasicFunctions.isNotEmpty(entityDTO)) {

            if (BasicFunctions.isNotEmpty(entityDTO.getId())) {
                this.setId(entityDTO.getId());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getNome())) {
                this.setNome(entityDTO.getNome());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getCriar())) {
                this.setCriar(entityDTO.getCriar());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getLer())) {
                this.setLer(entityDTO.getLer());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getAtualizar())) {
                this.setAtualizar(entityDTO.getAtualizar());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getApagar())) {
                this.setApagar(entityDTO.getApagar());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getRotinas())) {
                entityDTO.getRotinas().forEach(rotinaDTO -> {
                    this.addRotinas(new Rotina(rotinaDTO));
                });
            }
        }

        this.usuario = usuarioAuth;
        this.usuarioAcao = usuarioAuth;
        this.dataAcao = Contexto.dataHoraContexto();
    }

    public PerfilAcesso perfilAcesso(PerfilAcesso entity, PerfilAcessoDTO entityDTO) {

        if (BasicFunctions.isNotEmpty(entityDTO)) {

            if (BasicFunctions.isNotEmpty(entityDTO.getLer())) {
                if (!entity.getLer().equals(entityDTO.getLer())) {
                    entity.setLer(entityDTO.getLer());
                }
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getNome())) {
                if (!entity.getNome().equals(entityDTO.getNome())) {
                    entity.setNome(entityDTO.getNome());
                }
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getAtualizar())) {
                if (BasicFunctions.isNotEmpty(entity.getAtualizar())
                        && !entity.getAtualizar().equals(entityDTO.getAtualizar())) {
                    entity.setAtualizar(entityDTO.getAtualizar());
                }
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getCriar())) {
                if (!entity.getCriar().equals(entityDTO.getCriar())) {
                    entity.setCriar(entityDTO.getCriar());
                }
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getApagar())) {
                entity.setApagar(entityDTO.getApagar());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getRotinas())) {
                entityDTO.getRotinas().forEach(rotinaDTO -> {
                    entity.addRotinas(new Rotina(rotinaDTO));
                });
            }
        }
        entity.usuarioAcao = usuarioAuth;
        entity.dataAcao = Contexto.dataHoraContexto();
        return entity;
    }

    public Boolean hasRotinas() {
        return BasicFunctions.isValid(this.id) && BasicFunctions.isNotEmpty(this.rotinas);
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

    public Boolean getCriar() {
        return criar;
    }

    public void setCriar(Boolean criar) {
        this.criar = criar;
    }

    public Boolean getLer() {
        return ler;
    }

    public void setLer(Boolean ler) {
        this.ler = ler;
    }

    public Boolean getAtualizar() {
        return atualizar;
    }

    public void setAtualizar(Boolean atualizar) {
        this.atualizar = atualizar;
    }

    public Boolean getApagar() {
        return apagar;
    }

    public void setApagar(Boolean apagar) {
        this.apagar = apagar;
    }

    public List<Rotina> getRotinas() {
        return rotinas;
    }

    public void addRotinas(Rotina rotina) {
        this.rotinas.add(rotina);
    }

    public void removeRotinas(Rotina rotina) {
        this.rotinas.remove(rotina);
    }

    public void addAllRotinas(List<Rotina> rotinas) {
        this.rotinas.addAll(rotinas);
    }

    public void removeAllRotinas(List<Rotina> rotinas) {
        this.rotinas.removeAll(rotinas);
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
