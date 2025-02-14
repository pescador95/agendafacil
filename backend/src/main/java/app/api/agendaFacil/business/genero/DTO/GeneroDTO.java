package app.api.agendaFacil.business.genero.DTO;

import app.api.agendaFacil.business.genero.entity.Genero;
import app.core.helpers.utils.BasicFunctions;

public class GeneroDTO {

    private Long id;

    private String genero;

    public GeneroDTO() {
    }

    public GeneroDTO(Genero entity) {

        if (BasicFunctions.isNotEmpty(entity)) {
            this.id = entity.getId();
            this.genero = entity.getGenero();
        }
    }

    public GeneroDTO(GeneroDTO entity) {

        if (BasicFunctions.isNotEmpty(entity)) {
            this.id = entity.getId();
            this.genero = entity.getGenero();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenero() {
        if (BasicFunctions.isNotEmpty(genero)) {
            return genero;
        }
        return null;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
