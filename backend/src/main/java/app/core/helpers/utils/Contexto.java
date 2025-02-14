package app.core.helpers.utils;

import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.repository.UsuarioRepository;
import app.core.application.config.ApplicationConfig;
import app.core.application.config.ApplicationConfigRepository;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Contexto {

    public static LocalDate proximaDataByIntervalo(Integer intervaloSemana, DayOfWeek diaSemana) {
        return LocalDate.now().plusWeeks(intervaloSemana).with(diaSemana);
    }

    public static LocalDate dataContexto(String timeZone) {
        return LocalDate.now(ZoneId.of(timeZone));
    }

    public static LocalDate dataContexto(Organizacao organizacao) {
        organizacao = OrganizacaoRepository.findById(organizacao.getId());
        return LocalDate.now(ZoneId.of(organizacao.getZoneId()));
    }

    public static LocalDate dataContexto(OrganizacaoDTO organizacaoDTO) {
        Organizacao organizacao = OrganizacaoRepository.findById(organizacaoDTO.getId());
        return LocalDate.now(ZoneId.of(organizacao.getZoneId()));
    }

    public static LocalTime horarioContexto(String timeZone) {
        return LocalTime.now(ZoneId.of(timeZone));
    }

    public static LocalTime horarioContexto(Organizacao organizacao) {
        organizacao = OrganizacaoRepository.findById(organizacao.getId());
        return LocalTime.now(ZoneId.of(organizacao.getZoneId()));
    }

    public static LocalTime horarioContexto(OrganizacaoDTO organizacaoDTO) {
        Organizacao organizacao = OrganizacaoRepository.findById(organizacaoDTO.getId());
        return LocalTime.now(ZoneId.of(organizacao.getZoneId()));
    }

    public static ZoneId defaultZoneId() {
        return ZoneId.of("America/Sao_Paulo");
    }

    public static String defaultTimeZoneOffset() {
        return "GMT-03:00";
    }

    public static String defaultZoneIdToString() {
        return defaultZoneId().toString();
    }

    public static LocalDate dataContexto() {
        return LocalDate.now(defaultZoneId());
    }

    public static LocalDateTime dataHoraContexto(Long organizacaoId) {
        Organizacao organizacao = OrganizacaoRepository.findById(organizacaoId);
        return LocalDateTime.now(ZoneId.of(organizacao.getZoneId()));
    }

    public static LocalDateTime dataHoraContexto(String timeZone) {
        return LocalDateTime.now(ZoneId.of(timeZone));
    }

    public static LocalDateTime dataHoraContexto() {
        return LocalDateTime.now(defaultZoneId());
    }

    public static LocalDateTime dataHoraContexto(@Context @NotNull SecurityContext context) {
        return dataHoraContexto(getContextUser(context).getOrganizacaoDefaultId());
    }

    public static String dataHoraContextoToString(Organizacao organizacao) {
        organizacao = OrganizacaoRepository.findById(organizacao.getId());
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(organizacao.getZoneId()));
        return zonedDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS"));
    }

    public static String dataHoraContextoToString() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(defaultZoneId().toString()));
        return zonedDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS"));
    }

    public static String dataHoraToString(LocalDateTime dataHora, Organizacao organizacao) {
        organizacao = OrganizacaoRepository.findById(organizacao.getId());
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dataHora, ZoneId.of(organizacao.getZoneId()));
        return zonedDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS"));
    }

    public static Boolean dataValida(LocalDate data, Organizacao organizacao) {
        return BasicFunctions.isNotNull(data) && (data.isAfter(LocalDate.of(1899, 12, 31))
                && data.isBefore(LocalDate.now(ZoneId.of(organizacao.getZoneId())))
                || data.isEqual(LocalDate.now(ZoneId.of(organizacao.getZoneId()))));
    }

    public static Usuario getContextUser(@Context @NotNull SecurityContext context) {

        if (BasicFunctions.isNotEmpty(context, context.getUserPrincipal())) {

            String login = context.getUserPrincipal().getName();
            return UsuarioRepository.findByLogin(login);
        }
        return UsuarioRepository.findBot();
    }

    public static Usuario getContextUserAuth(@Context @NotNull SecurityContext context) {

        if (BasicFunctions.isNotEmpty(context, context.getUserPrincipal())) {

            String login = context.getUserPrincipal().getName();
            return UsuarioRepository.findByLogin(login);
        }
        return null;
    }

    public static Long getContextUserId(@Context @NotNull SecurityContext context) {
        return getContextUser(context).getId();
    }

    public static Boolean isUserAdmin(Usuario pUsuario) {
        Usuario usuario = UsuarioRepository.findByLogin(pUsuario.getLogin());

        if (usuario.hasRole()) {
            return usuario.admin();
        }
        return false;
    }

    public static ApplicationConfig getApplicationConfig() {
        try {
            return ApplicationConfigRepository.findByProfile();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getProfile() {
        return ConfigProvider.getConfig().getValue("quarkus.profile", String.class);
    }

    public static Boolean scheduleEnabled() {
        ApplicationConfig config = getApplicationConfig();
        return BasicFunctions.isNotEmpty(config) && config.getScheduleEnabled();
    }

    public static String getAgendaFacilUrl() {

        try {
            ApplicationConfig config = getApplicationConfig();
            return config.getAgendaFacilUrl();
        } catch (Exception e) {
            e.getStackTrace();
            throw new IllegalStateException("A variável de ambiente AGENDAFACIL_URL não está definida.");
        }
    }

    public static String getAgendaFacilUrl(String tenant) {

        try {

            String url = getAgendaFacilUrl();

            boolean isHttps = url.startsWith("https");

            String urlWithoutScheme = url.substring(url.indexOf("://") + 3);

            String newUrl = (isHttps ? "https" : "http") + "://" + tenant + "." + urlWithoutScheme;

            return newUrl;
        } catch (Exception e) {
            e.getStackTrace();
            throw new IllegalStateException("A variável de ambiente AGENDAFACIL_URL não está definida.");
        }
    }

    public static String getTelegramBaseUrl() {
        ApplicationConfig config = getApplicationConfig();
        return config.getTelegramBaseUrl();
    }

    public static String getWhatsappBaseUrl() {
        ApplicationConfig config = getApplicationConfig();
        return config.getWhatsappBaseUrl();
    }

    public static String getQuarkusBaseUrl() {
        ApplicationConfig config = getApplicationConfig();
        return config.getQuarkusBaseUrl();
    }

    public static Boolean traceMethods() {
        ApplicationConfig config = getApplicationConfig();
        return BasicFunctions.isNotEmpty(config) && config.getTraceMethods();
    }
}
