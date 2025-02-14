package app.api.agendaFacil.business.historicoPessoa.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.Map;

public class HistoricoPessoaFilters {

    public static QueryFilter makeHistoricoPessoaQueryStringByFilters(Long id, String queixaPrincipal, String medicamentos, String diagnosticoClinico, String comorbidades, String ocupacao, String responsavelContato, String nomePessoa, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "historicopessoa";
        alias = "h";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where(alias, ativo);
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND h.id = :id ");
            params.put("id", id);
        }

        if (BasicFunctions.isNotNull(queixaPrincipal) && !queixaPrincipal.isBlank()) {
            where.append("AND LOWER(h.queixaPrincipal) LIKE :queixaPrincipal ");
            params.put("queixaPrincipal", "%" + queixaPrincipal.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(medicamentos) && !medicamentos.isBlank()) {
            where.append("AND LOWER(h.medicamentos) LIKE :medicamentos ");
            params.put("medicamentos", "%" + medicamentos.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(comorbidades) && !comorbidades.isBlank()) {
            where.append("AND LOWER(h.comorbidades) LIKE :comorbidades ");
            params.put("comorbidades", "%" + comorbidades.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(diagnosticoClinico) && !diagnosticoClinico.isBlank()) {
            where.append("AND LOWER(h.diagnosticoClinico) LIKE :diagnosticoClinico ");
            params.put("diagnosticoClinico", "%" + diagnosticoClinico.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(ocupacao) && !ocupacao.isBlank()) {
            where.append("AND LOWER(h.ocupacao) LIKE :ocupacao ");
            params.put("ocupacao", "%" + ocupacao.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(responsavelContato) && !responsavelContato.isBlank()) {
            where.append("AND LOWER(h.responsavelContato) LIKE :responsavelContato ");
            params.put("responsavelContato", "%" + responsavelContato.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(nomePessoa) && !nomePessoa.isBlank()) {
            where.append("AND LOWER(h.nomePessoa) LIKE :nomePessoa ");
            params.put("nomePessoa", "%" + nomePessoa.toLowerCase() + "%");
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
