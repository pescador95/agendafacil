package app.api.agendaFacil.management.auth.validator;

import app.api.agendaFacil.business.contrato.entity.Contrato;
import app.api.agendaFacil.business.contrato.entity.TipoContrato;
import app.api.agendaFacil.business.contrato.loader.ContratoLoader;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.management.auth.entity.Auth;
import app.api.agendaFacil.management.database.redis.RedisService;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@ApplicationScoped
public class AuthValidator {

    final RedisService redisClient;
    final ContratoLoader contratoLoader;
    final OrganizacaoLoader organizacaoLoader;
    final UsuarioLoader usuarioLoader;
    final AuthValidator authValidator;
    final JWTParser parser;
    int maxSessionsAllowed;
    int activeSessions;
    Responses responses;
    Organizacao organizacao;
    Contrato contrato;
    TipoContrato tipoContrato;

    public AuthValidator(RedisService redisClient, AuthValidator authValidator, ContratoLoader contratoLoader, OrganizacaoLoader organizacaoLoader, JWTParser parser, UsuarioLoader usuarioLoader) {
        this.redisClient = redisClient;
        this.authValidator = authValidator;
        this.contratoLoader = contratoLoader;
        this.organizacaoLoader = organizacaoLoader;
        this.parser = parser;
        this.usuarioLoader = usuarioLoader;
    }

    public Responses validaAutenticacao(Usuario usuario, String userAgent, Auth data) {

        responses = new Responses();

        if (BasicFunctions.isNull(usuario)) {

            responses.setMessages("Credenciais não encontradas.");
            responses.setStatus(404);
            responses.setData(data);
            return responses;
        }

        validaUsuarioOrganizacao(usuario, data, userAgent);
        validaOrganizacao(usuario);
        validaContrato(usuario, userAgent);

        return responses;
    }

    public Responses validaReautenticacao(Usuario usuario, String userAgent, Auth data) throws ParseException {

        if (BasicFunctions.isNotEmpty(usuario)) {
            loadInformacoesByUsuario(usuario, userAgent);
            validaOrganizacao(usuario);
            validaContrato(usuario, userAgent);
        }
        validaExpiracaoToken(data);

        return responses;
    }

    private int getMaxSessionsAllowedForOrganization() {

        if (BasicFunctions.isNotEmpty(contrato)) {
            return contrato.getNumeroMaximoSessoes();
        }
        return 0;

    }

    public void validaUsuarioOrganizacao(Usuario usuario, Auth data, String userAgent) {

        if (BasicFunctions.isNotEmpty(usuario)) {

            loadInformacoesByUsuario(usuario, userAgent);

        }

        if (!usuario.checkPassword(data.getPassword())) {

            responses.setStatus(404);
            responses.setData(data);
            responses.setMessages("Credenciais inválidas.");
        }
    }

    public void validaOrganizacao(Usuario usuario) {

        responses = new Responses();

        if (BasicFunctions.isEmpty(organizacao)) {

            responses.setStatus(400);
            responses.setMessages(
                    "Não foi possível localizar um contrato para a organização do usuário " + usuario.getLogin() + ".");
            return;
        }

        if (BasicFunctions.isNotEmpty(contrato) && !contrato.contratoAtivo(organizacao)) {
            responses.setStatus(400);
            responses.setMessages(
                    "O contrato da Organização " + organizacao.getNome() + " não possui vigência. Entre em contato com a equipe do Agenda Fácil.");
        }
    }

    public void validaContrato(Usuario usuario, String userAgent) {

        if (BasicFunctions.isEmpty(tipoContrato)) {
            responses.setStatus(400);
            responses.setMessages("Não foi possível localizar um contrato para a organização do usuário "
                    + usuario.getLogin() + ".");
            return;
        }

        if (BasicFunctions.isNotEmpty(tipoContrato)) {

            if (tipoContrato.sessaoUnica()) {
                validaSessaoUnica(usuario, userAgent);
            }

            if (tipoContrato.sessaoCompartilhada()) {
                validaSessaoCompartilhada();
            }
        }
    }

    public void validaSessaoUnica(Usuario usuario, String userAgent) {

        String existingKey = RedisService.makeSessionKeyPattern(usuario, userAgent);

        if (BasicFunctions.isNotEmpty(existingKey)) {
            redisClient.delByKey(existingKey);
        }

        activeSessions = redisClient.countActiveSessionsForUserAndOrganization(usuario, false, userAgent);

        if (BasicFunctions.isValid(activeSessions) && activeSessions == 1) {
            responses = new Responses();
            responses.setStatus(400);
            responses.setMessages(new ArrayList<>());
            responses.setMessages(
                    "O Tipo de Contrato da sua Organização é: Sessão Única e você já possui uma sessão ativa.");
        }
    }

    public void validaSessaoCompartilhada() {

        if (activeSessions >= maxSessionsAllowed) {
            responses.setStatus(400);
            responses.setMessages("Número máximo de sessões atingido para a organização "
                    + organizacao.getNome() + ". Número limite de sessões simultâneas permitidas: "
                    + maxSessionsAllowed);
        }
    }

    public void validaExpiracaoToken(Auth data) throws ParseException {

        LocalDateTime expireDate;

        String login = parser.parse(data.getRefreshToken()).getClaim("upn");

        long expireDateOldToken = parser.parse(data.getRefreshToken()).getClaim("exp");

        Usuario usuario = usuarioLoader.findByLoginEmail(login);


        organizacao = OrganizacaoRepository.findById(usuario.getOrganizacaoDefaultId());

        expireDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(expireDateOldToken),
                ZoneId.of(organizacao.getZoneId()));

        if (BasicFunctions.isEmpty(usuario) || BasicFunctions.isNotEmpty(usuario) && expireDate.isBefore(Contexto.dataHoraContexto(organizacao.getZoneId()))) {
            responses.setStatus(400);
            responses.setData(data);
            responses.setMessages("Credenciais expiradas.");
        }
    }

    public void loadInformacoesByUsuario(Usuario usuario, String userAgent) {

        organizacao = organizacaoLoader.findById(usuario.getOrganizacaoDefaultId());

        contrato = contratoLoader.loadByOrganizacao(organizacao);

        maxSessionsAllowed = getMaxSessionsAllowedForOrganization();

        tipoContrato = ContratoLoader.getTipoContratoByUsuarioOrganizacaoDefault(usuario);

        activeSessions = redisClient.countActiveSessionsForUserAndOrganization(usuario, false, userAgent);
    }
}
