package app.api.agendaFacil.business.organizacao.DTO;

import app.api.agendaFacil.business.endereco.DTO.EnderecoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.management.timeZone.entity.TimeZone;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;

public class OrganizacaoDTO {

    private Long id;

    private Boolean ativo;

    private String cnpj;

    private String celular;

    private String email;

    private String nome;

    private EnderecoDTO endereco;

    private String tenant;

    private TimeZone timeZone;

    private String telefone;

    private Long telegramId;

    private Long whatsappId;

    private Boolean recebeNotificacaoTelegram = false;

    private Boolean recebeNotificacaoWhatsapp = false;


    public OrganizacaoDTO() {
    }

    public OrganizacaoDTO(Long id) {
        this.id = id;
    }

    public OrganizacaoDTO(Organizacao organizacao) {

        if (BasicFunctions.isNotEmpty(organizacao)) {

            this.id = organizacao.getId();

            this.nome = organizacao.getNome();

            this.cnpj = organizacao.getCnpj();

            this.telefone = organizacao.getTelefone();

            this.celular = organizacao.getCelular();

            this.email = organizacao.getEmail();

            this.endereco = new EnderecoDTO(organizacao.getEndereco());

            this.timeZone = organizacao.getTimeZone();

            this.ativo = organizacao.getAtivo();

            this.telegramId = organizacao.getTelegramId();

            this.whatsappId = organizacao.getWhatsappId();

            this.recebeNotificacaoTelegram = organizacao.getRecebeNotifacaoTelegram();

            this.recebeNotificacaoWhatsapp = organizacao.getRecebeNotifacaoWhatsapp();

            this.tenant = organizacao.getTenant();
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public TimeZone getTimeZone() {
        if (BasicFunctions.isNotEmpty(this.timeZone)) {
            return timeZone;
        }
        return new TimeZone(Contexto.defaultZoneIdToString(), Contexto.defaultTimeZoneOffset());
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public String getTenant() {
        if (BasicFunctions.isNotEmpty(this, this.tenant)) {
            return this.tenant.toLowerCase();
        }
        return "";
    }

    public void setTenant(String tenant) {
        this.tenant = tenant.toLowerCase();
    }

    public Boolean getRecebeNotificacaoTelegram() {
        return recebeNotificacaoTelegram;
    }

    public void setRecebeNotificacaoTelegram(Boolean recebeNotificacaoTelegram) {
        this.recebeNotificacaoTelegram = recebeNotificacaoTelegram;
    }

    public Boolean getRecebeNotificacaoWhatsapp() {
        return recebeNotificacaoWhatsapp;
    }

    public void setRecebeNotificacaoWhatsapp(Boolean recebeNotificacaoWhatsapp) {
        this.recebeNotificacaoWhatsapp = recebeNotificacaoWhatsapp;
    }
}
