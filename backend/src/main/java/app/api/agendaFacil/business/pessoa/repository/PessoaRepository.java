package app.api.agendaFacil.business.pessoa.repository;

import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class PessoaRepository extends PersistenceRepository<Pessoa> {

    public PessoaRepository() {
        super();
    }

    public static Pessoa findById(Long id) {
        return Pessoa.findById(id);
    }

    public static Pessoa findById(Long pId, Boolean ativo) {
        return Pessoa.find("id = ?1 and ativo = ?2", pId, ativo).firstResult();
    }

    public static PanacheQuery<Pessoa> find(String query) {
        return Pessoa.find(query);
    }

    public static Pessoa findByTelegramId(Long telegramId, Boolean ativo) {
        return Pessoa.find("telegramId = ?1 and ativo = ?2", telegramId, ativo).firstResult();
    }

    public static Pessoa findByWhatsappId(Long whatsappId, Boolean ativo) {
        return Pessoa.find("whatsappId = ?1 and ativo = ?2", whatsappId, ativo).firstResult();
    }

    public static Pessoa findByCpf(String cpf, Boolean ativo) {
        return Pessoa.find("cpf = ?1 and ativo = ?2", cpf, ativo).firstResult();
    }

    public static List<Pessoa> listByIds(List<Long> pListPessoa, Boolean ativo) {
        return Pessoa.list("id in ?1 and ativo = ?2", pListPessoa, ativo);
    }

    public static List<Pessoa> listByCpf(String cpf, Boolean ativo) {
        return Pessoa.list("cpf = ?1 and ativo = ?2", cpf, ativo);
    }

    public List<Pessoa> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Pessoa.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}