package app.api.agendaFacil.business.usuario.repository;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.query.UsuarioQueries;
import app.core.application.QueryFilter;
import app.core.application.persistence.PersistenceRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class UsuarioRepository extends PersistenceRepository<Usuario> {

    final UsuarioQueries usuarioQueries;

    public UsuarioRepository(UsuarioQueries usuarioQueries) {
        super();
        this.usuarioQueries = usuarioQueries;
    }

    public static Usuario findById(Long id) {
        return Usuario.findById(id);
    }

    public static Usuario findByLogin(String login) {
        return Usuario.find("login = ?1 and ativo = ?2", login.toLowerCase(), Boolean.TRUE).firstResult();
    }

    public static List<Usuario> listByIds(List<Long> pListIdUsuarios, Boolean ativo) {
        return Usuario.list("id in ?1 and ativo = ?2", pListIdUsuarios, ativo);
    }

    public static PanacheQuery<Usuario> find(String query) {
        return Usuario.find(query);
    }

    public static Usuario findByToken(Usuario pUsuario) {
        return Usuario.find("token = ?1 and ativo = ?2 and alterarSenha = ?2", pUsuario.getToken(), Boolean.TRUE)
                .firstResult();
    }

    public static Usuario findBot() {
        return Usuario.find("bot = true and ativo = true").firstResult();
    }

    public Usuario findByLoginEmail(String login) {
        return usuarioQueries.loadUsuarioByLogin(login);
    }

    public List<Usuario> loadListUsuariosByOrganizacaoAndDataAgendamento(AgendamentoDTO agendamento) {
        return usuarioQueries.loadListUsuariosByOrganizacaoAndDataAgendamento(agendamento);
    }

    public Usuario loadUsuarioByOrganizacaoAndProfissionalAndTipoAgendamento(AgendamentoDTO agendamento) {
        return usuarioQueries.loadUsuarioByOrganizacaoAndProfissionalAndTipoAgendamento(agendamento);
    }

    public List<Usuario> list(QueryFilter queryFilter) {
        return listByQueryFilter(queryFilter, Usuario.class);
    }

    public Integer count(QueryFilter queryFilter) {
        return countByQueryFilter(queryFilter);
    }
}
