package app.api.agendaFacil.business.contrato.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ContratoFilters {

    public static QueryFilter makeContratoQueryStringByFilters(String tenant, Long tipoContrato, Integer numeroMaximoSessoes, String consideracoes, LocalDate dataContrato, LocalDate dataTermino, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "config.contrato";
        alias = "c";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(tenant) && !tenant.isBlank()) {
            where.append("AND LOWER(c.tenant) LIKE :tenant ");
            params.put("tenant", tenant.toLowerCase());
        }

        if (BasicFunctions.isNotNull(consideracoes) && !consideracoes.isBlank()) {
            where.append("AND LOWER(c.consideracoes) LIKE :consideracoes ");
            params.put("consideracoes", consideracoes.toLowerCase());
        }

        if (BasicFunctions.isNotNull(tipoContrato)) {
            where.append("AND c.tipoContratoId = :tipoContrato ");
            params.put("tipoContrato", tipoContrato);
        }


        if (BasicFunctions.isNotNull(numeroMaximoSessoes)) {
            where.append("AND c.numeroMaximoSessoes = :numeroMaximoSessoes ");
            params.put("numeroMaximoSessoes", numeroMaximoSessoes);
        }

        if (BasicFunctions.isNotNull(dataContrato)) {
            where.append("AND c.dataContrato = :dataContrato ");
            params.put("dataContrato", dataContrato);
        }

        if (BasicFunctions.isNotNull(dataTermino)) {
            where.append("AND c.dataTerminoContrato = :dataTermino ");
            params.put("dataTermino", dataTermino);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
