package app.api.agendaFacil.management.profile.entity;

import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.core.application.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")

public class Profile extends EntityBase {

    @Column()
    String mimetype;
    @Column()
    LocalDateTime dataCriado;
    @Column()
    Long fileSize;
    @Column()
    String fileReference;
    @Column()
    String nomePessoa;
    @Column()
    @SequenceGenerator(name = "profileIdSequence", sequenceName = "profile_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column()
    private String originalName;
    @Column()
    private String keyName;
    @ManyToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "historicopessoaId")
    private HistoricoPessoa historicoPessoa;

    public Profile() {
        super();
    }

    @Inject
    protected Profile(SecurityContext context) {
        super(context);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public void setDataCriado(LocalDateTime dataCriado) {
        this.dataCriado = dataCriado;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public HistoricoPessoa getHistoricoPessoa() {
        return historicoPessoa;
    }

    public void setHistoricoPessoa(HistoricoPessoa historicoPessoa) {
        this.historicoPessoa = historicoPessoa;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }
}
