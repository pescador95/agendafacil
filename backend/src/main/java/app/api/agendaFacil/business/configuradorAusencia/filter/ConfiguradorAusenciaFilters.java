package app.api.agendaFacil.business.configuradorAusencia.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ConfiguradorAusenciaFilters {

    public static QueryFilter makeConfiguradorAusenciaQueryStringByFilters(String nomeAusencia, LocalDate dataInicio, LocalDate dataFim, LocalTime horaInicio, LocalTime horaFim, Long usuarioId, String observacao, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "configuradorausencia";
        alias = "c";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);
        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(nomeAusencia) && !nomeAusencia.isBlank()) {
            where.append("AND LOWER(c.nomeAusencia) LIKE :nomeAusencia ");
            params.put("nomeAusencia", "%" + nomeAusencia.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(dataInicio)) {
            where.append("AND c.dataInicioAusencia = :dataInicio ");
            params.put("dataInicio", dataInicio);
        }

        if (BasicFunctions.isNotNull(dataFim)) {
            where.append("AND c.dataFimAusencia = :dataFim ");
            params.put("dataFim", dataFim);
        }

        if (BasicFunctions.isNotNull(horaInicio)) {
            where.append("AND c.horaInicioAusencia = :horaInicio ");
            params.put("horaInicio", horaInicio);
        }

        if (BasicFunctions.isNotNull(horaFim)) {
            where.append("AND c.horaFimAusencia = :horaFim ");
            params.put("horaFim", horaFim);
        }

        if (BasicFunctions.isNotNull(observacao) && !observacao.isBlank()) {
            where.append("AND LOWER(c.observacao) LIKE :observacao ");
            params.put("observacao", "%" + observacao.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(usuarioId)) {
            join.append("JOIN configuradorausenciausuario cau on cau.configuradorAusenciaId = c.id ");
            where.append("AND cau.usuarioId = :usuarioId ");
            params.put("usuarioId", usuarioId);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
