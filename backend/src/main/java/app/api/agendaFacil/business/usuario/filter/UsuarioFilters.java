package app.api.agendaFacil.business.usuario.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioFilters {

    public static QueryFilter makeUsuarioQueryStringByFilters(Long id, String login, Long pessoaId, Long organizacaoDefaultId, List<Long> roleId, String nomeprofissional, List<Long> organizacaoId, List<Long> tipoAgendamentoId, Boolean bot, String sortQuery, int pageIndex, int pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "usuario";
        alias = "u";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where();
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND u.id = :id ");
            params.put("id", id);
        }

        if (BasicFunctions.isNotEmpty(tipoAgendamentoId)) {
            join.append("JOIN tipoagendamentousuarios tau on tau.profissionalid = u.id ");
            where.append("AND tau.tipoAgendamentoId in (:tipoAgendamentoId) ");
            params.put("tipoAgendamentoId", tipoAgendamentoId);
        }

        if (BasicFunctions.isNotEmpty(roleId)) {
            join.append("JOIN usuarioroles ur ON ur.usuarioId = u.id ");
            where.append("AND ur.roleId in (:roleId) ");
            params.put("roleId", roleId);
        }

        if (BasicFunctions.isNotNull(pessoaId)) {
            where.append("AND u.pessoaId = :pessoaId ");
            params.put("pessoaId", pessoaId);
        }

        if (BasicFunctions.isNotNull(login)) {
            where.append("AND u.login = :login ");
            params.put("login", login);
        }

        if (BasicFunctions.isNotNull(organizacaoDefaultId)) {
            where.append("AND u.organizacaoDefaultId >= :organizacaoDefaultId ");
            params.put("organizacaoDefaultId", organizacaoDefaultId);
        }

        if (BasicFunctions.isNotEmpty(organizacaoId)) {
            join.append("JOIN usuarioorganizacao uo on uo.usuarioid = u.id ");
            where.append("AND uo.organizacaoId in (:organizacaoId) ");
            params.put("organizacaoId", organizacaoId);
        }

        if (BasicFunctions.isNotNull(nomeprofissional) && !nomeprofissional.isBlank()) {
            join.append("JOIN pessoa p ON u.pessoaId = p.id ");
            where.append("AND LOWER(p.nome) LIKE :nomeprofissional ");
            params.put("nomeprofissional", "%" + nomeprofissional.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(bot)) {
            where.append("AND u.bot = :bot ");
            params.put("bot", bot);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);
        return new QueryFilter(params, sql, count);
    }
}
