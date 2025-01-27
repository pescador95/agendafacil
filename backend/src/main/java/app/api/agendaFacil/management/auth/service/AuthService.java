package app.api.agendaFacil.management.auth.service;

import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.business.usuario.validator.UsuarioValidator;
import app.api.agendaFacil.management.auth.DTO.AuthDTO;
import app.api.agendaFacil.management.auth.entity.Auth;
import app.api.agendaFacil.management.auth.manager.AuthManager;
import app.api.agendaFacil.management.auth.validator.AuthValidator;
import app.api.agendaFacil.management.database.redis.RedisService;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.AuthToken;
import app.core.helpers.utils.BasicFunctions;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

import static app.core.helpers.utils.AuthToken.generateUUID;

@RequestScoped
public class AuthService extends AuthManager {

    private Usuario usuario;

    public AuthService() {
        super();
    }

    @Inject
    public AuthService(AuthToken token, RedisService redisClient, JWTParser parser, HttpServerRequest request, UsuarioLoader usuarioLoader, UsuarioValidator usuarioValidator, AuthValidator authValidator) {
        super(token, redisClient, parser, request, usuarioLoader, usuarioValidator, authValidator);
    }

    public Response login(AuthDTO dataDTO, String userAgent) throws ParseException {

        try {

            responses = new Responses();

            responses.setMessages(new ArrayList<>());

            Auth data = new Auth(dataDTO);

            usuario = getUserLogin(data);

            responses = authValidator.validaAutenticacao(usuario, userAgent, data);

            if (BasicFunctions.isNotEmpty(usuario) && responses.getOk()) {

                Auth auth = createAuth(usuario, userAgent);

                responses = new Responses(200, auth, usuario.greetings(false));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response refreshToken(AuthDTO dataDTO, String userAgent) {

        try {

            responses = new Responses();

            responses.setMessages(new ArrayList<>());

            Auth data = new Auth(dataDTO);

            usuario = getUserRefreshLogin(data);

            responses = authValidator.validaReautenticacao(usuario, userAgent, data);

            if (BasicFunctions.isNotEmpty(usuario) && responses.getOk()) {

                Auth auth = createAuth(usuario, userAgent);

                responses = new Responses(200, auth, usuario.greetings(true));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }


    public Usuario getUserLogin(Auth data) {

        if (BasicFunctions.isNotEmpty(data, data.getLogin())) {

            return usuarioLoader.findByLoginEmail(data.getLogin());
        }
        return null;
    }

    public Usuario getUserRefreshLogin(Auth data) throws ParseException {

        if (BasicFunctions.isNotEmpty(data)) {

            String login = parser.parse(data.getRefreshToken()).getClaim("upn");

            return usuarioLoader.findByLoginEmail(login);
        }
        return null;
    }


    public Auth createAuth(Usuario usuario, String userAgent) throws ParseException {

        String uuid = generateUUID();

        String accessToken = token.generateAccessToken(usuario, userAgent, uuid);
        String refreshToken = token.generateRefreshToken(usuario, uuid);

        Long ACTOKEN = parser.parse(accessToken).getClaim("exp");
        Long RFTOKEN = parser.parse(refreshToken).getClaim("exp");

        return new Auth(usuario, accessToken, refreshToken, ACTOKEN, RFTOKEN, uuid);
    }
}