package app.api.agendaFacil.business.tipoAgendamento.loader;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.repository.TipoAgendamentoRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TipoAgendamentoLoader {

    final TipoAgendamentoLoader tipoAgendamentoLoader;
    final TipoAgendamentoRepository tipoAgendamentoRepository;

    public TipoAgendamentoLoader(TipoAgendamentoLoader tipoAgendamentoLoader, TipoAgendamentoRepository tipoAgendamentoRepository) {
        this.tipoAgendamentoLoader = tipoAgendamentoLoader;
        this.tipoAgendamentoRepository = tipoAgendamentoRepository;
    }

    public static PanacheQuery<TipoAgendamento> find(String query) {
        return TipoAgendamentoRepository.find(query);
    }

    public TipoAgendamento loadTipoAgendamentoByAgendamento(AgendamentoDTO pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getTipoAgendamento())) {
            if (BasicFunctions.isNotEmpty(pAgendamento.getTipoAgendamento().getId())) {
                return findById(pAgendamento.getTipoAgendamento().getId());
            }
            if (BasicFunctions.isNotEmpty(pAgendamento.getTipoAgendamento().getTipoAgendamento())) {
                return TipoAgendamentoRepository.findByTipoAgendamento(pAgendamento.getTipoAgendamento().getTipoAgendamento());
            }
        }
        return null;
    }

    public List<TipoAgendamento> listByIds(List<Long> pListIdTipoAgendamento) {
        if (BasicFunctions.isNotEmpty(pListIdTipoAgendamento)) {
            return TipoAgendamentoRepository.listByIds(pListIdTipoAgendamento);
        }
        return null;
    }

    public List<TipoAgendamento> tiposAgendamentosByOrganizacaoId(List<Long> organizacoes) {
        return tipoAgendamentoRepository.listTipoAgendamentoByOrganizacoes(organizacoes);
    }

    public TipoAgendamento findById(Long id) {
        if (BasicFunctions.isNotEmpty()) {
            return TipoAgendamentoRepository.findById(id);
        }
        return null;
    }

    public TipoAgendamento findByTipoAgendamento(TipoAgendamento pTipoAgendamento) {

        if (BasicFunctions.isNotEmpty(pTipoAgendamento)) {
            if (BasicFunctions.isNotEmpty(pTipoAgendamento.getId())) {
                return TipoAgendamentoRepository.findById(pTipoAgendamento.getId());
            }
            if (BasicFunctions.isNotEmpty(pTipoAgendamento, pTipoAgendamento.getTipoAgendamento())) {
                return TipoAgendamentoRepository.findByTipoAgendamento(pTipoAgendamento.getTipoAgendamento());
            }
        }
        return null;
    }

    public TipoAgendamento findByTipoAgendamento(TipoAgendamentoDTO pTipoAgendamento) {

        if (BasicFunctions.isNotEmpty(pTipoAgendamento)) {
            if (BasicFunctions.isNotEmpty(pTipoAgendamento.getId())) {
                return TipoAgendamentoRepository.findById(pTipoAgendamento.getId());
            }
            if (BasicFunctions.isNotEmpty(pTipoAgendamento, pTipoAgendamento.getTipoAgendamento())) {
                return TipoAgendamentoRepository.findByTipoAgendamento(pTipoAgendamento.getTipoAgendamento());
            }
        }
        return null;
    }

    public List<TipoAgendamento> listByTipoAgendamentos(Usuario pUsuario, EntidadeDTO entity) {
        if (BasicFunctions.isNotEmpty(pUsuario, pUsuario.getTiposAgendamentos(), entity, entity.getTiposAgendamentos())) {
            return TipoAgendamentoRepository.listByIds(entity.getTiposAgendamentos());
        }
        return null;
    }

    public <T> List<TipoAgendamentoDTO> list(QueryFilter queryFilter) {
        return tipoAgendamentoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return tipoAgendamentoRepository.count(queryFilter);
    }
}
