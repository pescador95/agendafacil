package app.api.agendaFacil.business.endereco.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.Map;

public class EnderecoFilters {

    public static QueryFilter makeEnderecoQueryStringByFilters(String cep, String logradouro, Long numero, String complemento, String cidade, String estado, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "endereco";
        alias = "e";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where(alias, ativo);
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(cep) && !cep.isBlank()) {
            where.append("AND LOWER(e.cep) LIKE :cep ");
            params.put("cep", "%" + cep.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(logradouro) && !logradouro.isBlank()) {
            where.append("AND LOWER(e.logradouro) LIKE :logradouro ");
            params.put("logradouro", "%" + logradouro.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(complemento) && !complemento.isBlank()) {
            where.append("AND LOWER(e.complemento) LIKE :complemento ");
            params.put("complemento", "%" + complemento.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(cidade) && !cidade.isBlank()) {
            where.append("AND LOWER(e.cidade) LIKE :cidade ");
            params.put("cidade", "%" + cidade.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(estado) && !estado.isBlank()) {
            where.append("AND LOWER(e.estado) LIKE :estado ");
            params.put("estado", "%" + estado.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(numero)) {
            where.append("AND e.numero = :numero ");
            params.put("numero", numero);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
