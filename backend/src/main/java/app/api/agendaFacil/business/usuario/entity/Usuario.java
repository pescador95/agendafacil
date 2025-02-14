package app.api.agendaFacil.business.usuario.entity;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.entity.ConfiguradorAgendamentoEspecial;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.management.auth.DTO.AuthDTO;
import app.api.agendaFacil.management.role.entity.Role;
import app.core.application.entity.Audit;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@UserDefinition
public class Usuario extends Audit {

    public static final Long USUARIO = 1L;
    public static final Long BOT = 2L;
    public static final Long ADMINISTRADOR = 3L;
    @Column()
    @SequenceGenerator(name = "usuarioIdSequence", sequenceName = "usuario_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn(name = "id")
    @Id
    private Long id;

    @Column
    @Password
    private String password;

    @Column
    @Username
    private String login;

    @Column()
    private Boolean alterarSenha = false;

    @Column()
    private String token;

    @Column()
    private LocalDateTime dataToken;

    @Column()
    private Boolean bot = false;

    @Column()
    private Long organizacaoDefaultId;

    @ManyToOne
    @JoinColumn(name = "pessoaId")
    private Pessoa pessoa;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarioorganizacao", joinColumns = {
            @JoinColumn(name = "usuarioId")}, inverseJoinColumns = {
            @JoinColumn(name = "organizacaoId")})
    private List<Organizacao> organizacoes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarioroles", joinColumns = {
            @JoinColumn(name = "usuarioId")}, inverseJoinColumns = {
            @JoinColumn(name = "roleId")})
    @Roles
    private List<Role> privilegio = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tipoagendamentousuarios", joinColumns = {
            @JoinColumn(name = "profissionalId")}, inverseJoinColumns = {
            @JoinColumn(name = "tipoAgendamentoId")})
    private List<TipoAgendamento> tiposAgendamentos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "configuradoragendamentoespecialusuario", joinColumns = {
            @JoinColumn(name = "profissionalId")}, inverseJoinColumns = {
            @JoinColumn(name = "configuradorAgendamentoEspecialId")})
    private List<ConfiguradorAgendamentoEspecial> configuradoresEspeciais = new ArrayList<>();

    @Column()
    private Boolean ativo;


    public Usuario() {
        super();
    }

    public Usuario(Long id) {
        super();
        this.id = id;
    }

    public Usuario(AuthDTO authDTO) {
        super();
        this.token = authDTO.getToken();
        this.password = authDTO.getPassword();
    }

    public Usuario(AuthDTO authDTO, String token) {
        super();
        this.token = token;
        this.password = authDTO.getPassword();
    }

    public Usuario(String token, String password) {
        super();
        this.token = token;
        this.password = password;
    }

    @Inject
    public Usuario(SecurityContext context) {
        super(context);
    }

    public Usuario(EntidadeDTO entity) {

        if (BasicFunctions.isNotEmpty(entity)) {
            this.id = entity.getId();
            this.login = entity.getLogin();
            this.ativo = entity.getAtivo();
            this.alterarSenha = entity.getAlterarSenha();
            this.bot = entity.getBot();
            this.setPassword(entity.getPassword());

            this.organizacaoDefaultId = entity.getOrganizacaoDefault();

            if (BasicFunctions.isNotEmpty(entity.getPrivilegios())) {
                this.privilegio = new ArrayList<>();
                entity.getPrivilegios().forEach(roleDTO -> this.privilegio.add(new Role(roleDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getOrganizacoes())) {
                this.organizacoes = new ArrayList<>();
                entity.getOrganizacoes().forEach(organizacaoDTO -> this.organizacoes.add(new Organizacao(organizacaoDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getTiposAgendamentos())) {
                this.tiposAgendamentos = new ArrayList<>();
                entity.getTiposAgendamentos().forEach(tipoAgendamentoDTO -> this.tiposAgendamentos.add(new TipoAgendamento(tipoAgendamentoDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getConfiguradoresEspeciais())) {
                this.configuradoresEspeciais = new ArrayList<>();
                entity.getConfiguradoresEspeciais().forEach(configuradorAgendamentoEspecialDTO -> this.configuradoresEspeciais
                        .add(new ConfiguradorAgendamentoEspecial(configuradorAgendamentoEspecialDTO)));
            }
        }
    }

    public Usuario(UsuarioDTO entity) {
        super();
        if (BasicFunctions.isNotEmpty(entity)) {
            this.id = entity.getId();
            this.login = entity.getLogin();
            this.ativo = entity.getAtivo();
            this.alterarSenha = entity.getAlterarSenha();
            this.bot = entity.getBot();
            this.setPassword(entity.getPassword());

            if (BasicFunctions.isNotEmpty(entity.getOrganizacaoDefault())) {
                this.organizacaoDefaultId = entity.getOrganizacaoDefault();
            }
            if (BasicFunctions.isNotEmpty(entity.getPessoa())) {
                this.pessoa = new Pessoa(entity.getPessoa());
            }

            if (BasicFunctions.isNotEmpty(entity.getPrivilegios())) {
                this.privilegio = new ArrayList<>();
                entity.getPrivilegios().forEach(roleDTO -> this.privilegio.add(new Role(roleDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getOrganizacoes())) {
                this.organizacoes = new ArrayList<>();
                entity.getOrganizacoes().forEach(organizacaoDTO -> this.organizacoes.add(new Organizacao(organizacaoDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getTiposAgendamentos())) {
                this.tiposAgendamentos = new ArrayList<>();
                entity.getTiposAgendamentos().forEach(tipoAgendamentoDTO -> this.tiposAgendamentos.add(new TipoAgendamento(tipoAgendamentoDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getConfiguradoresEspeciais())) {
                this.configuradoresEspeciais = new ArrayList<>();
                entity.getConfiguradoresEspeciais().forEach(configuradorAgendamentoEspecialDTO -> this.configuradoresEspeciais
                        .add(new ConfiguradorAgendamentoEspecial(configuradorAgendamentoEspecialDTO)));
            }
        }
    }

    public Usuario(UsuarioDTO entity, SecurityContext context) {
        super(context);

        if (BasicFunctions.isNotEmpty(entity)) {
            this.id = entity.getId();
            this.login = entity.getLogin();
            this.ativo = entity.getAtivo();
            this.alterarSenha = entity.getAlterarSenha();
            this.bot = entity.getBot();
            this.setPassword(entity.getPassword());

            if (BasicFunctions.isNotEmpty(entity.getOrganizacaoDefault())) {
                this.organizacaoDefaultId = entity.getOrganizacaoDefault();
            }
            if (BasicFunctions.isNotEmpty(entity.getPessoa())) {
                this.pessoa = new Pessoa(entity.getPessoa(), context);
            }

            if (BasicFunctions.isNotEmpty(entity.getPrivilegios())) {
                this.privilegio = new ArrayList<>();
                entity.getPrivilegios().forEach(roleDTO -> this.privilegio.add(new Role(roleDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getOrganizacoes())) {
                this.organizacoes = new ArrayList<>();
                entity.getOrganizacoes().forEach(organizacaoDTO -> this.organizacoes.add(new Organizacao(organizacaoDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getTiposAgendamentos())) {
                this.tiposAgendamentos = new ArrayList<>();
                entity.getTiposAgendamentos().forEach(tipoAgendamentoDTO -> this.tiposAgendamentos.add(new TipoAgendamento(tipoAgendamentoDTO)));
            }

            if (BasicFunctions.isNotEmpty(entity.getConfiguradoresEspeciais())) {
                this.configuradoresEspeciais = new ArrayList<>();
                entity.getConfiguradoresEspeciais().forEach(configuradorAgendamentoEspecialDTO -> this.configuradoresEspeciais
                        .add(new ConfiguradorAgendamentoEspecial(configuradorAgendamentoEspecialDTO)));
            }
        }
    }


    public Usuario(EntidadeDTO entity, Pessoa pessoa, List<Role> roles, List<Organizacao> organizacoes, List<TipoAgendamento> tiposAgendamentos, SecurityContext context) {


        if (BasicFunctions.isNotEmpty(entity)) {
            this.ativo = entity.getAtivo();
            this.alterarSenha = entity.getAlterarSenha();
            this.organizacaoDefaultId = entity.getOrganizacaoDefault();
            this.pessoa = pessoa;
            this.privilegio = roles;
            this.organizacoes = organizacoes;
            this.tiposAgendamentos = tiposAgendamentos;
            this.setPassword(entity.getPassword());

            if (BasicFunctions.isNotEmpty(entity.getLogin())) {
                this.setLogin(entity.getLogin().toLowerCase());
            }
            if (BasicFunctions.isNotEmpty(entity.getBot())) {
                this.setBot(entity.getBot());
            }

            this.setAlterarSenha(Boolean.FALSE);
            this.setAtivo(Boolean.TRUE);
            this.createAudit(context);
        }
    }

    public Usuario(UsuarioDTO entity, Pessoa pessoa, List<Role> roles, List<Organizacao> organizacoes, List<TipoAgendamento> tiposAgendamentos, SecurityContext context) {


        if (BasicFunctions.isNotEmpty(entity)) {
            this.login = entity.getLogin();
            this.ativo = entity.getAtivo();
            this.alterarSenha = entity.getAlterarSenha();
            this.bot = entity.getBot();
            this.organizacaoDefaultId = entity.getOrganizacaoDefault();
            this.pessoa = pessoa;
            this.privilegio = roles;
            this.organizacoes = organizacoes;
            this.tiposAgendamentos = tiposAgendamentos;
            this.setPassword(entity.getPassword());

            if (BasicFunctions.isNotEmpty(entity.getLogin())) {
                this.setLogin(entity.getLogin().toLowerCase());
            }

            if (BasicFunctions.isNotEmpty(entity.getBot())) {
                this.setBot(entity.getBot());
            }
            Role roleDefault = new Role();

            if (!this.getBot()) {
                roleDefault.setUsuario();
                this.getPrivilegio().add(roleDefault);
            }
            if (this.getBot()) {
                roleDefault.setBot();
                this.getPrivilegio().add(roleDefault);
            }
            this.setAlterarSenha(Boolean.FALSE);
            this.setAtivo(Boolean.TRUE);
            this.createAudit(context);
        }
    }

    public Usuario usuario(Usuario entityOld, EntidadeDTO entity, Pessoa pessoa, List<Role> roles, List<Organizacao> organizacoes,
                           List<TipoAgendamento> tiposAgendamentos, SecurityContext context) {

        entityOld.login = entity.getLogin();
        entityOld.ativo = entity.getAtivo();
        entityOld.alterarSenha = entity.getAlterarSenha();
        entityOld.bot = entity.getBot();
        entityOld.organizacaoDefaultId = entity.getOrganizacaoDefault();
        entityOld.setId(entity.getId());
        entityOld.setPessoa(pessoa);
        entityOld.setPassword(entity.getPassword());


        if (BasicFunctions.isNotEmpty(entity.getLogin())) {
            entityOld.setLogin(entity.getLogin().toLowerCase());
        }
        if (BasicFunctions.isNotEmpty(roles)) {
            entityOld.setPrivilegio(new ArrayList<>());
            entityOld.getPrivilegio().addAll(roles);
        }

        if (BasicFunctions.isNotEmpty(organizacoes)) {
            entityOld.setOrganizacoes(new ArrayList<>());
            entityOld.getOrganizacoes().addAll(organizacoes);
        }
        if (BasicFunctions.isNotEmpty(tiposAgendamentos)) {
            entityOld.setTiposAgendamentos(new ArrayList<>());
            entityOld.getTiposAgendamentos().addAll(tiposAgendamentos);
        }
        if (BasicFunctions.isNotEmpty(entity.getBot())) {
            entityOld.setBot(entity.getBot());
        }

        Role roleDefault = new Role();

        if (!entityOld.getBot()) {
            roleDefault.setUsuario();
            entityOld.getPrivilegio().add(roleDefault);
        }
        if (entityOld.getBot()) {
            roleDefault.setBot();
            entityOld.getPrivilegio().add(roleDefault);
        }
        entityOld.setAlterarSenha(Boolean.FALSE);
        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }

    public Usuario deletarUsuario(Usuario entity, SecurityContext context) {

        entity.setAtivo(Boolean.FALSE);
        entity.deleteAudit(context);
        return entity;
    }

    public Usuario reativarUsuario(Usuario entity, SecurityContext context) {

        entity.setAtivo(Boolean.TRUE);
        entity.restoreAudit(context);
        return entity;
    }

    public Boolean bot() {
        return this.privilegio.stream().anyMatch(role -> role.getId().equals(BOT));
    }

    public Boolean admin() {
        return this.privilegio.stream().anyMatch(role -> role.getId().equals(ADMINISTRADOR));
    }

    public Boolean user() {
        return this.privilegio.stream().anyMatch(role -> role.getId().equals(USUARIO));
    }

    public Boolean hasRole() {
        return BasicFunctions.isValid(this) && BasicFunctions.isNotEmpty(this.privilegio);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
        return bot;
    }

    public void setBot(Boolean bot) {
        this.bot = bot;
    }

    public List<Role> getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(List<Role> privilegio) {
        this.privilegio = privilegio;
    }

    public List<Organizacao> getOrganizacoes() {
        return organizacoes;
    }

    public void setOrganizacoes(List<Organizacao> organizacoes) {
        this.organizacoes = organizacoes;
    }

    public List<TipoAgendamento> getTiposAgendamentos() {
        return this.tiposAgendamentos;
    }

    public void setTiposAgendamentos(List<TipoAgendamento> tiposAgendamentos) {
        this.tiposAgendamentos = tiposAgendamentos;
    }

    public List<ConfiguradorAgendamentoEspecial> getConfiguradoresEspeciais() {
        return configuradoresEspeciais;
    }

    public void setConfiguradoresEspeciais(List<ConfiguradorAgendamentoEspecial> configuradoresEspeciais) {
        this.configuradoresEspeciais = configuradoresEspeciais;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getOrganizacaoDefaultId() {
        return this.organizacaoDefaultId;
    }

    public void setOrganizacaoDefaultId(Long organizacaoDefaultId) {
        this.organizacaoDefaultId = organizacaoDefaultId;
    }

    public Pessoa getPessoa() {

        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa;
        }
        return null;
    }

    public void setPessoa(Pessoa pessoa) {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            this.pessoa = pessoa;
        }
    }

    public Long getTelegramId() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getTelegramId();
        }
        return null;
    }

    public Long getWhatsappId() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getWhatsappId();
        }
        return null;
    }

    public String nomeProfissional() {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return pessoa.getNome();
        }
        return null;
    }

    public Boolean canChangePassword() {
        return BasicFunctions.isNotEmpty(this) && this.getDataToken().isAfter(Contexto.dataHoraContexto(this.getOrganizacaoDefaultId()));
    }

    public Boolean checkPassword(String password) {
        return BasicFunctions.isNotEmpty(this) && BasicFunctions.checkPassword(this.getPassword(), password);
    }

    public void setNewPassword(String password) {
        this.setPassword(password);
        this.setAlterarSenha(Boolean.FALSE);
        this.setToken(null);
        this.setDataToken(null);
    }

    public String greetings(Boolean again) {

        String nome = "usuário";

        if (BasicFunctions.isNotEmpty(this, this.nomeProfissional())) {
            nome = this.nomeProfissional();
        }
        if (again) {
            return "Bem vindo novamente, " + nome + "!";
        }
        return "Bem vindo, " + nome + "!";
    }
}
