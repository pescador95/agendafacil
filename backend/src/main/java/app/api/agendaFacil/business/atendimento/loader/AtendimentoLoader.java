package app.api.agendaFacil.business.atendimento.loader;

import app.api.agendaFacil.business.atendimento.entity.Atendimento;
import app.api.agendaFacil.business.atendimento.repository.AtendimentoRepository;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AtendimentoLoader {

    final AtendimentoLoader atendimentoLoader;
    final AtendimentoRepository atendimentoRepository;

    public AtendimentoLoader(AtendimentoLoader atendimentoLoader, AtendimentoRepository atendimentoRepository) {
        this.atendimentoLoader = atendimentoLoader;
        this.atendimentoRepository = atendimentoRepository;
    }

    public static List<Atendimento> listByIds(List<Long> pListIdAtendimento, Boolean ativo) {
        return AtendimentoRepository.listByIds(pListIdAtendimento, ativo);
    }

    public static Atendimento findById(Atendimento pAtendimento) {
        if (BasicFunctions.isNotEmpty(pAtendimento, pAtendimento.getId())) {
            return AtendimentoRepository.findById(pAtendimento);
        }
        return null;
    }

    public static Atendimento findById(Long id) {
        if (BasicFunctions.isNotEmpty(id)) {
            return AtendimentoRepository.findById(id);
        }
        return null;
    }

    public static PanacheQuery<Atendimento> find(String query) {
        return AtendimentoRepository.find(query);
    }

    public Atendimento findByPessoaDataAtendimento(Atendimento pAtendimento) {
        if (BasicFunctions.isNotEmpty(pAtendimento.getPessoa(), pAtendimento.getDataAtendimento())) {
            return AtendimentoRepository.findByPessoaDataAtendimento(pAtendimento);
        }
        return null;
    }

    public List<Atendimento> list(QueryFilter queryFilter) {
        return atendimentoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return atendimentoRepository.count(queryFilter);
    }
}
