package app.api.agendaFacil.business.configuradorAgendamentoEspecial.repository;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.entity.ConfiguradorAgendamentoEspecial;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class ConfiguradorAgendamentoEspecialRepository extends PersistenceRepository<ConfiguradorAgendamentoEspecial> {

    public ConfiguradorAgendamentoEspecialRepository() {
        super();
    }

    public static ConfiguradorAgendamentoEspecial findById(Long id) {
        return ConfiguradorAgendamentoEspecial.findById(id);
    }

    public static ConfiguradorAgendamentoEspecial findByAgendamento(Agendamento pAgendamento) {
        return ConfiguradorAgendamentoEspecial
                .find("organizacaoConfigurador = ?1 and profissionalConfigurador = ?2 and ?3 between dataInicio and dataFim",
                        pAgendamento.getOrganizacaoAgendamento(), pAgendamento.getProfissionalAgendamento(),
                        pAgendamento.getDataAgendamento())
                .firstResult();
    }

    public static List<ConfiguradorAgendamentoEspecial> listByProfissional(Usuario pUsuario) {
        return ConfiguradorAgendamentoEspecial.list("profissionalConfigurador = ?1", pUsuario);
    }

    public static List<ConfiguradorAgendamentoEspecial> listByOrganizacaoDataAgendamento(Agendamento pAgendamento) {
        return ConfiguradorAgendamentoEspecial.list(
                "organizacaoConfigurador = ?1 and ?2 between dataInicio and dataFim",
                pAgendamento.getOrganizacaoAgendamento(), pAgendamento.getDataAgendamento());
    }

    public static ConfiguradorAgendamentoEspecial findByConfiguradorAgendamentoEspecial(
            ConfiguradorAgendamentoEspecial pConfiguradorAgendamentoEspecial) {
        return ConfiguradorAgendamentoEspecial
                .find("organizacaoConfigurador = ?1 and profissionalConfigurador = ?2 and dataInicio = ?3 and dataFim = ?4",
                        pConfiguradorAgendamentoEspecial.getOrganizacaoConfigurador(),
                        pConfiguradorAgendamentoEspecial.getProfissionalConfigurador(),
                        pConfiguradorAgendamentoEspecial.getDataInicio(), pConfiguradorAgendamentoEspecial.getDataFim())
                .firstResult();
    }

    public static PanacheQuery<ConfiguradorAgendamentoEspecial> find(String query) {
        return ConfiguradorAgendamentoEspecial.find(query);
    }

    public static List<ConfiguradorAgendamentoEspecial> listByIds(List<Long> pListIdConfiguradorAgendamento) {
        return ConfiguradorAgendamentoEspecial.list("id in ?1", pListIdConfiguradorAgendamento);
    }

    public List<ConfiguradorAgendamentoEspecial> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, ConfiguradorAgendamentoEspecial.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
