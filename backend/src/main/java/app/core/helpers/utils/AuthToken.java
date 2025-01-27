package app.core.helpers.utils;

import app.api.agendaFacil.business.contrato.entity.TipoContrato;
import app.api.agendaFacil.business.contrato.loader.ContratoLoader;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.management.database.redis.RedisService;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequestScoped
public class AuthToken {

    final RedisService redisClient;
    private final Set<String> privilegios = new HashSet<>();
    private final String ORGANIZACAO_CONTEXTO = "organizacaoContexto";
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public AuthToken(RedisService redisClient) {
        this.redisClient = redisClient;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public String generateAccessToken(Usuario pUsuario, String userAgent, String uuid) {


        Instant currentInstant = Instant.now();

        Organizacao organizacao = OrganizacaoRepository.findById(pUsuario.getOrganizacaoDefaultId());

        ZonedDateTime zonedDateTime = currentInstant.atZone(ZoneId.of(organizacao.getZoneId()));


        Instant accessExpiresAt = zonedDateTime.plus(Duration.ofMinutes(10)).toInstant();

        int accessSeconds = (int) Duration.between(Instant.now(), accessExpiresAt).getSeconds();

        if (pUsuario.hasRole()) {
            pUsuario.getPrivilegio().forEach(c -> privilegios.add(c.getPrivilegio()));
        }
        String accessToken = Jwt.issuer(this.getIssuer())
                .upn(pUsuario.getLogin())
                .groups(privilegios)
                .claim("uuid", uuid)
                .claim(ORGANIZACAO_CONTEXTO, pUsuario.getOrganizacaoDefaultId())
                .expiresAt(accessExpiresAt)
                .sign();

        String expiration = Integer.toString(accessSeconds);

        TipoContrato tipoContrato = ContratoLoader.getTipoContratoByUsuarioOrganizacaoDefault(pUsuario);

        if (BasicFunctions.isNotEmpty(tipoContrato)) {
            String existingKey = RedisService.makeSessionKeyPattern(pUsuario, userAgent);
            if (BasicFunctions.isNotEmpty(existingKey)) {
                redisClient.delByKey(existingKey);
            }
            redisClient.setex(pUsuario, expiration, accessToken, tipoContrato, userAgent);
        }

        return accessToken;
    }

    public String getIssuer() {
        return issuer;
    }

    public String generateRefreshToken(Usuario pUsuario, String uuid) {


        Instant currentInstant = Instant.now();

        Organizacao organizacao = OrganizacaoRepository.findById(pUsuario.getOrganizacaoDefaultId());

        ZonedDateTime zonedDateTime = currentInstant.atZone(ZoneId.of(organizacao.getZoneId()));

        Instant refreshExpiresAt = zonedDateTime.plus(Duration.ofHours(12)).toInstant();

        if (pUsuario.hasRole()) {
            pUsuario.getPrivilegio().forEach(c -> privilegios.add(c.getPrivilegio()));
        }

        return Jwt.issuer(this.getIssuer())
                .upn(pUsuario.getLogin())
                .groups(privilegios)
                .claim("uuid", uuid)
                .claim(ORGANIZACAO_CONTEXTO, pUsuario.getOrganizacaoDefaultId())
                .expiresAt(refreshExpiresAt)
                .sign();
    }
}
