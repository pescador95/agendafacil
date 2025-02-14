package app.api.agendaFacil.business.organizacao.repository;

import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class OrganizacaoRepository extends PersistenceRepository<Organizacao> {

    public OrganizacaoRepository() {
        super();
    }

    public static Organizacao findById(Long id) {
        return Organizacao.findById(id);
    }

    public static Organizacao findByTenant(String tenant) {
        return Organizacao.find("tenant = ?1 and ativo = true", tenant).firstResult();
    }

    public static List<Organizacao> list(List<Long> organizacoesId) {
        return Organizacao.list("id in ?1", organizacoesId);
    }

    public static List<Organizacao> listByIds(List<Long> pListIdOrganizacao, Boolean ativo) {
        return Organizacao.list("id in ?1 and ativo = ?2", pListIdOrganizacao, ativo);
    }

    public static PanacheQuery<Organizacao> find(String query) {
        return Organizacao.find(query);
    }

    public static Organizacao findByCnpj(String cnpj) {
        return Organizacao.find("cnpj = ?1 and ativo = true", cnpj).firstResult();
    }

    public static List<Organizacao> listAllAtivos() {
        return Organizacao.list("ativo = true");
    }

    public List<Organizacao> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Organizacao.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
