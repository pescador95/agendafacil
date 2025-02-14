package app.api.agendaFacil.business.historicoPessoa.loader;

import app.api.agendaFacil.business.historicoPessoa.DTO.HistoricoPessoaDTO;
import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.api.agendaFacil.business.historicoPessoa.repository.HistoricoPessoaRepository;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class HistoricoPessoaLoader {

    final HistoricoPessoaLoader historicoPessoaLoader;
    final HistoricoPessoaRepository historicoPessoaRepository;

    public HistoricoPessoaLoader(HistoricoPessoaLoader historicoPessoaLoader, HistoricoPessoaRepository historicoPessoaRepository) {
        this.historicoPessoaLoader = historicoPessoaLoader;
        this.historicoPessoaRepository = historicoPessoaRepository;
    }

    public static PanacheQuery<HistoricoPessoa> find(String query) {
        return HistoricoPessoaRepository.find(query);
    }

    public HistoricoPessoa findById(HistoricoPessoaDTO historicoPessoaDTO) {
        if (BasicFunctions.isNotEmpty(historicoPessoaDTO, historicoPessoaDTO.getId())) {
            return HistoricoPessoaRepository.findById(historicoPessoaDTO.getId());
        }
        return null;
    }

    public List<HistoricoPessoa> listByIds(List<Long> pListIdHistoricoPessoa, Boolean ativo) {
        if (BasicFunctions.isNotEmpty(pListIdHistoricoPessoa)) {
            return HistoricoPessoaRepository.listByIds(pListIdHistoricoPessoa, ativo);
        }
        return null;
    }

    public HistoricoPessoa findById(Pessoa pessoa) {
        if (BasicFunctions.isNotEmpty(pessoa)) {
            return HistoricoPessoaRepository.findByPessoa(pessoa);
        }
        return null;
    }

    public HistoricoPessoa findById(HistoricoPessoa pHistoricoPessoa) {
        if (BasicFunctions.isNotEmpty(pHistoricoPessoa)) {
            return HistoricoPessoaRepository.findById(pHistoricoPessoa);
        }
        return null;
    }

    public HistoricoPessoa findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return HistoricoPessoaRepository.findById(pId);
        }
        return null;
    }

    public List<HistoricoPessoa> list(QueryFilter queryFilter) {
        return historicoPessoaRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return historicoPessoaRepository.count(queryFilter);
    }
}
