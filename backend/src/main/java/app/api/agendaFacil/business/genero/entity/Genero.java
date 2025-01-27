package app.api.agendaFacil.business.genero.entity;

import app.api.agendaFacil.business.genero.DTO.GeneroDTO;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Entity
@Table(name = "genero")
public class Genero extends EntityBase {

    @Column()
    @SequenceGenerator(name = "generoIdSequence", sequenceName = "generoAgendamento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column()
    private String genero;

    public Genero() {
        super();
    }

    @Inject
    protected Genero(SecurityContext context) {
        super(context);
    }

    protected Genero(GeneroDTO generoDTO) {
        super();
        if (BasicFunctions.isNotEmpty(generoDTO)) {
            this.id = generoDTO.getId();
            this.genero = generoDTO.getGenero();
        }
    }

    public Genero(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
