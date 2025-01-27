package app.api.agendaFacil.management.rotina.DTO;

import app.api.agendaFacil.management.rotina.entity.Rotina;
import app.core.helpers.utils.BasicFunctions;

public class RotinaDTO {

    private Long id;

    private String nome;

    private String icon;

    private String path;

    private String titulo;

    public RotinaDTO() {

    }

    public RotinaDTO(Rotina entity) {
        super();
        if (BasicFunctions.isNotEmpty(entity)) {
            this.id = entity.getId();
            this.icon = entity.getIcon();
            this.nome = entity.getNome();
            this.path = entity.getPath();
            this.titulo = entity.getTitulo();
        }
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

}
