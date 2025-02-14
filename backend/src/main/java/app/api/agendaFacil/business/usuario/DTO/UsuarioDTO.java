package app.api.agendaFacil.business.usuario.DTO;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.helpers.utils.BasicFunctions;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    LocalDateTime dataToken;

    private Long id;

    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean alterarSenha;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean bot;

    private PessoaDTO pessoa;

    private String nomeProfissional;

    private List<Long> privilegios;

    private List<Long> organizacoes;

    private Long organizacaoDefault;

    private List<Long> tiposAgendamentos;

    private List<Long> configuradoresEspeciais;

    private Long telegramId;

    private Long whatsappId;

    private Boolean ativo;

    private String tenant;

    public UsuarioDTO() {

    }

    public UsuarioDTO(Long id) {
        this.id = id;
    }

    public UsuarioDTO(String login) {
        this.login = login;
    }

    public UsuarioDTO(Usuario usuario) {

        if (BasicFunctions.isNotEmpty(usuario)) {

            this.id = usuario.getId();

            this.password = usuario.getPassword();

            this.login = usuario.getLogin();

            this.alterarSenha = usuario.getAlterarSenha();

            this.token = usuario.getToken();

            this.dataToken = usuario.getDataToken();

            this.bot = usuario.getBot();

            this.nomeProfissional = usuario.nomeProfissional();

            this.ativo = usuario.getAtivo();

            this.pessoa = new PessoaDTO(usuario.getPessoa());

            this.telegramId = usuario.getTelegramId();

            this.whatsappId = usuario.getWhatsappId();

            if (BasicFunctions.isNotEmpty(usuario.getPrivilegio())) {
                this.privilegios = new ArrayList<>();
                usuario.getPrivilegio().forEach(privilegio -> this.privilegios.add(privilegio.getId()));
            }

            if (BasicFunctions.isNotEmpty(usuario.getOrganizacoes())) {
                this.organizacoes = new ArrayList<>();
                usuario.getOrganizacoes().forEach(organizacao -> this.organizacoes.add(organizacao.getId()));
            }

            this.organizacaoDefault = usuario.getOrganizacaoDefaultId();

            if (BasicFunctions.isNotEmpty(usuario.getTiposAgendamentos())) {
                this.tiposAgendamentos = new ArrayList<>();
                usuario.getTiposAgendamentos().forEach(tipoAgendamento -> this.tiposAgendamentos.add(tipoAgendamento.getId()));
            }
            if (BasicFunctions.isNotEmpty(usuario.getConfiguradoresEspeciais())) {
                this.configuradoresEspeciais = new ArrayList<>();
                usuario.getConfiguradoresEspeciais().forEach(configurador -> this.configuradoresEspeciais.add(configurador.getId()));
            }
        }
    }

    public UsuarioDTO(Usuario usuario, List<Long> privilegios, List<Long> organizacoes,
                      List<Long> tiposAgendamentos,
                      List<Long> configuradoresEspeciais) {

        if (BasicFunctions.isNotEmpty(usuario, privilegios, organizacoes, tiposAgendamentos, configuradoresEspeciais)) {
            this.id = usuario.getId();

            this.password = usuario.getPassword();

            this.login = usuario.getLogin();

            this.alterarSenha = usuario.getAlterarSenha();

            this.token = usuario.getToken();

            this.dataToken = usuario.getDataToken();

            this.bot = usuario.getBot();

            this.nomeProfissional = usuario.nomeProfissional();

            this.organizacaoDefault = usuario.getOrganizacaoDefaultId();

            this.pessoa = new PessoaDTO(usuario.getPessoa());

            this.telegramId = usuario.getTelegramId();

            this.whatsappId = usuario.getWhatsappId();

            this.ativo = usuario.getAtivo();

            if (BasicFunctions.isNotEmpty(privilegios)) {
                this.privilegios = privilegios;
            }

            if (BasicFunctions.isNotEmpty(organizacoes)) {
                this.organizacoes = organizacoes;
            }

            if (BasicFunctions.isNotEmpty(usuario.getTiposAgendamentos())) {
                this.tiposAgendamentos = tiposAgendamentos;
            }

            if (BasicFunctions.isNotEmpty(usuario.getConfiguradoresEspeciais())) {
                this.configuradoresEspeciais = configuradoresEspeciais;
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
        if (BasicFunctions.isNotEmpty(password)) {
            this.password = BasicFunctions.setCryptedPassword(password);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getAlterarSenha() {
        return alterarSenha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getBot() {
        return bot;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }

    public List<Long> getPrivilegios() {
        return privilegios;
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

    public List<Long> getTiposAgendamentos() {
        return tiposAgendamentos;
    }

    public List<Long> getConfiguradoresEspeciais() {
        return configuradoresEspeciais;
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

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public String loadPessoaCpf() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getCpf();
        }
        return null;
    }

    public LocalDate loadPessoaDataNascimento() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getDataNascimento();
        }
        return null;
    }

    public String loadPessoaNome() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getNome();
        }
        return null;
    }

    public String loadPessoaTelefone() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getTelefone();
        }
        return null;
    }

    public String loadPessoaCelular() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getCelular();
        }
        return null;
    }

    public String loadPessoaEmail() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getEmail();
        }
        return null;
    }

    public Endereco loadPessoaEndereco() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getEndereco();
        }
        return null;
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
