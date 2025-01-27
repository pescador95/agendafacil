package app.api.agendaFacil.business.perfilAcesso.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilAcessoFilters {

    public static QueryFilter makePerfilAcessoQueryStringByFilters(Long id, String nome, Boolean criar, Boolean ler, Boolean atualizar, Boolean apagar, Long usuarioId, List<Long> rotinaId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "perfilacesso";
        alias = "p";

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

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND c.id = :id ");
            params.put("id", id);
        }

        if (BasicFunctions.isNotNull(criar)) {
            where.append("AND c.criar = :criar ");
            params.put("criar", criar);
        }

        if (BasicFunctions.isNotNull(ler)) {
            where.append("AND c.ler = :ler ");
            params.put("ler", ler);
        }

        if (BasicFunctions.isNotNull(atualizar)) {
            where.append("AND c.atualizar = :atualizar ");
            params.put("atualizar", atualizar);
        }

        if (BasicFunctions.isNotNull(apagar)) {
            where.append("AND c.apagar = :apagar ");
            params.put("apagar", apagar);
        }

        if (BasicFunctions.isNotNull(usuarioId)) {
            where.append("AND c.usuarioId = :usuarioId ");
            params.put("usuarioId", usuarioId);
        }

        if (BasicFunctions.isNotEmpty(rotinaId)) {
            join.append("JOIN rotinaperfilacesso rpa on rpa.perfilAcessoId = p.id ");
            where.append("AND rpa.rotinaId in (:rotinaId) ");
            params.put("rotinaId", rotinaId);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);

    }
}
