package app.api.agendaFacil.business.pessoa.DTO;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.management.timeZone.entity.TimeZone;
import app.core.helpers.utils.BasicFunctions;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntidadeDTO {

    private Long id;

    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean alterarSenha = false;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime dataToken;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean bot = false;

    private PessoaDTO pessoa;

    private String nomeProfissional;

    private List<Long> organizacoes;

    private Long organizacaoDefault;

    private List<Long> tiposAgendamentos;

    private List<Long> configuradoresEspeciais;

    private Long telegramId;

    private Long whatsappId;

    private String email;

    private String telefone;

    private String celular;

    private String nome;

    private String cpf;

    private String cnpj;

    private LocalDate dataNascimento;

    private Long genero;

    private Boolean ativo;

    private List<Long> privilegios;

    private Endereco endereco;

    private TimeZone timeZone;

    private Boolean recebeNotifacaoWhatsapp = false;

    private Boolean recebeNotifacaoTelegram = false;

    private String tenant;


    public EntidadeDTO() {
    }

    public EntidadeDTO(Usuario entity) {

        if (BasicFunctions.isNotEmpty(entity)) {

            this.id = entity.getId();

            this.password = entity.getPassword();

            this.login = entity.getLogin();

            this.alterarSenha = entity.getAlterarSenha();

            this.token = entity.getToken();

            this.dataToken = entity.getDataToken();

            this.bot = entity.getBot();

            this.nomeProfissional = entity.nomeProfissional();

            if (BasicFunctions.isNotEmpty(entity.getPessoa())) {
                this.recebeNotifacaoTelegram = entity.getPessoa().getRecebeNotifacaoTelegram();
                this.recebeNotifacaoWhatsapp = entity.getPessoa().getRecebeNotifacaoWhatsapp();
                this.whatsappId = entity.getWhatsappId();
                this.telegramId = entity.getTelegramId();
                this.tenant = entity.getPessoa().getTenant();
            }

            this.ativo = entity.getAtivo();

            if (BasicFunctions.isNotEmpty(entity.getPrivilegio())) {
                this.privilegios = new ArrayList<>();
                entity.getPrivilegio().forEach(privilegio -> {
                    this.privilegios.add(privilegio.getId());
                });
            }

            if (BasicFunctions.isNotEmpty(entity.getOrganizacoes())) {
                this.organizacoes = new ArrayList<>();
                entity.getOrganizacoes().forEach(organizacao -> {
                    this.organizacoes.add(organizacao.getId());
                });
            }

            this.organizacaoDefault = entity.getOrganizacaoDefaultId();

            if (BasicFunctions.isNotEmpty(entity.getTiposAgendamentos())) {
                this.tiposAgendamentos = new ArrayList<>();
                entity.getTiposAgendamentos().forEach(tipoAgendamento -> {
                    this.tiposAgendamentos.add(tipoAgendamento.getId());
                });
            }
            if (BasicFunctions.isNotEmpty(entity.getConfiguradoresEspeciais())) {
                this.configuradoresEspeciais = new ArrayList<>();
                entity.getConfiguradoresEspeciais().forEach(configurador -> {
                    this.configuradoresEspeciais.add(configurador.getId());
                });
            }
        }
    }

    public EntidadeDTO(Organizacao entity) {

        if (BasicFunctions.isNotEmpty(entity)) {

            this.id = entity.getId();
            this.nome = entity.getNome();
            this.cnpj = entity.getCnpj();
            this.celular = entity.getCelular();
            this.email = entity.getEmail();
            this.endereco = entity.getEndereco();
            this.recebeNotifacaoTelegram = entity.getRecebeNotifacaoTelegram();
            this.recebeNotifacaoWhatsapp = entity.getRecebeNotifacaoWhatsapp();
            this.whatsappId = entity.getWhatsappId();
            this.telegramId = entity.getTelegramId();
            this.tenant = entity.getTenant();
        }
        this.ativo = entity.getAtivo();
    }

    public EntidadeDTO(Pessoa entity) {

        if (BasicFunctions.isNotEmpty(entity)) {

            this.id = entity.getId();
            this.nome = entity.getNome();
            this.cpf = entity.getCpf();
            this.celular = entity.getCelular();
            this.email = entity.getEmail();
            this.dataNascimento = getDataNascimento();
            this.endereco = entity.getEndereco();
            this.recebeNotifacaoTelegram = entity.getRecebeNotifacaoTelegram();
            this.recebeNotifacaoWhatsapp = entity.getRecebeNotifacaoWhatsapp();
            this.whatsappId = entity.getWhatsappId();
            this.telegramId = entity.getTelegramId();
            this.tenant = entity.getTenant();
        }
        this.ativo = entity.getAtivo();
    }

    public EntidadeDTO(Usuario entity, List<Long> privilegios, List<Long> organizacoes, List<Long> tiposAgendamentos,
                       List<Long> configuradoresEspeciais) {

        if (BasicFunctions.isNotEmpty(entity)) {

            this.id = entity.getId();

            this.password = entity.getPassword();

            this.login = entity.getLogin();

            this.alterarSenha = entity.getAlterarSenha();

            this.token = entity.getToken();

            this.dataToken = entity.getDataToken();

            this.bot = entity.getBot();

            this.nomeProfissional = entity.nomeProfissional();

            this.organizacaoDefault = entity.getOrganizacaoDefaultId();

            this.pessoa = new PessoaDTO(entity.getPessoa());

            if (BasicFunctions.isNotEmpty(this.pessoa)) {
                this.recebeNotifacaoTelegram = this.pessoa.getRecebeNotifacaoTelegram();
                this.recebeNotifacaoWhatsapp = this.pessoa.getRecebeNotifacaoWhatsapp();
                this.whatsappId = this.pessoa.getWhatsappId();
                this.telegramId = this.pessoa.getTelegramId();
                this.tenant = this.pessoa.getTenant();
            }

            this.ativo = entity.getAtivo();

            if (BasicFunctions.isNotEmpty(privilegios)) {
                this.privilegios.addAll(privilegios);
            }

            if (BasicFunctions.isNotEmpty(organizacoes)) {
                this.organizacoes.addAll(organizacoes);
            }

            if (BasicFunctions.isNotEmpty(entity.getTiposAgendamentos())) {
                this.tiposAgendamentos.addAll(tiposAgendamentos);
            }

            if (BasicFunctions.isNotEmpty(entity.getConfiguradoresEspeciais())) {
                this.configuradoresEspeciais.addAll(configuradoresEspeciais);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public Boolean getAlterarSenha() {
        if (BasicFunctions.isNotEmpty(alterarSenha)) {
            return alterarSenha;
        }
        return Boolean.FALSE;
    }

    public void setAlterarSenha(Boolean alterarSenha) {
        this.alterarSenha = alterarSenha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDataToken() {
        return dataToken;
    }

    public void setDataToken(LocalDateTime dataToken) {
        this.dataToken = dataToken;
    }

    public Boolean getBot() {
        if (BasicFunctions.isNotEmpty(bot)) {
            return bot;
        }
        return Boolean.FALSE;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }

    public void setNomeProfissional(String nomeProfissional) {
        this.nomeProfissional = nomeProfissional;
    }

    public List<Long> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<Long> privilegios) {
        this.privilegios = privilegios;
    }

    public List<Long> getOrganizacoes() {
        return organizacoes;
    }

    public void setOrganizacoes(List<Long> organizacoes) {
        this.organizacoes = organizacoes;
    }

    public Long getOrganizacaoDefault() {
        return organizacaoDefault;
    }

    public void setOrganizacaoDefault(Long organizacaoDefault) {
        this.organizacaoDefault = organizacaoDefault;
    }

    public List<Long> getTiposAgendamentos() {
        return tiposAgendamentos;
    }

    public void setTiposAgendamentos(List<Long> tiposAgendamentos) {
        this.tiposAgendamentos = tiposAgendamentos;
    }

    public List<Long> getConfiguradoresEspeciais() {
        return configuradoresEspeciais;
    }

    public void setConfiguradoresEspeciais(List<Long> configuradoresEspeciais) {
        this.configuradoresEspeciais = configuradoresEspeciais;
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
        if (BasicFunctions.isNotEmpty(ativo)) {
            return ativo;
        }
        return Boolean.FALSE;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getGenero() {
        return genero;
    }

    public void setGenero(Long genero) {
        this.genero = genero;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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
