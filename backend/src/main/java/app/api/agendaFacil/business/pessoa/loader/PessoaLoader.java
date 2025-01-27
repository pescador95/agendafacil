package app.api.agendaFacil.business.pessoa.loader;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.atendimento.entity.Atendimento;
import app.api.agendaFacil.business.historicoPessoa.DTO.HistoricoPessoaDTO;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.pessoa.repository.PessoaRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PessoaLoader {

    final PessoaLoader pessoaLoader;
    final PessoaRepository pessoaRepository;

    public PessoaLoader(PessoaLoader pessoaLoader, PessoaRepository pessoaRepository) {
        this.pessoaLoader = pessoaLoader;
        this.pessoaRepository = pessoaRepository;
    }

    public static PanacheQuery<Pessoa> find(String query) {
        return PessoaRepository.find(query);
    }

    public Pessoa findById(Entidade pPessoa, Boolean ativo) {
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getId())) {
            return PessoaRepository.findById(pPessoa.getId(), ativo);
        }
        return null;
    }

    public Pessoa findById(EntidadeDTO pPessoa, Boolean ativo) {
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getId())) {
            return PessoaRepository.findById(pPessoa.getId(), ativo);
        }
        return null;
    }

    public Pessoa findByTelegramId(Long telegramId, Boolean ativo) {
        if (BasicFunctions.isNotEmpty(telegramId)) {
            return PessoaRepository.findByTelegramId(telegramId, ativo);
        }
        return null;
    }

    public Pessoa findByWhatsappId(Long whatsappId, Boolean ativo) {
        if (BasicFunctions.isNotEmpty(whatsappId)) {
            return PessoaRepository.findByWhatsappId(whatsappId, ativo);
        }
        return null;
    }

    public List<Pessoa> listByIds(List<Long> pListIdPessoa, Boolean ativo) {
        if (BasicFunctions.isNotEmpty(pListIdPessoa)) {
            return PessoaRepository.listByIds(pListIdPessoa, ativo);
        }
        return null;
    }

    public Pessoa findByPessoa(Pessoa pPessoa) {
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getId())) {
            return PessoaRepository.findById(pPessoa.getId());
        }
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getCpf())) {
            return PessoaRepository.findByCpf(pPessoa.getCpf(), true);
        }
        return null;
    }

    public Pessoa findByUsuario(Usuario pUsuario) {
        if (BasicFunctions.isNotEmpty(pUsuario, pUsuario.getPessoa())) {
            return findByPessoa(pUsuario.getPessoa());
        }
        return null;
    }

    public Pessoa findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return PessoaRepository.findById(pId);
        }
        return null;
    }

    public Pessoa loadByPessoaDTO(PessoaDTO pPessoa) {
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getId())) {
            return PessoaRepository.findById(pPessoa.getId());
        }
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getCpf())) {
            return PessoaRepository.findByCpf(pPessoa.getCpf(), true);
        }
        return null;
    }

    public Pessoa loadByEntidadeDTO(EntidadeDTO pPessoa) {
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getId())) {
            return PessoaRepository.findById(pPessoa.getId());
        }
        if (BasicFunctions.isNotEmpty(pPessoa, pPessoa.getCpf())) {
            return PessoaRepository.findByCpf(pPessoa.getCpf(), true);
        }
        return null;
    }


    public Pessoa findByEntidade(EntidadeDTO entity) {
        if (BasicFunctions.isNotEmpty(entity, entity.getPessoa())) {
            return loadByEntidadeDTO(entity);
        }
        return null;
    }

    public Pessoa loadPessoaByAgendamento(AgendamentoDTO pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getPessoaAgendamento())) {
            return findByPessoa(new Pessoa(pAgendamento.getPessoaAgendamento()));
        }
        return null;
    }

    public Pessoa loadPessoaByAtendimento(Atendimento pAtendimento) {

        if (BasicFunctions.isNotEmpty(pAtendimento, pAtendimento.getPessoa())) {
            return findByPessoa(pAtendimento.getPessoa());
        }
        return null;
    }

    public Pessoa loadPessoaByHistoricoPessoa(HistoricoPessoaDTO historicoPessoaDTO) {
        if (BasicFunctions.isNotEmpty(historicoPessoaDTO, historicoPessoaDTO.getPessoa())) {
            return loadByPessoaDTO(historicoPessoaDTO.getPessoa());
        }
        return null;
    }

    public List<Pessoa> list(QueryFilter queryFilter) {
        return pessoaRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return pessoaRepository.count(queryFilter);
    }
}
