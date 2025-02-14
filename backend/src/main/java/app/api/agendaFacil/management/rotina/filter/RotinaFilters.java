package app.api.agendaFacil.management.rotina.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.Map;

public class RotinaFilters {

    public static QueryFilter makeRotinaQueryStringByFilters(Long id, String nome, String icon, String path, String titulo, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "rotina";
        alias = "c";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();


        if (BasicFunctions.isNotNull(nome) && !nome.isBlank()) {
            where.append("AND LOWER(c.nome) LIKE :nome ");
            params.put("nome", "%" + nome.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(icon) && !icon.isBlank()) {
            where.append("AND LOWER(c.icon) LIKE :icon ");
            params.put("icon", "%" + icon.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(path) && !path.isBlank()) {
            where.append("AND LOWER(c.path) LIKE :path ");
            params.put("path", "%" + path.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(titulo) && !titulo.isBlank()) {
            where.append("AND LOWER(c.titulo) LIKE :titulo ");
            params.put("titulo", "%" + titulo.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND c.id = :id ");
            params.put("id", id);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
