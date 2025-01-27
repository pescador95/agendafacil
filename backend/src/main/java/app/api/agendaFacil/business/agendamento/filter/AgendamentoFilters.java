package app.api.agendaFacil.business.agendamento.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AgendamentoFilters {

    public static QueryFilter makeAgendamentoQueryStringByFilters(LocalDate dataAgendamento, LocalDate dataInicio, LocalDate dataFim, LocalTime horarioAgendamento, LocalTime horarioInicio, LocalTime horarioFim, Long pessoaId, String nomePessoa, String nomeProfissional, Long idStatus, Long organizacaoId, Long tipoAgendamentoId, Long profissionalId, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "agendamento";
        alias = "a";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where(alias, ativo);
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(idStatus)) {
            where.append("AND a.statusAgendamentoId = :statusId ");
            params.put("statusId", idStatus);
        }

        if (BasicFunctions.isNotNull(tipoAgendamentoId)) {
            where.append("AND a.tipoAgendamentoId = :tipoAgendamentoId ");
            params.put("tipoAgendamentoId", tipoAgendamentoId);
        }

        if (BasicFunctions.isNotNull(dataAgendamento)) {
            where.append("AND a.dataAgendamento = :dataAgendamento ");
            params.put("dataAgendamento", dataAgendamento);
        }
        if (BasicFunctions.isNotNull(dataInicio)) {
            where.append("AND a.dataAgendamento >= :dataInicio ");
            params.put("dataInicio", dataInicio);
        }
        if (BasicFunctions.isNotNull(dataFim)) {
            where.append("AND a.dataAgendamento <= :dataFim ");
            params.put("dataFim", dataFim);
        }
        if (BasicFunctions.isNotNull(horarioAgendamento)) {
            where.append("AND a.horarioAgendamento = :horarioAgendamento ");
            params.put("horarioAgendamento", horarioAgendamento);
        }
        if (BasicFunctions.isNotNull(horarioInicio)) {
            where.append("AND a.horarioAgendamento >= :horarioInicio ");
            params.put("horarioInicio", horarioInicio);
        }
        if (BasicFunctions.isNotNull(horarioFim)) {
            where.append("AND a.horarioAgendamento <= :horarioFim ");
            params.put("horarioFim", horarioFim);
        }

        if (BasicFunctions.isNotNull(pessoaId)) {
            where.append("AND a.pessoaId = :pessoaId ");
            params.put("pessoaId", pessoaId);
        }

        if (BasicFunctions.isNotNull(nomePessoa) && !nomePessoa.isBlank()) {
            join.append("JOIN pessoa p ON a.pessoaId = p.id ");
            where.append("AND LOWER(p.nome) LIKE :nomePessoa ");
            params.put("nomePessoa", "%" + nomePessoa.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(profissionalId)) {
            where.append("AND a.profissionalId = :profissionalId ");
            params.put("profissionalId", profissionalId);
        }

        if (BasicFunctions.isNotNull(nomeProfissional) && !nomeProfissional.isBlank()) {
            join.append("JOIN usuario u ON a.profissionalId = u.id ");
            where.append("AND LOWER(u.nome) LIKE :nomeProfissional ");
            params.put("nomeProfissional", "%" + nomeProfissional.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(organizacaoId)) {
            where.append("AND a.organizacaoId = :organizacaoId ");
            params.put("organizacaoId", organizacaoId);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
