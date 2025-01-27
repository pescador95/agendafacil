package app.api.agendaFacil.business.agendamento.query;


import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.lembrete.entity.Lembrete;
import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.core.helpers.utils.Contexto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@SuppressWarnings("unchecked")
@RequestScoped
@Transactional
public class AgendamentoQueries {

    @PersistenceContext
    EntityManager entityManager;

    public List<Agendamento> loadListAgendamentoStatusAgendadoByPessoaSemReagendar(Agendamento pAgendamento, Boolean reagendar) {
        try {
            String sqlQuery = "";
            if (reagendar) {
                sqlQuery = "select a.* from agendamento a where a.pessoaid = :pessoaId and a.dataagendamento >= :dataAgendamento and a.statusagendamentoid = :statusAgendamentoId and a.agendamentooldid is null";
            }
            if (!reagendar) {
                sqlQuery = "select a.* from agendamento a where a.pessoaid = :pessoaId and a.dataagendamento >= :dataAgendamento and a.statusagendamentoid = :statusAgendamentoId";
            }
            Query query = entityManager.createNativeQuery(sqlQuery, Agendamento.class);
            query.setParameter("pessoaId", pAgendamento.getPessoaAgendamento().getId());
            query.setParameter("statusAgendamentoId", StatusAgendamento.AGENDADO);
            query.setParameter("dataAgendamento", Contexto.dataContexto());
            return (List<Agendamento>) query.getResultList();

        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    public List<Agendamento> loadListAgendamentosSemLembretesGerados() {
        try {
            Query query = entityManager.createNativeQuery(
                    "select a.* from agendamento a join organizacao o on a.organizacaoid = o.id left join lembrete l on l.agendamentoid = a.id where a.ativo = true and a.dataagendamento >= :dataAgendamento and a.statusagendamentoid = :statusAgendamentoId and a.agendamentooldid is null and o.ativo = true and not exists ( select 1 from lembrete l2 where l2.agendamentoid = a.id)",
                    Agendamento.class);
            query.setParameter("statusAgendamentoId", StatusAgendamento.AGENDADO);
            query.setParameter("dataAgendamento", Contexto.dataContexto());
            return (List<Agendamento>) query.getResultList();

        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    public List<Agendamento> canIRunScheduler() {
        try {
            Query query = entityManager.createNativeQuery(
                    "select a.* from agendamento a left join lembrete l on a.id = l.agendamentoid where a.statusagendamentoid = 1 and a.ativo = true and a.dataagendamento >= :dataAgendamento and a.id not in ( select l2.agendamentoid  from lembrete l2 where l2.agendamentoid = a.id and a.dataagendamento >= :dataAgendamento) or a.id in (select l3.agendamentoid  from lembrete l3 where l3.agendamentoid = a.id and l3.statusnotificacao != :statusNotificacao and a.dataagendamento >= :dataAgendamento)",
                    Agendamento.class);
            query.setParameter("statusNotificacao", Lembrete.STATUS_NOTIFICACAO_ENVIADO);
            query.setParameter("dataAgendamento", Contexto.dataContexto());
            return (List<Agendamento>) query.getResultList();

        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }
}