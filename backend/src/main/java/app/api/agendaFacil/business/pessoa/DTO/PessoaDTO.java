package app.api.agendaFacil.business.pessoa.DTO;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.core.helpers.utils.BasicFunctions;

import java.time.LocalDate;

public class PessoaDTO {

    private Long id;

    private String nome;

    private Long genero;

    private LocalDate dataNascimento;

    private String telefone;

    private String celular;

    private String email;

    private String cpf;

    private Endereco endereco;

    private Long telegramId;

    private Long whatsappId;

    private Boolean recebeNotifacaoWhatsapp = false;

    private Boolean recebeNotifacaoTelegram = false;

    private Boolean ativo;

    private String tenant;

    public PessoaDTO() {
    }

    public PessoaDTO(Long id) {
        this.id = id;
    }

    public PessoaDTO(Pessoa pessoa) {

        if (BasicFunctions.isNotEmpty(pessoa)) {

            this.id = pessoa.getId();

            this.nome = pessoa.getNome();

            this.dataNascimento = pessoa.getDataNascimento();

            this.telefone = pessoa.getTelefone();

            this.celular = pessoa.getCelular();

            this.email = pessoa.getEmail();

            this.cpf = pessoa.getCpf();

            this.endereco = pessoa.getEndereco();

            this.telegramId = pessoa.getTelegramId();

            this.whatsappId = pessoa.getWhatsappId();

            this.recebeNotifacaoTelegram = pessoa.getRecebeNotifacaoTelegram();

            this.recebeNotifacaoWhatsapp = pessoa.getRecebeNotifacaoWhatsapp();

            this.ativo = pessoa.getAtivo();

            if (BasicFunctions.isNotEmpty(pessoa.getGenero())) {
                this.genero = pessoa.getGenero().getId();
            }
        }
    }

    public PessoaDTO(Entidade entity) {

        Pessoa pessoa = (Pessoa) entity;

        if (BasicFunctions.isNotEmpty(pessoa)) {

            this.id = pessoa.getId();

            this.nome = pessoa.getNome();

            this.dataNascimento = pessoa.getDataNascimento();

            this.telefone = pessoa.getTelefone();

            this.celular = pessoa.getCelular();

            this.email = pessoa.getEmail();

            this.cpf = pessoa.getCpf();

            this.endereco = pessoa.getEndereco();

            this.telegramId = pessoa.getTelegramId();

            this.whatsappId = pessoa.getWhatsappId();

            this.recebeNotifacaoTelegram = pessoa.getRecebeNotifacaoTelegram();

            this.recebeNotifacaoWhatsapp = pessoa.getRecebeNotifacaoWhatsapp();

            this.ativo = pessoa.getAtivo();

            if (BasicFunctions.isNotEmpty(pessoa.getGenero())) {
                this.genero = pessoa.getGenero().getId();
            }
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

    public Long getGenero() {
        return this.genero;
    }

    public void setGenero(Long genero) {
        this.genero = genero;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Long getWhatsappId() {
        return whatsappId;
    }

    public void setWhatsappId(Long whatsappId) {
        this.whatsappId = whatsappId;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getRecebeNotifacaoWhatsapp() {
        return recebeNotifacaoWhatsapp;
    }

    public void setRecebeNotifacaoWhatsapp(Boolean recebeNotifacaoWhatsapp) {
        this.recebeNotifacaoWhatsapp = recebeNotifacaoWhatsapp;
    }

    public Boolean getRecebeNotifacaoTelegram() {
        return recebeNotifacaoTelegram;
    }

    public void setRecebeNotifacaoTelegram(Boolean recebeNotifacaoTelegram) {
        this.recebeNotifacaoTelegram = recebeNotifacaoTelegram;
    }

    public String getTenant() {
        if (BasicFunctions.isNotEmpty(this, this.tenant)) {
            return this.tenant.toLowerCase();
        }
        return "";
    }

    public void setTenant(String tenant) {
        if (BasicFunctions.isNotEmpty(tenant)) {
            this.tenant = tenant.toLowerCase();
        }
    }
}
