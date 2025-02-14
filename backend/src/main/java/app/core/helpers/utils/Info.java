package app.core.helpers.utils;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

import static app.core.helpers.utils.BasicFunctions.log;

public class Info {

    public static void logProjectInfo(Boolean show) {

        try {

            log(projectInfoAppend(show));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String mensagemContrato(ContratoDTO contrato) {
        if (BasicFunctions.isNotEmpty(contrato, contrato.getTenant())) {
            return "Migrations criadas com sucesso para o contrato " + contrato.getTenant();
        }
        return "Migrations criadas com sucesso";
    }

    public static String projectInfoAppend(Boolean show) throws IOException {

        return insertProjectInfo() +
                envsInfo(show) +
                insertMemoryInfo() +
                quarkusVersion() +
                quarkusProfile() +
                startedIn() +
                agendaFacilLogo() +
                lineFeed() +
                lineFeed();
    }

    public static String projectInfo(Boolean show) {

        try {

            Writer writer = new StringWriter();
            writer.write(projectInfoAppend(show));
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String line() {
        return "================================================================================";
    }

    public static String lineFeed() {
        return line() + "\n";
    }

    public static String insertHeaderInfo(String header) {

        StringBuilder stringBuilder = new StringBuilder();

        int halfLength = line().length() / 2;
        int halfHeader = (header.length() / 2) + 1;
        String halfLine = line().substring(0, halfLength - halfHeader);

        stringBuilder.append(halfLine);
        stringBuilder.append(" ").append(header).append(" ");
        stringBuilder.append(halfLine);

        return stringBuilder.toString();
    }

    public static String insertProjectInfo() {
        return insertHeaderInfo(projectName()) + "\n";
    }

    public static String insertMemoryInfo() {
        return insertHeaderInfo("Memory Inf") +
                freeMemoryInfo() +
                allocatedMemory() +
                maxMemory() +
                totalFreeMemory()
                + "\n";
    }

    public static String projectName() {

        return "Agenda Fácil - Quarkus";
    }

    public static String quarkusVersion() {
        return lineFeed() + "Using Quarkus Version: 3.18.1" + "\n";
    }

    public static String quarkusProfile() {
        return lineFeed() + "Environment profile: " + Contexto.getProfile() + "\n";
    }

    public static String startedIn() {
        return "Started in : " + Contexto.dataHoraContexto().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                + "\n" + lineFeed();
    }

    public static String databaseInfo() {
        return "Conectado no Banco de Dados: " + databaseBaseURL();
    }

    public static String schedulerInfo() {
        return "Filas e Schedulers de Lembrete de Agendamentos: " + booleanToText(Contexto.scheduleEnabled());
    }

    public static String templateDirPath() {
        return "src/main/resources/db/template";
    }

    public static String migrationDirPath(String tenant) {
        return "src/main/resources/db/migration/tenant/" + tenant;
    }

    public static String liquibaseChangelogPath() {
        return "db/liquibase-changelog.yaml";
    }

    public static String defaultSchemaName() {
        return "config";
    }

    public static String migrationAuthor() {
        return "agenda.facil";
    }

    public static String telegramApiUrlInfo() {
        return "Telegram API URL: " + telegramBaseURL();
    }

    public static String whatsappApiUrlInfo() {
        return "Whatsapp API URL: " + whatsappBaseURL();
    }

    public static String booleanToText(Boolean ativado) {
        if (ativado) {
            return "Ativado";
        }
        return "Desativado";
    }

    public static Boolean traceMethods() {
        return Contexto.traceMethods();
    }

    public static String traceMethodsInfo() {
        return "Rastreabilidade de Métodos: " + booleanToText(traceMethods());
    }

    public static String databaseBaseURL() {

        switch (Contexto.getProfile()) {

            case "dev":
                return System.getenv("DATABASE_RELEASE");
            case "prod":
                return System.getenv("DATABASE_URL");
            case "test":
                return System.getenv("DATABASE_TEST");

        }

        return System.getenv("DATABASE_URL");
    }

    public static String databaseUser() {
        return System.getenv("DATABASE_USER");
    }

    public static String databasePassword() {
        return System.getenv("DATABASE_PASSWORD");
    }

    public static String telegramBaseURL() {
        return Contexto.getTelegramBaseUrl();
    }

    public static String whatsappBaseURL() {
        return Contexto.getWhatsappBaseUrl();
    }

    public static String quarkusBaseURL() {
        return Contexto.getQuarkusBaseUrl();
    }

    public static String freeMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";

        return "\n" +
                "Free memory: " + format.format(freeMemory / mb) + mega;
    }

    public static String allocatedMemory() {
        Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long allocatedMemory = runtime.totalMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";
        String result = format.format(allocatedMemory / mb) + mega;
        return "\n" +
                "Allocated memory: " + result;
    }

    public static String maxMemory() {
        Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long maxMemory = runtime.maxMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";
        String result = format.format(maxMemory / mb) + mega;

        return "\n" +
                "Max memory: " + result;
    }

    public static String totalFreeMemory() {
        Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long maxMemory = runtime.maxMemory();
        final long allocatedMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";
        String result = format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega;

        return "\n" +
                "Total free memory: " + result;
    }

    public static String envsInfo(Boolean show) {

        if (show) {
            return databaseInfo() +
                    "\n" +
                    schedulerInfo() +
                    "\n" +
                    traceMethodsInfo() +
                    "\n" +
                    telegramApiUrlInfo() +
                    "\n" +
                    whatsappApiUrlInfo()
                    + "\n";
        }
        return "";
    }

    public static String agendaFacilLogo() {
        return """
                   _                        _           ___          _ _\s
                  /_\\   __ _  ___ _ __   __| | __ _    / __\\_ _  ___(_) |
                 //_\\\\ / _` |/ _ \\ '_ \\ / _` |/ _` |  / _\\/ _` |/ __| | |
                /  _  \\ (_| |  __/ | | | (_| | (_| | / / | (_| | (__| | |
                \\_/ \\_/\\__, |\\___|_| |_|\\__,_|\\__,_| \\/   \\__,_|\\___|_|_|
                       |___/                                            \s""" + "\n";
    }
}
