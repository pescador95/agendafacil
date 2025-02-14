package app.api.agendaFacil.business.configuradorAgendamentoEspecial.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ConfiguradorAgendamentoEspecialFilters {

    public static QueryFilter makeConfiguradorAgendamentoEspecialQueryStringByFilters(String nome, Long profissionalId, Long tipoAgendamentoId, LocalDate dataInicio, LocalDate dataFim, Long organizacaoId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "configuradoragendamentoespecial";
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
            where.append("AND c.organizacaoId = :organizacaoId ");
            params.put("organizacaoId", organizacaoId);
        }

        if (BasicFunctions.isNotNull(profissionalId)) {
            where.append("AND c.profissionalId = :profissionalId ");
            params.put("profissionalId", profissionalId);
        }

        if (BasicFunctions.isNotNull(tipoAgendamentoId)) {
            join.append("JOIN configuradorespecialtipoagendamento ce ON ce.configAgendamentoEspecialId = c.id ");
            where.append("AND ce.tipoAgendamentoId = :tipoAgendamentoId ");
            params.put("tipoAgendamentoId", tipoAgendamentoId);
        }

        if (BasicFunctions.isNotNull(nome) && !nome.isBlank()) {
            where.append("AND LOWER(c.nome) LIKE :nome ");
            params.put("nome", "%" + nome.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(dataInicio)) {
            where.append("AND c.dataInicio = :dataInicio ");
            params.put("dataInicio", dataInicio);
        }

        if (BasicFunctions.isNotNull(dataFim)) {
            where.append("AND c.dataFim = :dataFim ");
            params.put("dataFim", dataFim);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
