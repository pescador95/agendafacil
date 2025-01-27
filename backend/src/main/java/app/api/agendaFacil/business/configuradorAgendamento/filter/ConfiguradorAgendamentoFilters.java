package app.api.agendaFacil.business.configuradorAgendamento.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ConfiguradorAgendamentoFilters {

    public static QueryFilter makeConfiguradorAgendamentoQueryStringByFilters(String nome, Long profissionalId, Long organizacaoId, Boolean configuradorOrganizacao, LocalTime horarioInicioManha, LocalTime horarioFimManha, LocalTime horarioInicioTarde, LocalTime horarioFimTarde, LocalTime horarioInicioNoite, LocalTime horarioFimNoite, LocalTime horaMinutoIntervalo, LocalTime horaMinutoTolerancia, Boolean agendaManha, Boolean agendaTarde, Boolean agendaNoite, Boolean atendeSabado, Boolean atendeDomingo, Boolean agendaSabadoManha, Boolean agendaSabadoTarde, Boolean agendaSabadoNoite, Boolean agendaDomingoManha, Boolean agendaDomingoTarde, Boolean agendaDomingoNoite, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "configuradoragendamento";
        alias = "c";

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

        if (BasicFunctions.isNotNull(profissionalId)) {
            where.append("AND c.profissionalId = :profissionalId ");
            params.put("profissionalId", profissionalId);
        }

        if (BasicFunctions.isNotNull(organizacaoId)) {
            where.append("AND c.organizacaoId = :organizacaoId ");
            params.put("organizacaoId", organizacaoId);
        }

        if (BasicFunctions.isNotNull(configuradorOrganizacao)) {
            where.append("AND c.configuradorOrganizacao = :configuradorOrganizacao ");
            params.put("configuradorOrganizacao", configuradorOrganizacao);
        }

        if (BasicFunctions.isNotNull(horarioInicioManha)) {
            where.append("AND c.horarioInicioManha = :horarioInicioManha ");
            params.put("horarioInicioManha", horarioInicioManha);
        }

        if (BasicFunctions.isNotNull(horarioFimManha)) {
            where.append("AND c.horarioFimManha = :horarioFimManha ");
            params.put("horarioFimManha", horarioFimManha);
        }

        if (BasicFunctions.isNotNull(horarioInicioTarde)) {
            where.append("AND c.horarioInicioTarde = :horarioInicioTarde ");
            params.put("horarioInicioTarde", horarioInicioTarde);
        }

        if (BasicFunctions.isNotNull(horarioInicioTarde)) {
            where.append("AND c.horarioInicioTarde = :horarioInicioTarde ");
            params.put("horarioInicioTarde", horarioInicioTarde);
        }

        if (BasicFunctions.isNotNull(horarioFimTarde)) {
            where.append("AND c.horarioFimTarde = :horarioFimTarde ");
            params.put("horarioFimTarde", horarioFimTarde);
        }

        if (BasicFunctions.isNotNull(horarioInicioNoite)) {
            where.append("AND c.horarioInicioNoite = :horarioInicioNoite ");
            params.put("horarioInicioNoite", horarioInicioNoite);
        }

        if (BasicFunctions.isNotNull(horarioFimNoite)) {
            where.append("AND c.horarioFimNoite = :horarioFimNoite ");
            params.put("horarioFimNoite", horarioFimNoite);
        }

        if (BasicFunctions.isNotNull(horaMinutoIntervalo)) {
            where.append("AND c.horaMinutoIntervalo = :horaMinutoIntervalo ");
            params.put("horaMinutoIntervalo", horaMinutoIntervalo);
        }

        if (BasicFunctions.isNotNull(horaMinutoTolerancia)) {
            where.append("AND c.horaMinutoTolerancia = :horaMinutoTolerancia ");
            params.put("horaMinutoTolerancia", horaMinutoTolerancia);
        }

        if (BasicFunctions.isNotNull(agendaManha)) {
            where.append("AND c.agendaManha = :agendaManha ");
            params.put("agendaManha", agendaManha);
        }

        if (BasicFunctions.isNotNull(agendaTarde)) {
            where.append("AND c.agendaTarde = :agendaTarde ");
            params.put("agendaTarde", agendaTarde);
        }

        if (BasicFunctions.isNotNull(agendaNoite)) {
            where.append("AND c.agendaNoite = :agendaNoite ");
            params.put("agendaNoite", agendaNoite);
        }

        if (BasicFunctions.isNotNull(atendeSabado)) {
            where.append("AND c.atendeSabado = :atendeSabado ");
            params.put("atendeSabado", atendeSabado);
        }

        if (BasicFunctions.isNotNull(atendeDomingo)) {
            where.append("AND c.atendeDomingo = :atendeDomingo ");
            params.put("atendeDomingo", atendeSabado);
        }

        if (BasicFunctions.isNotNull(agendaSabadoManha)) {
            where.append("AND c.agendaSabadoManha = :agendaSabadoManha ");
            params.put("agendaSabadoManha", agendaSabadoManha);
        }

        if (BasicFunctions.isNotNull(agendaSabadoTarde)) {
            where.append("AND c.agendaSabadoTarde = :agendaSabadoTarde ");
            params.put("agendaSabadoTarde", agendaSabadoTarde);
        }

        if (BasicFunctions.isNotNull(agendaSabadoNoite)) {
            where.append("AND c.agendaSabadoNoite = :agendaSabadoNoite ");
            params.put("agendaSabadoNoite", agendaSabadoNoite);
        }

        if (BasicFunctions.isNotNull(agendaDomingoManha)) {
            where.append("AND c.agendaDomingoManha = :agendaDomingoManha ");
            params.put("agendaDomingoManha", agendaDomingoManha);
        }

        if (BasicFunctions.isNotNull(agendaDomingoTarde)) {
            where.append("AND c.agendaDomingoTarde = :agendaDomingoTarde ");
            params.put("agendaDomingoTarde", agendaDomingoTarde);
        }

        if (BasicFunctions.isNotNull(agendaDomingoNoite)) {
            where.append("AND c.agendaDomingoNoite = :agendaDomingoNoite ");
            params.put("agendaDomingoNoite", agendaDomingoNoite);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }

}
