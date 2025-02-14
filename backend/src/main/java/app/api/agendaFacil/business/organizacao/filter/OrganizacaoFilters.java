package app.api.agendaFacil.business.organizacao.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.Map;

public class OrganizacaoFilters {

    public static QueryFilter makeOrganizacaoQueryStringByFilters(String nome, String cnpj, String telefone, String celular, String email, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "organizacao";
        alias = "o";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where(alias, ativo);
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();


        if (BasicFunctions.isNotNull(nome) && !nome.isBlank()) {
            where.append("AND LOWER(o.nome) LIKE :nome ");
            params.put("nome", "%" + nome.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(cnpj) && !cnpj.isBlank()) {
            where.append("AND LOWER(o.cnpj) LIKE :cnpj ");
            params.put("cnpj", "%" + cnpj.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(telefone) && !telefone.isBlank()) {
            where.append("AND LOWER(o.telefone) LIKE :telefone ");
            params.put("telefone", "%" + telefone.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(celular) && !celular.isBlank()) {
            where.append("AND LOWER(o.celular) LIKE :celular ");
            params.put("celular", "%" + celular.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(email) && !email.isBlank()) {
            where.append("AND LOWER(o.email) LIKE :email ");
            params.put("email", "%" + email.toLowerCase() + "%");
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);

    }
}
