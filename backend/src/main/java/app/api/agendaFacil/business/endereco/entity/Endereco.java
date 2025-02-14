package app.api.agendaFacil.business.endereco.entity;

import app.api.agendaFacil.business.endereco.DTO.EnderecoDTO;
import app.core.application.entity.Audit;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Entity
@Table(name = "endereco")
public class Endereco extends Audit {
    @Column()
    @SequenceGenerator(name = "enderecoIdSequence", sequenceName = "endereco_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String cep;

    @Column()
    private String logradouro;

    @Column()
    private Long numero;

    @Column()
    private String complemento;

    @Column()
    private String cidade;

    @Column()
    private String estado;

    @Column()
    private Boolean ativo;

    public Endereco() {
        super();
    }

    @Inject
    public Endereco(SecurityContext context) {
        super(context);
    }

    public Endereco(EnderecoDTO enderecoDTO) {
        super();
        if (BasicFunctions.isNotEmpty(enderecoDTO)) {
            this.id = enderecoDTO.getId();
            this.cep = enderecoDTO.getCep();
            this.logradouro = enderecoDTO.getLogradouro();
            this.numero = enderecoDTO.getNumero();
            this.complemento = enderecoDTO.getComplemento();
            this.cidade = enderecoDTO.getCidade();
            this.estado = enderecoDTO.getEstado();
        }
    }

    public Endereco(Endereco pEndereco, SecurityContext context) {
        super();
        if (BasicFunctions.isNotEmpty(pEndereco.getCep())) {
            this.setCep(pEndereco.getCep());
        }
        if (BasicFunctions.isNotEmpty(pEndereco.getLogradouro())) {
            this.setLogradouro(pEndereco.getLogradouro());
        }
        if (BasicFunctions.isNotEmpty(pEndereco.getNumero())) {
            this.setNumero(pEndereco.getNumero());
        }
        if (BasicFunctions.isNotEmpty(pEndereco.getComplemento())) {
            this.setComplemento(pEndereco.getComplemento());
        }
        if (BasicFunctions.isNotEmpty(pEndereco.getCidade())) {
            this.setCidade(pEndereco.getCidade());
        }
        if (BasicFunctions.isNotEmpty(pEndereco.getEstado())) {
            this.setEstado(pEndereco.getEstado());
        }

        this.setAtivo(Boolean.TRUE);
        this.createAudit(context);
    }

    public Endereco endereco(Endereco entityOld, Endereco entity, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(entity.getCep())) {
            entityOld.setCep(entity.getCep());
        }
        if (BasicFunctions.isNotEmpty(entity.getLogradouro())) {
            entityOld.setLogradouro(entity.getLogradouro());
        }
        if (BasicFunctions.isNotEmpty(entity.getNumero())) {
            entityOld.setNumero(entity.getNumero());
        }
        if (BasicFunctions.isNotEmpty(entity.getComplemento())) {
            entityOld.setComplemento(entity.getComplemento());
        }
        if (BasicFunctions.isNotEmpty(entity.getCidade())) {
            entityOld.setCidade(entity.getCidade());
        }
        if (BasicFunctions.isNotEmpty(entity.getEstado())) {
            entityOld.setEstado(entity.getEstado());
        }

        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }

    public Endereco deletarEndereco(Endereco entity, SecurityContext context) {

        entity.setAtivo(Boolean.FALSE);
        entity.deleteAudit(context);

        return entity;
    }

    public Endereco reativarEndereco(Endereco entity, SecurityContext context) {

        entity.setAtivo(Boolean.TRUE);
        entity.restoreAudit(context);

        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = StringFunctions.makeOnlyNumbers(cep);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
