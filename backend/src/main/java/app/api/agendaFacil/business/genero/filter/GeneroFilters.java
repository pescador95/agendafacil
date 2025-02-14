package app.api.agendaFacil.business.genero.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.Map;

public class GeneroFilters {

    public static QueryFilter makeGeneroQueryStringByFilters(Long id, String genero, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "genero";
        alias = "g";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND g.id = :id ");
            params.put("id", id);
        }

        if (BasicFunctions.isNotNull(genero) && !genero.isBlank()) {
            where.append("AND LOWER(g.genero) LIKE :genero ");
            params.put("genero", "%" + genero.toLowerCase() + "%");
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);

    }
}
