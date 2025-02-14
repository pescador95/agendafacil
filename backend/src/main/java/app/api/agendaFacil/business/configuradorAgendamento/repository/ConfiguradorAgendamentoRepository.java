package app.api.agendaFacil.business.configuradorAgendamento.repository;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class ConfiguradorAgendamentoRepository extends PersistenceRepository<ConfiguradorAgendamento> {

    public ConfiguradorAgendamentoRepository() {
        super();
    }

    public static ConfiguradorAgendamento findById(Long id) {
        return ConfiguradorAgendamento.findById(id);
    }

    public static ConfiguradorAgendamento findByOrganizacao(Organizacao orgazanicao) {
        return ConfiguradorAgendamento.find("organizacaoConfigurador = ?1", orgazanicao).firstResult();
    }

    public static List<ConfiguradorAgendamento> listByOrganizacao(Organizacao orgazanicao) {
        return ConfiguradorAgendamento.list("organizacaoConfigurador = ?1", orgazanicao);
    }

    public static List<ConfiguradorAgendamento> ListByProfissional(Usuario pUsuario) {
        return ConfiguradorAgendamento.list("profissionalConfigurador = ?1", pUsuario);
    }

    public static ConfiguradorAgendamento findByOrganizacaoProfissional(Agendamento pAgendamento) {
        return ConfiguradorAgendamento
                .find("organizacaoConfigurador = ?1 and profissionalConfigurador = ?2",
                        pAgendamento.getOrganizacaoAgendamento(), pAgendamento.getProfissionalAgendamento())
                .firstResult();
    }

    public static ConfiguradorAgendamento findByOrganizacaoProfissional(ConfiguradorAgendamento pConfiguradorAgendamento) {
        return ConfiguradorAgendamento
                .find("organizacaoConfigurador = ?1 and profissionalConfigurador = ?2",
                        pConfiguradorAgendamento.getOrganizacaoConfigurador(), pConfiguradorAgendamento.getProfissionalConfigurador())
                .firstResult();
    }

    public static PanacheQuery<ConfiguradorAgendamento> find(String query) {
        return ConfiguradorAgendamento.find(query);
    }

    public static List<ConfiguradorAgendamento> listByIds(List<Long> pListIdConfiguradorAgendamento) {
        return ConfiguradorAgendamento.list("id in ?1", pListIdConfiguradorAgendamento);
    }

    public List<ConfiguradorAgendamento> loadListConfiguradorAgendamentoByOrganizacao(
            @NotNull Agendamento pAgendamento) {

        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getOrganizacaoAgendamento())) {
            return ConfiguradorAgendamentoRepository.listByOrganizacao(pAgendamento.getOrganizacaoAgendamento());
        }
        return null;

    }

    public Set<ConfiguradorAgendamento> loadListConfiguradorAgendamentoByOrganizacaoSet(
            @NotNull Agendamento pAgendamento) {

        return loadListConfiguradorAgendamentoByOrganizacao(pAgendamento).stream().collect(Collectors.toSet());
    }

    public ConfiguradorAgendamento loadConfiguradorAgendamentoByOrganizacaoProfissional(
            @NotNull Agendamento pAgendamento) {

        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getOrganizacaoAgendamento(), pAgendamento.getProfissionalAgendamento())) {
            return ConfiguradorAgendamentoRepository.findByOrganizacaoProfissional(pAgendamento);

        }
        return null;
    }

    public List<ConfiguradorAgendamento> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, ConfiguradorAgendamento.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
