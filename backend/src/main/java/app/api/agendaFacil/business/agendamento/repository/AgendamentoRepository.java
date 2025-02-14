package app.api.agendaFacil.business.agendamento.repository;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.agendamento.query.AgendamentoQueries;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class AgendamentoRepository extends PersistenceRepository<Agendamento> {

    final AgendamentoQueries agendamentoQueries;

    public AgendamentoRepository(AgendamentoQueries agendamentoQueries) {
        super();
        this.agendamentoQueries = agendamentoQueries;
    }

    public static Agendamento findById(Long id) {
        return Agendamento.findById(id);
    }

    public static Agendamento findByPessoaDataAgendamento(Agendamento pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getPessoaAgendamento(), pAgendamento.getDataAgendamento())) {
            return Agendamento.find("pessoaAgendamento = ?1 and dataAgendamento = ?2 and ativo = true",
                    pAgendamento.getPessoaAgendamento(), pAgendamento.getDataAgendamento()).firstResult();
        }
        return null;
    }

    public static List<Agendamento> listByProfissionalDataAgendamento(Agendamento pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getProfissionalAgendamento(), pAgendamento.getDataAgendamento())) {
            return Agendamento.find("profissionalAgendamento = ?1 and dataAgendamento = ?2 and ativo = true",
                    pAgendamento.getProfissionalAgendamento(), pAgendamento.getDataAgendamento()).list();
        }
        return null;
    }

    public static List<Agendamento> listByDataAgendamento(Agendamento pAgendamento) {
        return Agendamento.find("dataAgendamento = ?1 and ativo = true", pAgendamento.getDataAgendamento()).list();
    }

    public static Agendamento findByPessoaDataHorarioAgendamento(Agendamento pAgendamento) {
        return Agendamento.find("pessoaAgendamento = ?1 and dataAgendamento = ?2 and horarioAgendamento = ?3 and ativo = true",
                pAgendamento.getPessoaAgendamento(), pAgendamento.getDataAgendamento(),
                pAgendamento.getHorarioAgendamento()).firstResult();
    }

    public static PanacheQuery<Agendamento> find(String query) {
        return Agendamento.find(query);
    }

    public static List<Agendamento> listByIds(List<Long> pListIdAgendamento, Boolean ativo) {
        return Agendamento.list("id in ?1 and ativo = ?2", pListIdAgendamento, ativo);
    }

    public static List<Agendamento> listByIds(List<Long> pListIdAgendamento) {
        return Agendamento.list("id in ?1", pListIdAgendamento);
    }

    public List<Agendamento> listAgendamentosSemLembretesGerados() {
        return agendamentoQueries.loadListAgendamentosSemLembretesGerados();
    }

    public List<Agendamento> listByPessoaDataAgendamentoStatusAgendadoAndReagendar(AgendamentoDTO pAgendamento, Boolean reagendar) {

        Agendamento agendamento = new Agendamento(pAgendamento);

        return agendamentoQueries.loadListAgendamentoStatusAgendadoByPessoaSemReagendar(agendamento, reagendar);
    }

    public Boolean canIRunScheduler() {
        return agendamentoQueries.canIRunScheduler();
    }

    public List<Agendamento> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Agendamento.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }

}
