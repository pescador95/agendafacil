package app.api.agendaFacil.business.statusAgendamento.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.Map;

public class StatusAgendamentoFilters {

    public static QueryFilter makeStatusAgendamentoQueryStringByFilters(Long id, String status, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "statusagendamento";
        alias = "s";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(id)) {
            join.append("JOIN statusagendamento s on s.id = a.statusAgendamentoId ");
            where.append("AND s.id = :statusId ");
            params.put("statusId", id);
        }

        if (BasicFunctions.isNotNull(status)) {
            where.append("AND s.status = :status ");
            params.put("status", status);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}

