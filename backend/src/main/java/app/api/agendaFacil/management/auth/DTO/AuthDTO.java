package app.api.agendaFacil.management.auth.DTO;

import app.core.helpers.utils.Contexto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class AuthDTO {

    private String login;
    private String password;
    private String tenant;
    private String token;
    private String accessToken;
    private String refreshToken;
    private Boolean admin;
    private LocalDateTime expireDateAccessToken;
    private LocalDateTime expireDateRefreshToken;
    private Long organizacaoDefault;


    public AuthDTO() {

    }

    public AuthDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public AuthDTO(String login, String password, String tenant, String accessToken, String refreshToken, Long ACTOKEN, Long RFTOKEN, Long organizacaoDefault) {
        this.login = login;
        this.password = password;
        this.tenant = tenant;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.setExpireDateAccessToken(LocalDateTime.ofEpochSecond(ACTOKEN, 0, ZoneOffset.of(Contexto.defaultZoneIdToString())));
        this.setExpireDateRefreshToken(LocalDateTime.ofEpochSecond(RFTOKEN, 0, ZoneOffset.of(Contexto.defaultZoneIdToString())));
        this.setOrganizacaoDefault(organizacaoDefault);
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
        this.password = password;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireDateAccessToken() {
        return expireDateAccessToken;
    }

    public void setExpireDateAccessToken(LocalDateTime expireDateAccessToken) {
        this.expireDateAccessToken = expireDateAccessToken;
    }

    public LocalDateTime getExpireDateRefreshToken() {
        return expireDateRefreshToken;
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
}
