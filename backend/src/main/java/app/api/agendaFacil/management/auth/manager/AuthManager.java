package app.api.agendaFacil.management.auth.manager;

import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.business.usuario.validator.UsuarioValidator;
import app.api.agendaFacil.management.auth.validator.AuthValidator;
import app.api.agendaFacil.management.database.redis.RedisService;
import app.core.application.ContextManager;
import app.core.helpers.utils.AuthToken;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;

@RequestScoped
public abstract class AuthManager extends ContextManager {

    protected final AuthToken token;

    protected final RedisService redisClient;

    protected final JWTParser parser;
    @Context
    protected final HttpServerRequest request;

    protected final UsuarioLoader usuarioLoader;

    protected final UsuarioValidator usuarioValidator;

    protected final AuthValidator authValidator;

    protected String clientIp;

    protected String hostname;

    @Inject
    protected AuthManager(AuthToken token, RedisService redisClient, JWTParser parser, HttpServerRequest request, UsuarioLoader usuarioLoader, UsuarioValidator usuarioValidator, AuthValidator authValidator) {
        this.token = token;
        this.redisClient = redisClient;
        this.parser = parser;
        this.request = request;
        this.usuarioLoader = usuarioLoader;
        this.usuarioValidator = usuarioValidator;
        this.authValidator = authValidator;
        this.clientIp = request.getHeader("X-Forwarded-For");
        this.hostname = request.getHeader("Host");
    }

    protected AuthManager() {
        this.token = null;
        this.redisClient = null;
        this.parser = null;
        this.request = null;
        this.usuarioLoader = null;
        this.usuarioValidator = null;
        this.authValidator = null;
        this.clientIp = null;
        this.hostname = null;
    }
}
