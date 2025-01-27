package app.api.agendaFacil.business.usuario.loader;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.usuario.DTO.CopyUserDTO;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.repository.UsuarioRepository;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioLoader {

    final UsuarioLoader usuarioLoader;
    final UsuarioRepository usuarioRepository;

    public UsuarioLoader(UsuarioLoader usuarioLoader, UsuarioRepository usuarioRepository) {
        this.usuarioLoader = usuarioLoader;
        this.usuarioRepository = usuarioRepository;
    }

    public static PanacheQuery<Usuario> find(String query) {
        return UsuarioRepository.find(query);
    }

    public List<Usuario> loadListUsuariosByOrganizacaoAndDataAgendamento(AgendamentoDTO pAgendamento) {
        return usuarioRepository.loadListUsuariosByOrganizacaoAndDataAgendamento(pAgendamento);
    }

    public Set<Usuario> loadListUsuariosByOrganizacaoAndDataAgendamentoSet(AgendamentoDTO pAgendamento) {
        return loadListUsuariosByOrganizacaoAndDataAgendamento(pAgendamento).stream().collect(Collectors.toSet());
    }

    public Usuario loadUsuarioByOrganizacao(AgendamentoDTO pAgendamento) {
        return usuarioRepository.loadUsuarioByOrganizacaoAndProfissionalAndTipoAgendamento(pAgendamento);
    }

    public Usuario loadByAgendamento(AgendamentoDTO pAgendamento) {
        if (BasicFunctions.isNotEmpty(pAgendamento, pAgendamento.getProfissionalAgendamento())) {
            return findByUsuario(new Usuario(pAgendamento.getProfissionalAgendamento()));
        }
        return null;
    }

    public Usuario findByConfiguradorAgendamentoEspecial(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial, pConfiguradorAgendamentoEspecial.getProfissionalConfigurador())
                && BasicFunctions.isValid(pConfiguradorAgendamentoEspecial.getProfissionalConfigurador())) {
            return UsuarioRepository.findById(pConfiguradorAgendamentoEspecial.getProfissionalConfigurador().getId());
        }
        return null;
    }

    public Usuario findByConfiguradorAgendamento(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamento)
                && BasicFunctions.isNotEmpty(pConfiguradorAgendamento.getProfissionalConfigurador())
                && BasicFunctions.isValid(pConfiguradorAgendamento.getProfissionalConfigurador())) {
            return UsuarioRepository.findById(pConfiguradorAgendamento.getProfissionalConfigurador().getId());
        }
        return null;
    }

    public List<Usuario> listByIds(List<Long> profissionaisAusentesId, Boolean ativo) {
        return UsuarioRepository.listByIds(profissionaisAusentesId, ativo);
    }

    public Usuario findByLogin(Usuario pUsuario) {
        if (BasicFunctions.isNotEmpty(pUsuario, pUsuario.getLogin())) {
            return UsuarioRepository.findByLogin(pUsuario.getLogin());
        }
        return null;
    }

    public Usuario findByLoginEmail(String login) {
        if (BasicFunctions.isNotEmpty(login)) {
            return usuarioRepository.findByLoginEmail(login);
        }
        return null;
    }

    public Usuario findById(Long id) {
        if (BasicFunctions.isNotEmpty(id)) {
            return UsuarioRepository.findById(id);
        }
        return null;
    }

    public Usuario findByUsuarioOrigem(CopyUserDTO entity) {
        if (BasicFunctions.isNotEmpty(entity, entity.getUsuarioOrigem())) {
            return findByUsuario(entity.getUsuarioOrigem());
        }
        return null;
    }

    public Usuario findByUsuarioDestino(CopyUserDTO entity) {
        if (BasicFunctions.isNotEmpty(entity, entity.getUsuarioDestino())) {
            return findByUsuario(entity.getUsuarioDestino());
        }
        return null;
    }


    public Usuario findByUsuario(UsuarioDTO entity) {
        if (BasicFunctions.isNotEmpty(entity)) {
            if (BasicFunctions.isNotEmpty(entity.getId())) {
                return UsuarioRepository.findById(entity.getId());
            }
            if (BasicFunctions.isNotEmpty(entity.getLogin())) {
                return UsuarioRepository.findByLogin(entity.getLogin());
            }
        }
        return null;
    }

    public Usuario findByUsuario(Usuario entity) {
        if (BasicFunctions.isNotEmpty(entity)) {
            if (BasicFunctions.isNotEmpty(entity.getId())) {
                return UsuarioRepository.findById(entity.getId());
            }
            if (BasicFunctions.isNotEmpty(entity.getLogin())) {
                return UsuarioRepository.findByLogin(entity.getLogin());
            }
        }
        return null;
    }


    public Usuario findByToken(Usuario pUsuario) {
        if (BasicFunctions.isNotEmpty(pUsuario)) {
            return UsuarioRepository.findByToken(pUsuario);
        }
        return null;
    }

    public List<Usuario> list(QueryFilter queryFilter) {
        return usuarioRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return usuarioRepository.count(queryFilter);
    }
}
