package app.api.agendaFacil.business.tipoAgendamento.query;


import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@SuppressWarnings("unchecked")
@RequestScoped
@Transactional
public class TipoAgendamentoQueries {

    @PersistenceContext
    EntityManager entityManager;

    public List<TipoAgendamento> listTipoAgendamentoByOrganizacoes(List<Long> organizacoes) {
        try {
            Query query = entityManager.createNativeQuery(
                    "select distinct t.* from tipoagendamento t join tipoagendamentoorganizacoes t2 on  t.id = t2.tipoagendamentoid join organizacao o on  o.id = t2.organizacaoid  join usuarioorganizacao uo on uo.organizacaoid = o.id join tipoagendamentousuarios t3 on  t3.profissionalid = uo.usuarioid join usuario u on u.id = uo.usuarioid where o.id in (:organizacaoId) and o.ativo = true  and u.ativo = true",
                    TipoAgendamento.class);
            query.setParameter("organizacaoId", organizacoes);
            return (List<TipoAgendamento>) query.getResultList();

        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

}