package app.api.agendaFacil.business.atendimento.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AtendimentoFilters {

    public static QueryFilter makeAtendimentoQueryStringByFilters(LocalDate dataAtendimento, LocalDate dataInicio,
                                                                  LocalDate dataFim, String atividade, String evolucaoSintomas, String avaliacao, Long usuarioId, Long pessoaId, String sortQuery,
                                                                  Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "atendimento";
        alias = "a";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where(alias, ativo);
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isValid(dataAtendimento)) {
            where.append("AND a.dataAtendimento = :dataAtendimento ");
            params.put("dataAtendimento", dataAtendimento);
        }
        if (BasicFunctions.isValid(dataInicio)) {
            where.append("AND a.dataAtendimento >= :dataInicio ");
            params.put("dataInicio", dataInicio);
        }
        if (BasicFunctions.isValid(dataFim)) {
            where.append("AND a.dataAtendimento <= :dataFim ");
            params.put("dataFim", dataFim);
        }
        if (BasicFunctions.isNotEmpty(atividade)) {
            where.append("AND a.atividade = :atividade ");
            params.put("atividade", atividade);
        }
        if (BasicFunctions.isNotEmpty(evolucaoSintomas)) {
            where.append("AND a.evolucaoSintomas LIKE :evolucaoSintomas ");
            params.put("evolucaoSintomas", "%" + evolucaoSintomas + "%");
        }
        if (BasicFunctions.isNotEmpty(avaliacao)) {
            where.append("AND a.avaliacao LIKE :avaliacao ");
            params.put("avaliacao", "%" + avaliacao + "%");
        }
        if (BasicFunctions.isNotEmpty(pessoaId)) {
            join.append("JOIN pessoa p ON a.pessoaId = p.id ");
            where.append("AND p.id = :pessoaId ");
            params.put("pessoaId", pessoaId);
        }
        if (BasicFunctions.isNotEmpty(usuarioId)) {
            join.append("JOIN usuario u ON a.profissionalId = u.id ");
            where.append("AND u.id = :usuarioId ");
            params.put("usuarioId", usuarioId);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
