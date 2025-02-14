package app.api.agendaFacil.business.contrato.repository;

import app.api.agendaFacil.business.contrato.entity.Contrato;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;


@RequestScoped
public class ContratoRepository extends PersistenceRepository<Contrato> {

    public ContratoRepository() {
        super();
    }

    public static Contrato findByOrganizacao(Organizacao organizacao) {
        return Contrato.find("tenant = ?1", organizacao.getTenant()).firstResult();
    }

    public static Contrato findByTenant(String tenant) {
        return Contrato.find("tenant = ?1 and ativo = ?2", tenant, Boolean.TRUE).firstResult();
    }

    public static Contrato findById(Long id) {
        return Contrato.findById(id);
    }

    public static PanacheQuery<Contrato> find(String query) {
        return Contrato.find(query);
    }

    public static List<Contrato> listByIds(List<Long> pListIdContrato, Boolean ativo) {
        return Contrato.list("id in ?1 and ativo = ?2", pListIdContrato, ativo);
    }

    public List<Contrato> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Contrato.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
