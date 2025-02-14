package app.core.helpers.utils;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.atendimento.entity.Atendimento;
import app.api.agendaFacil.business.configuradorAusencia.entity.ConfiguradorAusencia;
import app.api.agendaFacil.business.configuradorFeriado.entity.ConfiguradorFeriado;
import app.api.agendaFacil.business.contrato.entity.Contrato;
import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bytebuddy.utility.RandomString;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.MaskFormatter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class StringFunctions {

    public static String createToken(Integer length) {
        return RandomString.make(length);
    }

    public static String makeMaskCnpjFormatter(String pCnpj) throws ParseException {
        MaskFormatter mask;
        mask = new MaskFormatter("###.###.###/####-##");
        if (BasicFunctions.isNotEmpty(pCnpj)) {
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(pCnpj);
        }
        return null;
    }

    public static String removeChars(String pText) {
        if (BasicFunctions.isNotEmpty(pText)) {
            String text = pText.replaceAll("^\\[", "");
            return text.replaceAll("]", "");
        }
        return null;
    }

    public static List<String> removeCharsList(List<String> pText) {
        List<String> returnTexts = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(pText)) {

            pText.forEach(text -> {
                String textReturn;
                textReturn = text.replaceAll("]", "");
                returnTexts.add(textReturn);
            });
            return returnTexts;
        }
        return null;
    }

    public static String makeOnlyNumbers(String text) {
        if (BasicFunctions.isNotEmpty(text)) {
            return text.replaceAll("[^0-9]+", "");
        }
        return null;
    }

    public static String makeEnderecoString(Endereco endereco) {

        if (BasicFunctions.isNotEmpty(endereco)) {
            StringBuilder enderecoFormatado = new StringBuilder();

            if (BasicFunctions.isNotEmpty(endereco.getLogradouro())) {
                enderecoFormatado.append(endereco.getLogradouro()).append(", ");
            }

            if (BasicFunctions.isNotEmpty(endereco.getNumero())) {
                enderecoFormatado.append(endereco.getNumero()).append(". ");
            }

            if (BasicFunctions.isNotEmpty(endereco.getCidade())) {
                enderecoFormatado.append(endereco.getCidade()).append(" - ");
            }

            if (BasicFunctions.isNotEmpty(endereco.getEstado())) {
                enderecoFormatado.append(endereco.getEstado());
            }

            return enderecoFormatado.toString();

        }
        return "Endereço não disponível";
    }

    public static String makeMaskCelularFormatter(String celular) throws ParseException {

        try {

            MaskFormatter mask;
            mask = new MaskFormatter("(##) # ####-####");
            mask.setValueContainsLiteralCharacters(false);

            if (BasicFunctions.isNotEmpty(celular)) {
                return mask.valueToString(celular);
            }
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> String makeQueryString(T parameterValue, String parameterName, Class<?> entity) {

        String queryString = "";

        if (parameterValue instanceof String) {
            return queryString + (" AND LOWER(" + parameterName + ") LIKE '%" + parameterValue.toString().toLowerCase()
                    + "%'");
        }
        if (parameterValue instanceof Integer || parameterValue instanceof Long || parameterValue instanceof Boolean
                || parameterValue instanceof Double || parameterValue instanceof Float
                || parameterValue instanceof BigDecimal || parameterValue instanceof LocalDateTime) {
            return queryString + (" AND " + parameterName + " = " + parameterValue);
        }
        if (parameterValue instanceof LocalDate) {
            return queryString + makeEntityAtributeLocalDate(parameterValue, parameterName, entity);
        }
        if (parameterValue instanceof LocalTime) {
            return queryString + makeEntityAtributeLocalTime(parameterValue, parameterName, entity);
        }
        if (parameterValue instanceof List<?> listValues) {
            StringJoiner joiner = new StringJoiner(",", "", "");
            for (Object listValue : listValues) {
                String string = listValue.toString();
                joiner.add(string);
            }
            String paramList = joiner.toString();
            return queryString + " AND " + parameterName + " IN (" + paramList + ")";
        }
        return queryString;
    }

    public static String makeEntityAtributeLocalDate(Object parameterValue, String parameterName, Class<?> entity) {

        String attributeName = "", operator = "=";

        if (parameterName.equals("dataInicio")) {
            operator = ">=";
        }
        if (parameterName.equals("dataFim")) {
            operator = "<=";
        }

        if (entity.equals(Agendamento.class)) {
            attributeName = "dataAgendamento";
        }
        if (entity.equals(Contrato.class)) {
            attributeName = "dataContrato";
        }
        if (entity.equals(Atendimento.class)) {
            attributeName = "dataAtendimento";
        }
        if (entity.equals(ConfiguradorAusencia.class)) {
            attributeName = "dataInicioAusencia";
        }
        if (entity.equals(ConfiguradorFeriado.class)) {
            attributeName = "dataFeriado";
        }
        if (entity.equals(Entidade.class)) {
            attributeName = "dataNascimento";
        }

        return " AND " + attributeName + " " + operator + " " + "'" + parameterValue + "'";
    }

    public static String makeEntityAtributeLocalTime(Object parameterValue, String parameterName, Class<?> entity) {

        String attributeName = "", operator = "=";

        if (parameterName.equals("horaInicio")) {
            operator = ">=";
        }
        if (parameterName.equals("horaFim")) {
            operator = "<=";
        }

        if (entity.equals(ConfiguradorAusencia.class)) {
            attributeName = "horaInicioAusencia";
            if (parameterName.equals("horaFim")) {
                attributeName = "horaFimAusencia";
            }
        }
        if (entity.equals(ConfiguradorFeriado.class)) {
            attributeName = "horaInicioFeriado";
            if (parameterName.equals("horaFim")) {
                attributeName = "horaFimFeriado";
            }
        }
        if (entity.equals(Agendamento.class)) {
            attributeName = "horarioAgendamento";
        }
        return " AND " + attributeName + " " + operator + " " + "'" + parameterValue + "'";
    }

    public static String dataFormatter(LocalDate pData) {
        if (BasicFunctions.isNotEmpty(pData)) {
            return pData.getDayOfMonth() + "/" + pData.getMonthValue() + "/" + pData.getYear();
        }
        return null;
    }

    public static Map<String, String> notificacaoBuilder(Agendamento agendamento) {

        Map<String, String> substituicoes = new HashMap<>();

        try {

            if (BasicFunctions.isNotEmpty(agendamento)) {
                substituicoes.put("CLIENTE", agendamento.getPessoaAgendamento().getNome());
                substituicoes.put("DIA", nomeDia(agendamento));
                substituicoes.put("SEMANA", nomeSemana(agendamento.getDataAgendamento()));
                substituicoes.put("DATA", dataFormatter(agendamento.getDataAgendamento()));
                substituicoes.put("HORARIO", agendamento.getHorarioAgendamento().toString());
                substituicoes.put("EMPRESA", agendamento.getOrganizacaoAgendamento().getNome());
                substituicoes.put("ENDERECO",
                        makeEnderecoString(agendamento.getOrganizacaoAgendamento().getEndereco()));
                substituicoes.put("PROFISSIONAL", agendamento.getProfissionalAgendamento().nomeProfissional());
                substituicoes.put("TIPOAGENDAMENTO", agendamento.getTipoAgendamento().getTipoAgendamento());
                substituicoes.put("CONTATO",
                        makeMaskCelularFormatter(agendamento.getOrganizacaoAgendamento().getCelular()));
            }

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return substituicoes;
    }

    public static String makeMensagemByNotificacaoTemplate(String mensagem, Map<String, String> substituicoes) {
        if (BasicFunctions.isNotEmpty(mensagem, substituicoes)) {
            for (Map.Entry<String, String> entrada : substituicoes.entrySet()) {
                if (BasicFunctions.isNotEmpty(entrada)) {
                    mensagem = mensagem.replace(entrada.getKey(), entrada.getValue());
                }

            }
            return mensagem;
        }
        return null;
    }

    public static String nomeDia(Agendamento agendamento) {
        return agendamento.getDataAgendamento()
                .isEqual(Contexto.dataContexto(agendamento.getOrganizacaoAgendamento().getZoneId())) ? "hoje"
                : dataFormatter(agendamento.getDataAgendamento());
    }

    public static String nomeSemana(LocalDate dataAgendamento) {
        DayOfWeek dayOfWeek = dataAgendamento.getDayOfWeek();
        Map<DayOfWeek, String> DIAS_DA_SEMANA;

        DIAS_DA_SEMANA = new EnumMap<>(DayOfWeek.class);
        DIAS_DA_SEMANA.put(DayOfWeek.SATURDAY, "Sábado");
        DIAS_DA_SEMANA.put(DayOfWeek.SUNDAY, "Domingo");
        DIAS_DA_SEMANA.put(DayOfWeek.MONDAY, "Segunda-feira");
        DIAS_DA_SEMANA.put(DayOfWeek.TUESDAY, "Terça-feira");
        DIAS_DA_SEMANA.put(DayOfWeek.WEDNESDAY, "Quarta-feira");
        DIAS_DA_SEMANA.put(DayOfWeek.THURSDAY, "Quinta-feira");
        DIAS_DA_SEMANA.put(DayOfWeek.FRIDAY, "Sexta-feira");

        return DIAS_DA_SEMANA.getOrDefault(dayOfWeek, "");
    }

    public static String extractNumericDigits(String str) {

        if (BasicFunctions.isNotEmpty(str)) {
            return str.replaceAll("\\D+", "");
        }
        return null;
    }

    public static String getClassName(Object clazz) {

        if (BasicFunctions.isNotEmpty(clazz)) {
            if (clazz instanceof ArrayList<?>) {
                return ((ArrayList) clazz).get(0).getClass().getSimpleName();
            }
            return clazz.getClass().getSimpleName();
        }
        return "";
    }

    public static List<String> getMimeTypes() {
        return Arrays.asList("image/jpg", "image/jpeg", "application/msword",
                "application/vnd.ms-excel", "application/xml",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "image/gif",
                "image/png", "text/plain", "application/vnd.ms-powerpoint", "application/pdf", "text/csv",
                "document/doc", "document/docx",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/zip",
                "application/vnd.sealed.xls");
    }

    public static String mapToJson(Map<String, ?> attributes) {
        StringBuilder jsonBuilder = new StringBuilder("{");

        boolean firstEntry = true;

        for (Map.Entry<String, ?> entry : attributes.entrySet()) {
            if (!firstEntry) {
                jsonBuilder.append(", ");
            }
            firstEntry = false;

            appendKeyValue(jsonBuilder, entry.getKey(), entry.getValue());
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private static void appendKeyValue(StringBuilder jsonBuilder, String key, Object value) {
        jsonBuilder.append("\"").append(key).append("\":");
        jsonBuilder.append(formatValue(value));
    }

    private static String formatValue(Object value) {
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        return "\"" + value.toString() + "\"";
    }

    public static String objectToJson(Object obj) {

        String jsonPayload = "";

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            jsonPayload = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jsonPayload;
        }
    }

    public static <T> String listObjectToJson(T... objs) {

        String jsonPayload = "";

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            return jsonPayload = mapper.writeValueAsString(objs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jsonPayload;
        }
    }

    public static String attributeToJson(String name, String value) {
        return "{\"" + name + "\":\"" + value + "\"}";
    }

    public static String dateFormat(@NotNull LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static StringBuilder count(String table, String alias, String target) {
        String select = "SELECT COUNT(alias.target) FROM table alias "
                .replace("table", table)
                .replace("target", target)
                .replace("alias", alias);
        return new StringBuilder(select);
    }

    public static StringBuilder select(String table, String alias) {
        String select = "SELECT alias.* FROM table alias "
                .replace("table", table)
                .replace("alias", alias);
        return new StringBuilder(select);
    }

    public static StringBuilder select(String table, String alias, String field) {
        String select = "SELECT alias.field FROM table alias "
                .replace("table", table)
                .replace("alias", alias)
                .replace("field", field);
        return new StringBuilder(select);
    }

    public static StringBuilder join() {
        return new StringBuilder();
    }

    public static StringBuilder where() {
        return new StringBuilder("WHERE 1 = 1 ");
    }

    public static StringBuilder where(String alias, Boolean ativo) {
        String where = "WHERE alias.ativo = value "
                .replace("alias", alias)
                .replace("value", ativo.toString());
        return new StringBuilder(where);
    }

    public static StringBuilder groupBy(String alias, String value) {
        String groupBy = "group by alias.groupBy "
                .replace("alias", alias)
                .replace("groupBy", value);
        return new StringBuilder(groupBy).append(" ");
    }

    public static StringBuilder orderBy(String alias, String strgOrder, String sortQuery) {
        String groupBy = "order by alias.groupBy sortQuery "
                .replace("alias", alias)
                .replace("groupBy", strgOrder)
                .replace("sortQuery", sortQuery);
        return new StringBuilder(groupBy).append(" ");
    }

    public static StringBuilder paginate(Integer pageSize, Integer pageIndex) {
        String limit, offset;
        limit = String.valueOf(pageSize);
        offset = String.valueOf((Math.max(1, pageIndex) - 1) * pageSize);
        return new StringBuilder("limit ").append(limit).append(" OFFSET ").append(offset);
    }

    public String makeMaskCpfFormatter(String pCpf) throws ParseException {

        MaskFormatter mask;
        mask = new MaskFormatter("###.###.###-##");
        if (BasicFunctions.isNotEmpty(pCpf)) {
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(pCpf);
        }
        return null;
    }

    public String makeMaskRgFormatter(String pRg) throws ParseException {

        MaskFormatter mask;
        mask = new MaskFormatter("##.###.###-#");
        if (BasicFunctions.isNotEmpty(pRg)) {
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(pRg);
        }
        return null;
    }
}