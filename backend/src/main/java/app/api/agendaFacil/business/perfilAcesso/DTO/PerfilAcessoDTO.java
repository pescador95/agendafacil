package app.api.agendaFacil.business.perfilAcesso.DTO;

import app.api.agendaFacil.business.perfilAcesso.entity.PerfilAcesso;
import app.api.agendaFacil.management.rotina.DTO.RotinaDTO;
import app.core.helpers.utils.BasicFunctions;

import java.util.ArrayList;
import java.util.List;

public class PerfilAcessoDTO {

    private Long id;

    private String nome;

    private Boolean criar;

    private Boolean ler;

    private Boolean atualizar;

    private Boolean apagar;

    private List<RotinaDTO> rotinas = new ArrayList<>();

    public PerfilAcessoDTO() {

    }

    public PerfilAcessoDTO(PerfilAcesso entity) {

        if (BasicFunctions.isNotEmpty(entity)) {

            if (BasicFunctions.isNotEmpty(entity.getId())) {
                this.setId(entity.getId());
            }
            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                this.setNome(entity.getNome());
            }
            if (BasicFunctions.isNotEmpty(entity.getCriar())) {
                this.setCriar(entity.getCriar());
            }
            if (BasicFunctions.isNotEmpty(entity.getLer())) {
                this.setLer(entity.getLer());
            }
            if (BasicFunctions.isNotEmpty(entity.getAtualizar())) {
                this.setAtualizar(entity.getAtualizar());
            }
            if (BasicFunctions.isNotEmpty(entity.getApagar())) {
                this.setApagar(entity.getApagar());
            }
            if (BasicFunctions.isNotEmpty(entity.getRotinas())) {
                entity.getRotinas().forEach(rotina -> {
                    this.addRotinas(new RotinaDTO(rotina));
                });
            }
            if (BasicFunctions.isNotEmpty(rotinas)) {
                this.rotinas = new ArrayList<>();
                this.addAllRotinas(rotinas);
            }
        }
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

    public List<RotinaDTO> getRotinas() {
        return rotinas;
    }

    public void addRotinas(RotinaDTO rotina) {
        this.rotinas.add(rotina);
    }

    public void removeRotinas(RotinaDTO rotina) {
        this.rotinas.remove(rotina);
    }

    public void addAllRotinas(List<RotinaDTO> rotinas) {
        this.rotinas.addAll(rotinas);
    }

    public void removeAllRotinas(List<RotinaDTO> rotinas) {
        this.rotinas.removeAll(rotinas);
    }

}
