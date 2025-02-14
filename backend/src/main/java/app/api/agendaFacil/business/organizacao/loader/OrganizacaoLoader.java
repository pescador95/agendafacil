package app.api.agendaFacil.business.organizacao.loader;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrganizacaoLoader {

    final OrganizacaoLoader organizacaoLoader;
    final OrganizacaoRepository organizacaoRepository;

    public OrganizacaoLoader(OrganizacaoLoader organizacaoLoader, OrganizacaoRepository organizacaoRepository) {
        this.organizacaoLoader = organizacaoLoader;
        this.organizacaoRepository = organizacaoRepository;
    }

    public static PanacheQuery<Organizacao> find(String query) {
        return OrganizacaoRepository.find(query);
    }

    public Organizacao loadByOrganizacao(AgendamentoDTO pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getOrganizacaoAgendamento(), pAgendamento.getOrganizacaoAgendamento())) {
            return findByOrganizacao(new Organizacao(pAgendamento.getOrganizacaoAgendamento()));
        }
        return null;
    }

    public List<Organizacao> list(List<Long> organizacoesId) {
        if (BasicFunctions.isNotEmpty(organizacoesId)) {
            return OrganizacaoRepository.list(organizacoesId);
        }
        return null;
    }

    public Organizacao loadByConfiguradorAgendamentoEspecial(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial, pConfiguradorAgendamentoEspecial.getOrganizacaoConfigurador())) {
            return findByOrganizacao(new Organizacao(pConfiguradorAgendamentoEspecial.getOrganizacaoConfigurador()));
        }
        return null;
    }

    public Organizacao loadByConfiguradorAgendamento(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamento, pConfiguradorAgendamento.getOrganizacaoConfigurador())) {
            return findByOrganizacao(pConfiguradorAgendamento.getOrganizacaoConfigurador());
        }
        return null;
    }

    public List<Organizacao> listByIds(List<Long> organizacoesId, Boolean ativo) {
        if (BasicFunctions.isNotEmpty(organizacoesId)) {
            return OrganizacaoRepository.listByIds(organizacoesId, ativo);
        }
        return null;
    }

    public Organizacao findByOrganizacao(Organizacao organizacao) {

        if (BasicFunctions.isNotEmpty(organizacao)) {
            if (BasicFunctions.isNotEmpty(organizacao, organizacao.getId())) {
                return OrganizacaoRepository.findById(organizacao.getId());
            }
            if (BasicFunctions.isNotEmpty(organizacao, organizacao.getCnpj())) {
                return OrganizacaoRepository.findByCnpj(organizacao.getCnpj());
            }
        }
        return null;
    }

    public Organizacao findByOrganizacao(OrganizacaoDTO organizacao) {

        if (BasicFunctions.isNotEmpty(organizacao)) {
            if (BasicFunctions.isNotEmpty(organizacao, organizacao.getId())) {
                return OrganizacaoRepository.findById(organizacao.getId());
            }
            if (BasicFunctions.isNotEmpty(organizacao, organizacao.getCnpj())) {
                return OrganizacaoRepository.findByCnpj(organizacao.getCnpj());
            }
        }
        return null;
    }

    public Organizacao findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return OrganizacaoRepository.findById(pId);
        }
        return null;
    }

    public Organizacao findByOrganizacaoDefault(Usuario pUsuario) {
        if (BasicFunctions.isNotEmpty(pUsuario, pUsuario.getOrganizacaoDefaultId())) {
            return Organizacao.findById(pUsuario.getOrganizacaoDefaultId());
        }
        return null;
    }

    public List<Organizacao> listByOrganizacoes(Usuario pUsuario, EntidadeDTO entity) {
        if (BasicFunctions.isNotEmpty(pUsuario, pUsuario.getOrganizacoes(), entity, entity.getOrganizacoes())) {
            return OrganizacaoRepository.list(entity.getOrganizacoes());
        }
        return null;
    }

    public List<Organizacao> list(QueryFilter queryFilter) {
        return organizacaoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return organizacaoRepository.count(queryFilter);
    }
}
