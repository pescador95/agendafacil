package app.api.agendaFacil.management.database.redis;

import app.api.agendaFacil.business.contrato.entity.TipoContrato;
import app.api.agendaFacil.business.contrato.loader.ContratoLoader;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.DTO.Responses;
import app.core.helpers.trace.RemoteHostKeyGenerator;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.redis.client.RedisAPI;
import io.vertx.mutiny.redis.client.Response;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

import java.util.ArrayList;
import java.util.Collections;

import static app.core.helpers.utils.BasicFunctions.log;

@RequestScoped
public class RedisService extends RedisManager {

    public RedisService() {
        super();
    }

    @Inject
    public RedisService(SecurityContext context, RedisAPI redisAPI) {
        super(context, redisAPI);
    }


    public static String makeSessionKeyPattern(Usuario usuario, String userAgent) {

        return getPattern(usuario) + getKey(userAgent);
    }

    public static String getPattern(Usuario usuario) {

        TipoContrato tipoContrato = ContratoLoader.getTipoContratoByUsuarioOrganizacaoDefault(usuario);

        if (BasicFunctions.isNotEmpty(tipoContrato)) {

            Organizacao organizacao = OrganizacaoRepository.findById(usuario.getOrganizacaoDefaultId());

            String pattern = "user_" + usuario.getLogin() + "_tenant_" + organizacao.getTenant() + "_organizacaoId_" + usuario.getOrganizacaoDefaultId() + "_userAgent_";

            String session = tipoContrato.getTipoSessao();
            return session + pattern;
        }
        return "";
    }

    public static String getKey(String userAgent) {

        RemoteHostKeyGenerator remoteHostKeyGenerator = new RemoteHostKeyGenerator();

        return remoteHostKeyGenerator.generateKey(userAgent);
    }

    public static String getSessionDefaultPattern(Usuario usuario, String userAgent, Boolean withKey) {

        String key = getKey(userAgent);

        String pattern = getPattern(usuario);

        if (withKey) {
            return pattern + key;
        }
        return pattern + "*";

    }

    private static void logSessoesAtivas(TipoContrato tipoContrato, Usuario usuario, Boolean firstTime, Integer count,
                                         String userAgent) {

        String pattern = getSessionDefaultPattern(usuario, userAgent, true);

        if (firstTime) {
            log("Número de sessões ativas: " + count + " para o usuário: " + usuario.getLogin(), getTraceMethods());

            if (BasicFunctions.isNotEmpty(tipoContrato)) {
                String patternName = tipoContrato.getTipoContrato() + " com o pattern " + pattern;
                log(patternName, getTraceMethods());
            }
        }
    }

    public Uni<Response> get(String key) {
        return redisAPI.get(key);
    }

    private void setex(String key, String seconds, String value, TipoContrato tipoContrato) {

        if (tipoContrato.sessaoCompartilhada()) {
            key = key + value;
        }

        redisAPI.setex(key, seconds, value).await().indefinitely();
    }

    public jakarta.ws.rs.core.Response del(String userAgent) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Usuario usuario = Contexto.getContextUserAuth(context);

            validaUsuarioSessoa(usuario);

            if (BasicFunctions.isNotEmpty(usuario)) {

                String key = makeSessionKeyPattern(usuario, userAgent);

                Boolean sessao = checkSession(usuario, userAgent);

                if (sessao) {

                    redisAPI.del(Collections.singletonList(key)).await().indefinitely();
                    countActiveSessionsForUserAndOrganization(usuario, true, userAgent);
                    responses.setMessages("Sessão do Usuário " + usuario.getLogin() + " finalizada com sucesso!");
                    responses.setStatus(200);
                    log("Sessão do Usuário " + usuario.getLogin() + " finalizada com sucesso!", getTraceMethods());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return jakarta.ws.rs.core.Response.ok(responses).status(responses.getStatus()).build();
    }

    public void delByKey(String key) {
        redisAPI.del(Collections.singletonList(key)).await().indefinitely();
    }

    public int countActiveSessionsForUserAndOrganization(Usuario usuario, Boolean firstTime, String userAgent) {

        TipoContrato tipoContrato = ContratoLoader.getTipoContratoByUsuarioOrganizacaoDefault(usuario);

        String key = getSessionDefaultPattern(usuario, userAgent, false);

        Uni<Integer> activeSessionsCount = redisAPI.keys(key)
                .onItem().transform(Response::size);

        Integer count = activeSessionsCount.await().indefinitely();

        logSessoesAtivas(tipoContrato, usuario, firstTime, count, userAgent);

        return count;
    }

    public void setex(Usuario usuario, String expiration, String accessToken, TipoContrato tipoContrato,
                      String userAgent) {

        setex(makeSessionKeyPattern(usuario, userAgent), expiration, accessToken, tipoContrato);

        countActiveSessionsForUserAndOrganization(usuario, Boolean.TRUE, userAgent);

    }

    public jakarta.ws.rs.core.Response flushRedis() {

        try {

            Responses responses = new Responses();
            responses.setMessages(new ArrayList<>());

            redisAPI.flushdbAndAwait(Collections.emptyList());
            responses.setMessages("Sessões do Redis finalizadas com sucesso!");
            responses.setStatus(200);
            log("Redis database flushed!", getTraceMethods());
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return jakarta.ws.rs.core.Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaUsuarioSessoa(Usuario usuario) {
        if (BasicFunctions.isEmpty(usuario)) {
            responses.setMessages("O usuário de contexto da sessão não pode ser encontrado!");
            responses.setStatus(404);
        }
    }

    private Boolean checkSession(Usuario usuario, String userAgent) {
        if (countActiveSessionsForUserAndOrganization(usuario, true, userAgent) >= 1) {
            return Boolean.TRUE;
        }
        responses.setMessages("O usuário de contexto não possui sessão ativa.");
        responses.setStatus(404);
        return Boolean.FALSE;
    }
}
