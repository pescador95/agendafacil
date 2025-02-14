package app.api.agendaFacil.business.agendamento.service;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.agendamento.loader.AgendamentoAutomaticoLoader;
import app.api.agendaFacil.business.agendamento.loader.AgendamentoLoader;
import app.api.agendaFacil.business.agendamento.manager.AgendamentoAutomaticoManager;
import app.api.agendaFacil.business.agendamento.validator.AgendamentoAutomaticoValidator;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.endereco.DTO.EnderecoDTO;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.statusAgendamento.DTO.StatusAgendamentoDTO;
import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.business.usuario.service.UsuarioService;
import app.api.agendaFacil.business.usuario.validator.UsuarioValidator;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestScoped
public class AgendamentoAutomaticoService extends AgendamentoAutomaticoManager {


    protected TipoAgendamento tipoAgendamento;
    protected Pessoa pessoa;
    protected Usuario profissional;
    protected Organizacao organizacao;
    protected ConfiguradorAgendamento configuradorAgendamento;
    protected List<Agendamento> agendamentos = new ArrayList<>();
    protected List<AgendamentoDTO> agendamentosDTO = new ArrayList<>();
    protected Set<ConfiguradorAgendamento> configuradorAgendamentoList = new HashSet<>();
    protected Set<Usuario> listaProfissionais = new HashSet<>();
    protected Boolean comPreferencia;

    public AgendamentoAutomaticoService() {
        super();
    }

    @Inject
    public AgendamentoAutomaticoService(UsuarioService usuarioService, ConfiguradorAgendamentoLoader configuradorAgendamentoLoader, AgendamentoAutomaticoValidator agendamentoAutomaticoValidator, UsuarioValidator usuarioValidator, SecurityContext context, AgendamentoLoader agendamentoLoader, AgendamentoAutomaticoLoader agendamentoAutomaticoLoader, UsuarioLoader usuarioLoader, TipoAgendamentoLoader tipoAgendamentoLoader, PessoaLoader pessoaLoader, OrganizacaoLoader organizacaoLoader) {
        super(usuarioService, configuradorAgendamentoLoader, agendamentoAutomaticoValidator, usuarioValidator, context, agendamentoLoader, agendamentoAutomaticoLoader, usuarioLoader, tipoAgendamentoLoader, pessoaLoader, organizacaoLoader);
    }

    public Response listAgendamentosLivres(@NotNull AgendamentoDTO pAgendamento, Boolean reagendar) {

        try {

            responses = new Responses();
            responses.setDatas(new ArrayList<>());
            responses.setMessages(new ArrayList<>());
            comPreferencia = false;

            if (BasicFunctions.isNotEmpty(pAgendamento)) {
                comPreferencia = pAgendamento.getComPreferencia();
            }

            responses = agendamentoAutomaticoValidator.validarDataAgendamento(pAgendamento, reagendar, comPreferencia);

            if (responses.getOk()) {

                loadByAgendamentoPreferencia(pAgendamento, comPreferencia);

                if (validaProfissionalDisponivel(pAgendamento, comPreferencia)) {

                    List<AgendamentoDTO> agendamentosLivres = makeAgendamentosByProfissionaisConfiguradores(pAgendamento, comPreferencia);
                    setAgendamentoLivresDTO(agendamentosLivres, pAgendamento, reagendar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Boolean profissionalDisponivel(Boolean comPreferencia) {
        return usuarioValidator.checkProfissional(profissional, comPreferencia);
    }

    public Boolean validaProfissionalDisponivel(AgendamentoDTO pAgendamento, Boolean comPreferencia) {

        if (!profissionalDisponivel(comPreferencia)) {
            responses.setData(pAgendamento);
            responses.setMessages("O profissional em questão não estará disponível na data escolhida: "
                    + pAgendamento.getDataAgendamento());
            responses.setStatus(400);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void setAgendamentoLivresDTO(List<AgendamentoDTO> agendamentosLivresAux, AgendamentoDTO pAgendamento, Boolean reagendar) {

        List<AgendamentoDTO> returnAgendamentosLivres;

        if (validaAgendamentoLivre(agendamentosLivresAux, pAgendamento)) {

            returnAgendamentosLivres = agendamentoLoader.makeAgendamentosLivres(agendamentosLivresAux,
                    agendamentosDTO, reagendar);

            responses.setMessages("Existem horários disponíveis.");
            responses.getDatas().addAll(returnAgendamentosLivres);
            responses.setStatus(200);
        }
    }

    public void loadByAgendamentoPreferencia(AgendamentoDTO pAgendamento, Boolean comPreferencia) {

        if (comPreferencia) {
            loadWithPreferencia(pAgendamento);
        }

        if (!comPreferencia) {
            loadWithoutPreferencia(pAgendamento);
        }

        tipoAgendamento = tipoAgendamentoLoader.loadTipoAgendamentoByAgendamento(pAgendamento);

        pessoa = pessoaLoader.loadPessoaByAgendamento(pAgendamento);

        organizacao = organizacaoLoader.loadByOrganizacao(pAgendamento);
    }

    public void loadWithPreferencia(AgendamentoDTO pAgendamento) {

        configuradorAgendamento = configuradorAgendamentoLoader.loadConfiguradorByUsuarioOrganizacao(pAgendamento);

        configuradorAgendamentoList.add(configuradorAgendamento);

        agendamentos = new ArrayList<>(agendamentoLoader.loadListAgendamentosByUsuarioDataAgenda(pAgendamento));

        agendamentosDTO = toDTO(agendamentos);

        profissional = usuarioLoader.loadUsuarioByOrganizacao(pAgendamento);

        if (BasicFunctions.isNotEmpty(profissional)) {
            listaProfissionais.add(profissional);
        }
    }

    public void loadWithoutPreferencia(AgendamentoDTO pAgendamento) {

        configuradorAgendamentoList = configuradorAgendamentoLoader.listConfiguradoresByOrganizacao(pAgendamento);

        agendamentos = agendamentoLoader.loadListAgendamentosByDataAgenda(pAgendamento);

        agendamentosDTO = toDTO(agendamentos);

        listaProfissionais = usuarioLoader.loadListUsuariosByOrganizacaoAndDataAgendamentoSet(pAgendamento);

    }

    public List<AgendamentoDTO> makeAgendamentosByProfissionaisConfiguradores(AgendamentoDTO pAgendamento, Boolean comPreferencia) {

        List<AgendamentoDTO> agendamentosLivres;

        List<AgendamentoDTO> returnAgendamentosLivres = new ArrayList<>();

        StatusAgendamento statusAgendamento = StatusAgendamento.statusLivre();

        if (BasicFunctions.isNotEmpty(listaProfissionais, configuradorAgendamentoList)) {

            for (Usuario profissional : listaProfissionais) {

                ConfiguradorAgendamento forConfiguradorAgendamento;

                forConfiguradorAgendamento = configuradorAgendamentoList.stream().filter(config -> config.getProfissionalConfigurador().getId().equals(profissional.getId())).findFirst().orElse(null);

                if (BasicFunctions.isNotEmpty(forConfiguradorAgendamento)
                        && forConfiguradorAgendamento.getProfissionalConfigurador().getId().equals(profissional.getId())) {

                    String mensagem = validaUsuarioDiposnivel(forConfiguradorAgendamento, pAgendamento, comPreferencia);

                    responses.setMessages(mensagem);
                    pAgendamento.setPessoaAgendamento(new PessoaDTO(pessoa));
                    pAgendamento.setTipoAgendamento(new TipoAgendamentoDTO(tipoAgendamento));
                    pAgendamento.setOrganizacaoAgendamento(new OrganizacaoDTO(organizacao));
                    pAgendamento.setProfissionalAgendamento(new UsuarioDTO(profissional));
                    pAgendamento.setStatusAgendamento(new StatusAgendamentoDTO(statusAgendamento));
                    pAgendamento.setEndereco(new EnderecoDTO(organizacao.getEndereco()));
                    agendamentosLivres = agendamentoLoader.makeListAgendamentosByProfissionalAgendamento(pAgendamento, forConfiguradorAgendamento);
                    if (BasicFunctions.isNotEmpty(agendamentosLivres)) {
                        returnAgendamentosLivres.addAll(agendamentosLivres);
                    }
                }
            }
        }
        return returnAgendamentosLivres;
    }

    public Boolean validaAgendamentoLivre(List<AgendamentoDTO> agendamentosLivresAux, AgendamentoDTO pAgendamento) {
        if (!BasicFunctions.isNotEmpty(agendamentosLivresAux)) {
            horariosIndisponiveis(pAgendamento);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void horariosIndisponiveis(AgendamentoDTO pAgendamento) {
        responses.setData(pAgendamento);
        responses.setMessages("Não existe horários disponíveis para a seguinte data: "
                + pAgendamento.getDataAgendamento() + " na Empresa: " + organizacao.getNome() + ".");
        responses.setStatus(400);
    }

    public String validaUsuarioDiposnivel(ConfiguradorAgendamento configuradorAgendamento, AgendamentoDTO pAgendamento, Boolean comPreferencia) {
        String mensagem = "";

        if (configuradorAgendamento.naoAtendeFimDeSemana(pAgendamento.getDataAgendamento())) {
            responses.setData(pAgendamento);
            responses.setStatus(400);

            mensagem = mensagemUsuarioIndisponivel(comPreferencia);
        }

        mensagem += " para atendimentos " + StringFunctions.nomeSemana(pAgendamento.getDataAgendamento()) + ": " + pAgendamento.getDataAgendamento() + " na Empresa: " + organizacao.getNome() + ".";

        return mensagem;
    }

    public String mensagemUsuarioIndisponivel(Boolean comPreferencia) {

        if (comPreferencia) {
            return "O profissional " + profissional.getLogin() + " em questão não estará disponível";
        }
        return "Não há nenhum profissional disponível";
    }


    private List<AgendamentoDTO> toDTO(List<Agendamento> entityList) {

        List<AgendamentoDTO> entityDTOList = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(entityList)) {
            entityList.forEach(entity -> {
                entityDTOList.add(new AgendamentoDTO(entity));
            });
        }
        return entityDTOList;
    }
}