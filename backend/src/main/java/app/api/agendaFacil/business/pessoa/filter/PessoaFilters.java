package app.api.agendaFacil.business.pessoa.filter;

import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PessoaFilters {

    public static QueryFilter makePessoaQueryStringByFilters(Long id, String nome, Long generoId, LocalDate dataNascimento, String telefone, String celular, String email, String cpf, Long telegramId, Long whatsappId, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        StringBuilder sql, count, join, where, groupBy, orderBy, limit;
        String table, alias;
        table = "pessoa";
        alias = "p";

        sql = StringFunctions.select(table, alias);
        count = StringFunctions.count(table, alias, strgOrder);
        join = StringFunctions.join();
        where = StringFunctions.where(alias, ativo);
        groupBy = StringFunctions.groupBy(alias, strgOrder);
        orderBy = StringFunctions.orderBy(alias, strgOrder, sortQuery);
        limit = StringFunctions.paginate(pageSize, pageIndex);

        Map<String, Object> params = new HashMap<>();


        if (BasicFunctions.isNotNull(nome) && !nome.isBlank()) {
            where.append("AND LOWER(p.nome) LIKE :nome ");
            params.put("nome", "%" + nome.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(telefone) && !telefone.isBlank()) {
            where.append("AND LOWER(p.telefone) LIKE :telefone ");
            params.put("telefone", "%" + telefone.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(celular) && !celular.isBlank()) {
            where.append("AND LOWER(p.celular) LIKE :celular ");
            params.put("celular", "%" + celular.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(email) && !email.isBlank()) {
            where.append("AND LOWER(p.email) LIKE :email ");
            params.put("email", "%" + email.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(cpf) && !cpf.isBlank()) {
            where.append("AND LOWER(p.cpf) LIKE :cpf ");
            params.put("cpf", "%" + cpf.toLowerCase() + "%");
        }

        if (BasicFunctions.isNotNull(id)) {
            where.append("AND p.id = :id ");
            params.put("id", id);
        }

        if (BasicFunctions.isNotNull(generoId)) {
            where.append("AND p.generoId = :generoId ");
            params.put("generoId", generoId);
        }

        if (BasicFunctions.isNotNull(dataNascimento)) {
            where.append("AND p.dataNascimento = :dataNascimento ");
            params.put("dataNascimento", dataNascimento);
        }

        if (BasicFunctions.isNotNull(telegramId)) {
            where.append("AND p.telegramId = :telegramId ");
            params.put("telegramId", telegramId);
        }

        if (BasicFunctions.isNotNull(whatsappId)) {
            where.append("AND p.whatsappId = :whatsappId ");
            params.put("whatsappId", whatsappId);
        }

        sql.append(join).append(where).append(groupBy).append(orderBy).append(limit);
        count.append(join).append(where);

        return new QueryFilter(params, sql, count);
    }
}
