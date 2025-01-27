package app.api.agendaFacil.business.lembrete.repository;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.lembrete.entity.Lembrete;
import app.api.agendaFacil.business.thread.entity.Thread;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class LembreteRepository extends PersistenceRepository<Lembrete> {

    public LembreteRepository() {
        super();
    }

    public static Lembrete findById(Long id) {
        return Lembrete.findById(id);
    }

    public static List<Lembrete> listByAgendamentos(List<Agendamento> agendamentos) {
        return Lembrete.list("agendamentoLembrete in ?1", agendamentos);
    }

    public static List<Lembrete> listAll() {
        return Lembrete.listAll();
    }

    public static List<Lembrete> listByListaFilaId(List<Long> filaIds) {
        return Lembrete.list("thread.id in ?1", filaIds);
    }

    public static List<Lembrete> listLembretesNaoEnviados() {
        return Lembrete.list("statusNotificacao <> ?1",
                Lembrete.STATUS_NOTIFICACAO_ENVIADO);
    }

    public static List<Lembrete> listByThread(Thread thread) {
        return Lembrete.list("thread = ?1", thread);
    }

    public List<Lembrete> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Lembrete.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
