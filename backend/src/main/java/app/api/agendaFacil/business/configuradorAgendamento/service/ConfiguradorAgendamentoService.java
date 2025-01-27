package app.api.agendaFacil.business.configuradorAgendamento.service;

import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.configuradorAgendamento.loader.ConfiguradorAgendamentoLoader;
import app.api.agendaFacil.business.configuradorAgendamento.manager.ConfiguradorAgendamentoManager;
import app.api.agendaFacil.business.configuradorAgendamento.repository.ConfiguradorAgendamentoRepository;
import app.api.agendaFacil.business.configuradorAgendamento.validator.ConfiguradorAgendamentoValidator;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.configuradorAgendamento.filter.ConfiguradorAgendamentoFilters.makeConfiguradorAgendamentoQueryStringByFilters;

@RequestScoped
public class ConfiguradorAgendamentoService extends ConfiguradorAgendamentoManager {

    private ConfiguradorAgendamento configuradorAgendamento = new ConfiguradorAgendamento();
    private Organizacao organizacao;
    private Usuario profissionalConfigurador;

    public ConfiguradorAgendamentoService() {
        super();
    }

    @Inject
    public ConfiguradorAgendamentoService(SecurityContext context,
                                          ConfiguradorAgendamentoRepository configuradorAgendamentoRepository,
                                          ConfiguradorAgendamentoValidator configuradorAgendamentoValidator,
                                          ConfiguradorAgendamentoLoader configuradorAgendamentoLoader, OrganizacaoLoader organizacaoLoader,
                                          UsuarioLoader usuarioLoader) {
        super(context, configuradorAgendamentoRepository, configuradorAgendamentoValidator,
                configuradorAgendamentoLoader, organizacaoLoader, usuarioLoader);
    }

    public static List<ConfiguradorAgendamentoDTO> toDTO(List<ConfiguradorAgendamento> listConfigurador) {

        List<ConfiguradorAgendamentoDTO> listaConfiguradorDTO = new ArrayList<>();

        listConfigurador.forEach(configurador -> {

            ConfiguradorAgendamentoDTO configuradorDTO = new ConfiguradorAgendamentoDTO(configurador);

            listaConfiguradorDTO.add(configuradorDTO);
        });
        return listaConfiguradorDTO;
    }

    public Response addConfiguradorAgendamento(@NotNull ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadConfiguradorAgendamento(pConfiguradorAgendamento);

            validaConfigurador();

            loadByConfiguradorAgendamento(pConfiguradorAgendamento);

            if (!responses.hasMessages()) {

                responses.setMessages(new ArrayList<>());
                configuradorAgendamento = new ConfiguradorAgendamento(pConfiguradorAgendamento, organizacao,
                        profissionalConfigurador, context);
                configuradorAgendamentoRepository.create(configuradorAgendamento);

                responses.setData(new ConfiguradorAgendamentoDTO(configuradorAgendamento));
                responses.setMessages("Configurador de Agendamento cadastrado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateConfiguradorAgendamento(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadConfiguradorAgendamento(pConfiguradorAgendamento);

            loadByConfiguradorAgendamento(pConfiguradorAgendamento);

            if (BasicFunctions.isNotEmpty(configuradorAgendamento) && !responses.hasMessages()) {

                configuradorAgendamento = configuradorAgendamento.configuradorAgendamento(configuradorAgendamento,
                        pConfiguradorAgendamento, organizacao, profissionalConfigurador, usuarioAuth);

                configuradorAgendamentoRepository.update(configuradorAgendamento);

                responses.setData(new ConfiguradorAgendamentoDTO(configuradorAgendamento));
                responses.setMessages("Configurador de Agendamento atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteConfiguradorAgendamento(@NotNull List<Long> pListIdConfiguradorAgendamento) {

        try {

            List<ConfiguradorAgendamento> configuradorAgendamentos;
            List<ConfiguradorAgendamento> agendamentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());


            configuradorAgendamentos = configuradorAgendamentoLoader.listByIds(pListIdConfiguradorAgendamento);
            int count = configuradorAgendamentos.size();

            validaConfiguradorExistente(configuradorAgendamentos);

            if (BasicFunctions.isNotEmpty(configuradorAgendamentos)) {

                configuradorAgendamentos.forEach((configAgendamento) -> {
                    agendamentosAux.add(configAgendamento);
                    responses.setData(configAgendamento);
                    configuradorAgendamentoRepository.remove(configAgendamento);

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

    private void loadByConfiguradorAgendamento(@NotNull ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {

        organizacao = new Organizacao();
        profissionalConfigurador = new Usuario();

        configuradorAgendamento = configuradorAgendamentoLoader.findByConfiguradorAgendamento(pConfiguradorAgendamento);

        organizacao = organizacaoLoader.loadByConfiguradorAgendamento(pConfiguradorAgendamento);

        profissionalConfigurador = usuarioLoader.findByConfiguradorAgendamento(pConfiguradorAgendamento);

        validaOrganizacao();
        validaProfissionalConfigurador();
    }

    private void validaOrganizacao() {
        if (BasicFunctions.isEmpty(organizacao)) {
            responses.setMessages("Por favor, informe a Organização do Configurador corretamente!");
        }
    }

    private void validaProfissionalConfigurador() {
        if (BasicFunctions.isEmpty(profissionalConfigurador)) {
            responses.setMessages("Não foi possível localizar o profissional.");
        }
    }

    private void loadConfiguradorAgendamento(ConfiguradorAgendamentoDTO pConfiguradorAgendamento) {

        if (!configuradorAgendamentoValidator.validarConfiguradorAgendamento(pConfiguradorAgendamento)) {
            addErrorvalidarConfiguradorAgendamento();
        }

        configuradorAgendamento = configuradorAgendamentoLoader.findByOrganizacaoProfissional(pConfiguradorAgendamento);

        if (BasicFunctions.isNotEmpty(configuradorAgendamento)) {

            if (!configuradorAgendamentoValidator.validarConfiguradorAgendamento(configuradorAgendamento)) {
                addErrorvalidarConfiguradorAgendamento();
            }
        }
    }

    public void addErrorvalidarConfiguradorAgendamento() {
        responses.setMessages("Informe os dados para atualizar o Configurador de Agendamento.");
        responses.setStatus(400);
    }

    public Response findById(Long pId) {

        try {

            configuradorAgendamento = configuradorAgendamentoLoader.findById(pId);
            return Response.ok(new ConfiguradorAgendamentoDTO(configuradorAgendamento)).status(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(String nome, Long profissionalId, Long organizacaoId, Boolean configuradorOrganizacao, LocalTime horarioInicioManha, LocalTime horarioFimManha, LocalTime horarioInicioTarde, LocalTime horarioFimTarde, LocalTime horarioInicioNoite, LocalTime horarioFimNoite, LocalTime horaMinutoIntervalo, LocalTime horaMinutoTolerancia, Boolean agendaManha, Boolean agendaTarde, Boolean agendaNoite, Boolean atendeSabado, Boolean atendeDomingo, Boolean agendaSabadoManha, Boolean agendaSabadoTarde, Boolean agendaSabadoNoite, Boolean agendaDomingoManha, Boolean agendaDomingoTarde, Boolean agendaDomingoNoite, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {
            responses = new Responses();

            QueryFilter queryString = makeConfiguradorAgendamentoQueryStringByFilters(nome, profissionalId, organizacaoId, configuradorOrganizacao, horarioInicioManha, horarioFimManha, horarioInicioTarde, horarioFimTarde, horarioInicioNoite, horarioFimNoite, horaMinutoIntervalo, horaMinutoTolerancia, agendaManha, agendaTarde, agendaNoite, atendeSabado, atendeDomingo, agendaSabadoManha, agendaSabadoTarde, agendaSabadoNoite, agendaDomingoManha, agendaDomingoTarde, agendaDomingoNoite, sortQuery, pageIndex, pageSize, strgOrder);

            Integer count = configuradorAgendamentoLoader.count(queryString);
            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(String nome, Long profissionalId, Long organizacaoId, Boolean configuradorOrganizacao, LocalTime horarioInicioManha, LocalTime horarioFimManha, LocalTime horarioInicioTarde, LocalTime horarioFimTarde, LocalTime horarioInicioNoite, LocalTime horarioFimNoite, LocalTime horaMinutoIntervalo, LocalTime horaMinutoTolerancia, Boolean agendaManha, Boolean agendaTarde, Boolean agendaNoite, Boolean atendeSabado, Boolean atendeDomingo, Boolean agendaSabadoManha, Boolean agendaSabadoTarde, Boolean agendaSabadoNoite, Boolean agendaDomingoManha, Boolean agendaDomingoTarde, Boolean agendaDomingoNoite, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeConfiguradorAgendamentoQueryStringByFilters(nome, profissionalId, organizacaoId, configuradorOrganizacao, horarioInicioManha, horarioFimManha, horarioInicioTarde, horarioFimTarde, horarioInicioNoite, horarioFimNoite, horaMinutoIntervalo, horaMinutoTolerancia, agendaManha, agendaTarde, agendaNoite, atendeSabado, atendeDomingo, agendaSabadoManha, agendaSabadoTarde, agendaSabadoNoite, agendaDomingoManha, agendaDomingoTarde, agendaDomingoNoite, sortQuery, pageIndex, pageSize, strgOrder);


            List<ConfiguradorAgendamento> configuradoresAgendamentos = configuradorAgendamentoLoader.list(queryString);

            return Response.ok(toDTO(configuradoresAgendamentos)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaConfigurador() {

        if (BasicFunctions.isNotEmpty(configuradorAgendamento)) {
            responses.setData(configuradorAgendamento);
            responses.setMessages("Configurador de Agendamento já realizado!");
            responses.setStatus(400);
        }
    }

    private void validaConfiguradorExistente(List<ConfiguradorAgendamento> configuradorAgendamentos) {

        if (BasicFunctions.isEmpty(configuradorAgendamentos)) {
            responses.setMessages("Agendamentos não localizados ou já excluídos.");
            responses.setStatus(400);
        }
    }
}
