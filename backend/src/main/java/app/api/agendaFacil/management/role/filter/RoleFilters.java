package app.api.agendaFacil.management.role.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.Map;

public class RoleFilters {

    public static QueryFilter makeRoleQueryStringByFilters(Long id, String nome, String privilegio, Boolean admin, Long usuarioId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "role";
        alias = "r";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();


        if (BasicFunctions.isNotNull(nome) && !nome.isBlank()) {
            where.append("AND LOWER(r.nome) LIKE :nome ");
            params.put("nome", "%" + nome.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(privilegio) && !privilegio.isBlank()) {
            where.append("AND LOWER(r.privilegio) LIKE :privilegio ");
            params.put("privilegio", "%" + privilegio.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND r.id = :id ");
            params.put("id", id);
        }

        if (BasicFunctions.isNotNull(admin)) {
            where.append("AND r.admin = :admin ");
            params.put("admin", admin);
        }

        if (BasicFunctions.isNotNull(usuarioId)) {
            where.append("AND r.usuarioId = :usuarioId ");
            params.put("usuarioId", usuarioId);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
