package app.api.agendaFacil.business.configuradorFeriado.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ConfiguradorFeriadoFilters {

    public static QueryFilter makeConfiguradorFeriadoQueryStringByFilters(String nomeFeriado, LocalDate dataFeriado, LocalDate dataInicio, LocalDate dataFim, LocalTime horaInicio, LocalTime horaFim, Long organizacaoId, String observacao, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "configuradorferiado";
        alias = "c";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(organizacaoId)) {
            join.append("JOIN configuradorferiadoorganizacao co on co.configuradorferiadoid = c.id ");
            where.append("AND co.organizacaoId = :organizacaoId ");
            params.put("organizacaoId", organizacaoId);
        }

        if (BasicFunctions.isNotNull(nomeFeriado) && !nomeFeriado.isBlank()) {
            where.append("AND LOWER(c.nomeFeriado) LIKE :nomeFeriado ");
            params.put("nomeFeriado", "%" + nomeFeriado.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(dataFeriado)) {
            where.append("AND c.dataFeriado = :dataFeriado ");
            params.put("dataFeriado", dataFeriado);
        }

        if (BasicFunctions.isNotNull(dataInicio)) {
            where.append("AND c.dataFeriado <= :dataInicio ");
            params.put("dataInicio", dataInicio);
        }

        if (BasicFunctions.isNotNull(dataFim)) {
            where.append("AND c.dataFeriado >= :dataFim ");
            params.put("dataFim", dataFim);
        }

        if (BasicFunctions.isNotNull(horaInicio)) {
            where.append("AND c.horaInicioFeriado = :horaInicio ");
            params.put("horaInicio", horaInicio);
        }

        if (BasicFunctions.isNotNull(horaFim)) {
            where.append("AND c.horaFimFeriado = :horaFim ");
            params.put("horaFim", horaFim);
        }

        if (BasicFunctions.isNotNull(observacao) && !observacao.isBlank()) {
            where.append("AND LOWER(c.observacao) LIKE :observacao ");
            params.put("observacao", "%" + observacao.toLowerCase() + "%");
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
