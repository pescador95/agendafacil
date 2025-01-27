package app.api.agendaFacil.management.auth.entity;

import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.management.auth.DTO.AuthDTO;
import app.api.agendaFacil.management.role.entity.Role;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.RequestScoped;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class Auth {
    LocalDateTime expireDateAccessToken;
    LocalDateTime expireDateRefreshToken;
    String uuid;
    private String accessToken;
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Role> privilegio;
    private List<String> roles;
    private Boolean admin;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Usuario usuario;
    private String refreshToken;
    private String tenant;
    private Long organizacaoDefault;

    public Auth(Usuario usuario, String accessToken, String refreshToken, Long ACTOKEN, Long RFTOKEN, String uuid) throws ParseException {

        try {

            List<String> privilegios = new ArrayList<>();

            if (usuario.hasRole()) {
                usuario.getPrivilegio().forEach(c -> privilegios.add(c.getPrivilegio()));
            }

            Organizacao organizacao = OrganizacaoRepository.findById(usuario.getOrganizacaoDefaultId());

            this.setLogin(usuario.getLogin());
            this.setPassword(usuario.getPassword());
            this.setUUID(uuid);
            this.setPrivilegio(new ArrayList<>());
            this.setRoles(new ArrayList<>());
            this.getPrivilegio().addAll(usuario.getPrivilegio());
            this.getRoles().addAll(privilegios);
            this.setAdmin(Contexto.isUserAdmin(usuario));
            this.setAccessToken(accessToken);
            this.setRefreshToken(refreshToken);
            this.setUsuario(usuario);
            this.setExpireDateAccessToken(LocalDateTime.ofEpochSecond(ACTOKEN, 0, ZoneOffset.of(organizacao.getTimeZoneOffset())));
            this.setExpireDateRefreshToken(LocalDateTime.ofEpochSecond(RFTOKEN, 0, ZoneOffset.of(organizacao.getTimeZoneOffset())));
            this.setOrganizacaoDefault(usuario.getOrganizacaoDefaultId());
        } catch (Exception e) {
            throw new ParseException(e.getMessage());
        }
    }

    public Auth() {

    }

    public Auth(AuthDTO authDTO) {
        this.password = authDTO.getPassword();
        this.login = authDTO.getLogin();
        this.accessToken = authDTO.getAccessToken();
        this.refreshToken = authDTO.getRefreshToken();
    }

    public Auth(AuthDTO authDTO, String tenant) {
        this.password = authDTO.getPassword();
        this.login = authDTO.getLogin();
        this.accessToken = authDTO.getAccessToken();
        this.refreshToken = authDTO.getRefreshToken();
        this.tenant = tenant;
    }

    public Auth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (BasicFunctions.isNotEmpty(password)) {
            this.password = password;
        }
    }

    public List<Role> getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(List<Role> privilegio) {
        this.privilegio = privilegio;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setExpireDateAccessToken(LocalDateTime expireDateAccessToken) {
        this.expireDateAccessToken = expireDateAccessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpireDateRefreshToken(LocalDateTime expireDateRefreshToken) {
        this.expireDateRefreshToken = expireDateRefreshToken;
    }

    public Long getOrganizacaoDefault() {
        return organizacaoDefault;
    }

    public void setOrganizacaoDefault(Long organizacaoDefault) {
        this.organizacaoDefault = organizacaoDefault;
    }

    private void setUUID(String uuid) {
        this.uuid = uuid;
    }
}
