package app.api.agendaFacil.business.endereco.DTO;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

public class EnderecoDTO {

    private Long id;

    private String cep;

    private String logradouro;

    private Long numero;

    private String complemento;

    private String cidade;

    private String estado;

    private String enderecoCompleto = enderecoToString();

    private Boolean ativo;

    public EnderecoDTO() {
    }

    public EnderecoDTO(Endereco endereco) {

        if (BasicFunctions.isNotEmpty(endereco)) {

            this.id = endereco.getId();
            this.cep = endereco.getCep();
            this.logradouro = endereco.getLogradouro();
            this.numero = endereco.getNumero();
            this.complemento = endereco.getComplemento();
            this.cidade = endereco.getCidade();
            this.estado = endereco.getEstado();
            this.ativo = endereco.getAtivo();

            this.enderecoCompleto = StringFunctions.makeEnderecoString(endereco);
        }
    }

    public String enderecoToString() {
        return StringFunctions.makeEnderecoString(new Endereco(this));
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }
}
