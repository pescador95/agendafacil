package app.api.agendaFacil.business.usuario.service;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.configuradorAgendamento.service.ConfiguradorAgendamentoService;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.entity.ConfiguradorAgendamentoEspecial;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.loader.ConfiguradorAgendamentoEspecialLoader;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.pessoa.repository.PessoaRepository;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.usuario.DTO.CopyUserDTO;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.business.usuario.manager.UsuarioManager;
import app.api.agendaFacil.business.usuario.repository.UsuarioRepository;
import app.api.agendaFacil.business.usuario.validator.UsuarioValidator;
import app.api.agendaFacil.management.role.entity.Role;
import app.api.agendaFacil.management.role.loader.RoleLoader;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.usuario.filter.UsuarioFilters.makeUsuarioQueryStringByFilters;

@RequestScoped
public class UsuarioService extends UsuarioManager {

    private Usuario usuario;
    private Usuario usuarioDestino;
    private Usuario usuarioOrigem;
    private Pessoa pessoa;
    private Organizacao organizacaoDefault;
    private List<Organizacao> organizacoes = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();
    private List<TipoAgendamento> tiposAgendamentos = new ArrayList<>();

    private List<ConfiguradorAgendamento> configuradoresAgendamentos = new ArrayList<>();
    private List<ConfiguradorAgendamentoEspecial> configuradoresAgendamentosEspeciais = new ArrayList<>();

    public UsuarioService() {
        super();
    }

    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository, PessoaRepository pessoaRepository, SecurityContext context, ConfiguradorAgendamentoService configuradorAgendamentoService, UsuarioValidator usuarioValidator, UsuarioLoader usuarioLoader, PessoaLoader pessoaLoader, RoleLoader roleLoader, OrganizacaoLoader organizacaoLoader, TipoAgendamentoLoader tipoAgendamentoLoader, ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoEspecialLoader, ConfiguradorAgendamentoLoader configuradorAgendamentoLoader) {
        super(usuarioRepository, pessoaRepository, context, configuradorAgendamentoService, usuarioValidator, usuarioLoader, pessoaLoader, roleLoader, organizacaoLoader, tipoAgendamentoLoader, configuradorAgendamentoEspecialLoader, configuradorAgendamentoLoader);
    }

    public static List<UsuarioDTO> toDTO(List<Usuario> usuarios) {

        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();

        usuarios.forEach(usuario -> {

            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

            usuarioDTOS.add(usuarioDTO);
        });
        return usuarioDTOS;
    }

    public Response addUser(@NotNull EntidadeDTO entity) {

        try {

            Usuario usuarioValidate = new Usuario(entity);

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadUsuarioByLogin(usuarioValidate);

            validaUsuario();

            if (BasicFunctions.isEmpty(usuario)) {

                usuario = new Usuario();

                loadByUsuario(usuarioValidate, entity);

                if (!responses.hasMessages()) {

                    usuario = new Usuario(entity, pessoa, roles, organizacoes, tiposAgendamentos, context);

                    usuarioRepository.create(usuario);

                    responses.setData(new UsuarioDTO(usuario));
                    responses.setMessages("Usuário cadastrado com sucesso!");
                    responses.setStatus(201);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateUser(@NotNull EntidadeDTO entity) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Usuario usuarioValidate = new Usuario(entity);

            loadUsuarioById(usuarioValidate);

            if (BasicFunctions.isNotEmpty(usuario)) {

                loadByUsuario(usuarioValidate, entity);

                if (!responses.hasMessages()) {

                    usuario = usuario.usuario(usuario, entity, pessoa, roles, organizacoes, tiposAgendamentos, context);

                    usuarioRepository.update(usuario);

                    responses.setData(new UsuarioDTO(usuario));
                    responses.setMessages("Usuário atualizado com sucesso!");
                    responses.setStatus(200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteUser(@NotNull List<Long> pListIdUsuario) {

        try {


            List<Usuario> usuarios;
            List<Usuario> usuariosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            usuarios = usuarioLoader.listByIds(pListIdUsuario, Boolean.TRUE);
            int count = usuarios.size();

            validaUsuarios(usuarios);

            if (BasicFunctions.isNotEmpty(usuarios)) {

                usuarios.forEach((usuario) -> {

                    Usuario usuarioDeleted = usuario.deletarUsuario(usuario, context);

                    usuarioRepository.delete(usuarioDeleted);
                    usuariosAux.add(usuarioDeleted);
                });

                responses.setMessages(Responses.DELETED, count);
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response reactivateUser(@NotNull List<Long> pListIdUsuario) {

        try {

            List<Usuario> usuarios;
            List<Usuario> usuariosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            usuarios = usuarioLoader.listByIds(pListIdUsuario, Boolean.FALSE);
            int count = usuarios.size();

            validaUsuariosReativados(usuarios);

            if (BasicFunctions.isNotEmpty(usuarios)) {

                usuarios.forEach((usuario) -> {

                    Usuario usuarioReactivated = usuario.reativarUsuario(usuario, context);

                    usuarioRepository.restore(usuarioReactivated);
                    usuariosAux.add(usuarioReactivated);
                });

                responses.setMessages(Responses.REACTIVATED, count);
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public void loadPessoa(EntidadeDTO entity, Usuario pUsuario) {

        Usuario usuario;

        if (BasicFunctions.isNotEmpty(pUsuario)) {

            usuario = usuarioLoader.findByUsuario(pUsuario);

            if (BasicFunctions.isNotEmpty(usuario)) {
                pessoa = usuario.getPessoa();
            }
        }

        if (BasicFunctions.isEmpty(pessoa) && BasicFunctions.isNotEmpty(entity)) {
            pessoa = pessoaLoader.findByEntidade(entity);

        }

        if (BasicFunctions.isEmpty(pessoa)) {
            persistPessoa(entity);
        }
    }

    public void persistPessoa(EntidadeDTO entity) {

        if (BasicFunctions.isValid(entity.getPessoa())) {

            pessoa = new Pessoa(entity.getPessoa(), context);

        }

        if (BasicFunctions.isEmpty(pessoa)) {
            pessoa = new Pessoa(entity, context, getTenant());
        }

        pessoaRepository.create(pessoa);
    }

    private void loadByUsuario(Usuario pUsuario, EntidadeDTO entity) {

        pessoa = null;
        organizacaoDefault = new Organizacao();
        organizacoes = new ArrayList<>();
        tiposAgendamentos = new ArrayList<>();
        configuradoresAgendamentos = new ArrayList<>();
        configuradoresAgendamentosEspeciais = new ArrayList<>();

        loadPessoa(entity, pUsuario);

        roles = roleLoader.listByListIdRole(entity);

        if (!pUsuario.bot()) {
            roles.removeIf(role -> role.getId().equals(Usuario.BOT));
        }

        organizacoes = organizacaoLoader.listByOrganizacoes(pUsuario, entity);

        tiposAgendamentos = tipoAgendamentoLoader.listByTipoAgendamentos(pUsuario, entity);

        organizacaoDefault = organizacaoLoader.findByOrganizacaoDefault(pUsuario);

        configuradoresAgendamentosEspeciais = configuradorAgendamentoEspecialLoader.listByProfissional(pUsuario);

        configuradoresAgendamentos = configuradorAgendamentoLoader.listByProfissional(pUsuario);

        usuarioValidator.validaUsuario(pUsuario, responses, organizacoes, tiposAgendamentos);
    }

    private void loadUsuarioByLogin(Usuario pUsuario) {

        usuario = usuarioLoader.findByLogin(pUsuario);

        if (BasicFunctions.isNotEmpty(usuario)) {
            usuarioValidator.validaUsuario(usuario, responses, usuario.getOrganizacoes(), usuario.getTiposAgendamentos());
        }
    }

    private void loadUsuarioById(Usuario pUsuario) {

        usuario = usuarioLoader.findByUsuario(pUsuario);

        usuarioValidator.validaUsuario(pUsuario, responses, pUsuario.getOrganizacoes(), pUsuario.getTiposAgendamentos());
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();
            return Response.ok(new UsuarioDTO(usuarioLoader.findById(pId))).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();

    }

    public Response count(Long id, String login, Long pessoaId, Long organizacaoDefaultId, List<Long> roleId, String nomeprofissional, List<Long> organizacaoId, List<Long> tipoAgendamentoId, Boolean bot, String sortQuery, int pageIndex, int pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeUsuarioQueryStringByFilters(id, login, pessoaId, organizacaoDefaultId, roleId, nomeprofissional, organizacaoId, tipoAgendamentoId, bot, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            Integer count = usuarioLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();

    }

    public Response list(Long id, String login, Long pessoaId, Long organizacaoDefaultId, List<Long> roleId, String nomeprofissional, List<Long> organizacaoId, List<Long> tipoAgendamentoId, Boolean bot, String sortQuery, int pageIndex, int pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeUsuarioQueryStringByFilters(id, login, pessoaId, organizacaoDefaultId, roleId, nomeprofissional, organizacaoId, tipoAgendamentoId, bot, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            List<Usuario> usuarios = usuarioLoader.list(queryString);

            return Response.ok(toDTO(usuarios)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();

    }

    public Response listForScheduler(LocalDate dataAgendamento, Long organizaao, Long tipoAgendamento, Long profissional, Boolean comPreferencia) {

        try {

            responses = new Responses();

            List<Usuario> usuariosFiltrados;
            AgendamentoDTO agendamento = new AgendamentoDTO();
            agendamento.setDataAgendamento(dataAgendamento);
            agendamento.setComPreferencia(comPreferencia);
            if (BasicFunctions.isNotEmpty(tipoAgendamento)) {
                agendamento.setTipoAgendamento(new TipoAgendamentoDTO(tipoAgendamento));
            }
            if (BasicFunctions.isNotEmpty(profissional) && BasicFunctions.isValid(profissional)) {
                agendamento.setProfissionalAgendamento(new UsuarioDTO(profissional));
            }
            if (BasicFunctions.isNotEmpty(organizaao)) {
                agendamento.setOrganizacaoAgendamento(new OrganizacaoDTO(organizaao));
            }

            usuariosFiltrados = usuarioLoader.loadListUsuariosByOrganizacaoAndDataAgendamento(agendamento);

            return Response.ok(toDTO(usuariosFiltrados)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response copyUser(@NotNull CopyUserDTO entity) {

        try {

            usuarioDestino = usuarioLoader.findByUsuarioDestino(entity);

            usuarioOrigem = usuarioLoader.findByUsuarioOrigem(entity);

            EntidadeDTO entityDTO = new EntidadeDTO(usuarioOrigem);

            loadByUsuario(usuarioOrigem, entityDTO);

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            validaCopyUser();

            if (BasicFunctions.isNotEmpty(usuarioOrigem, usuarioDestino)) {

                if (!responses.hasMessages()) {

                    makeCopyFromUsuario(entity);

                    usuarioRepository.update(usuarioDestino);

                    responses.setData(new UsuarioDTO(usuarioDestino));
                    responses.setMessages("Usuário cadastrado com sucesso!");
                    responses.setStatus(201);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();

    }

    public void makeCopyFromUsuario(CopyUserDTO copy) {

        if (copy.copyOrganizacaoDefault()) {

            usuarioDestino.setOrganizacaoDefaultId(organizacaoDefault.getId());
        }
        if (copy.copyOrganizacoes()) {

            usuarioDestino.setOrganizacoes(organizacoes);
        }
        if (copy.copyTipoAgendamentos()) {

            usuarioDestino.setTiposAgendamentos(tiposAgendamentos);
        }
        if (copy.getPrivilegios()) {

            usuarioDestino.setPrivilegio(roles);
        }
        if (copy.copyConfiguradorAgendamentoEspecial()) {

            usuarioDestino.setConfiguradoresEspeciais(makeConfiguradoresAgendamentoEspeciaisCopy());
        }
        if (copy.copyConfiguradorAgendamento()) {

            makeConfiguradorAgendamentoCopy();
        }
    }

    public void makeConfiguradorAgendamentoCopy() {

        List<ConfiguradorAgendamento> newConfiguradoresAgendamentos = new ArrayList<>();

        configuradoresAgendamentos.forEach(config -> {
            ConfiguradorAgendamentoDTO configuradorAgendamentoDTO = new ConfiguradorAgendamentoDTO(config);
            configuradorAgendamentoDTO.setId(-1L);
            configuradorAgendamentoDTO.setProfissionalConfigurador(new UsuarioDTO(usuarioDestino));
            newConfiguradoresAgendamentos.add(new ConfiguradorAgendamento(configuradorAgendamentoDTO));
        });

        newConfiguradoresAgendamentos.forEach(config -> {

            configuradorAgendamentoService.addConfiguradorAgendamento(new ConfiguradorAgendamentoDTO(config));
        });
    }

    public List<ConfiguradorAgendamentoEspecial> makeConfiguradoresAgendamentoEspeciaisCopy() {

        List<ConfiguradorAgendamentoEspecial> newConfiguradoresAgendamentosEspeciais = new ArrayList<>();

        configuradoresAgendamentosEspeciais.forEach(config -> {
            ConfiguradorAgendamentoEspecialDTO configuradorAgendamentoEspecialDTO = new ConfiguradorAgendamentoEspecialDTO(config);
            configuradorAgendamentoEspecialDTO.setProfissionalConfigurador(new UsuarioDTO(usuarioDestino));
            newConfiguradoresAgendamentosEspeciais.add(new ConfiguradorAgendamentoEspecial(configuradorAgendamentoEspecialDTO));

        });
        return newConfiguradoresAgendamentosEspeciais;
    }

    private void validaUsuario() {

        if (BasicFunctions.isNotEmpty(usuario)) {
            responses.setData(new UsuarioDTO(usuario));
            responses.setMessages("Verifique as informações!");
            responses.setStatus(400);
        }
    }

    private void validaUsuarios(List<Usuario> usuarios) {

        if (BasicFunctions.isEmpty(usuarios)) {
            responses.setMessages("Usuários não localizados ou já excluídos.");
            responses.setStatus(404);
        }
    }

    private void validaUsuariosReativados(List<Usuario> usuarios) {

        if (BasicFunctions.isEmpty(usuarios)) {
            responses.setMessages("Usuários não localizados ou já reativados.");
            responses.setStatus(404);
        }
    }

    private void validaCopyUser() {

        if (BasicFunctions.isEmpty(usuarioOrigem) || BasicFunctions.isEmpty(usuarioDestino)) {
            responses.setData(new UsuarioDTO(usuarioDestino));
            responses.setMessages("Verifique as informações!");
            responses.setStatus(400);
        }
    }
}
