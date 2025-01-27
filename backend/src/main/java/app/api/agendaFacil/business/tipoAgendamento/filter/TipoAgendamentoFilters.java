package app.api.agendaFacil.business.tipoAgendamento.filter;

import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;
import io.quarkus.panache.common.Parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoAgendamentoFilters {


    public static QueryFilter makeTipoAgendamentoQueryStringByFilters(Long id, String tipoAgendamento, List<Long> organizacaoId, String sortQuery, String strgOrder, Integer pageIndex, Integer pageSize) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "tipoagendamento";
        alias = "t";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND t.id = :id ");
            params.put("id", id);
        }

        if (BasicFunctions.isNotNull(tipoAgendamento)) {
            where.append("AND LOWER(t.tipoAgendamento) LIKE :tipoAgendamento ");
            params.put("tipoAgendamento", "%" + tipoAgendamento.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotEmpty(organizacaoId)) {
            join.append("JOIN tipoagendamentoorganizacoes tao ON tao.tipoagendamentoid = t.id ");
            where.append("AND tao.organizacaoid in (:organizacaoId) ");
            params.put("organizacaoId", organizacaoId);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }


    public static List<TipoAgendamento> findByOrganizacaoIdAndQueryString(List<Long> organizacaoId, String queryString) {
        String fullQuery = "organizacaoId in :orgId";
        fullQuery += " " + queryString;
        return TipoAgendamento.find(fullQuery, Parameters.with("orgId", organizacaoId)).list();
    }
}
