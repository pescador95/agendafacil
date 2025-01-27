package app.api.agendaFacil.business.configuradorAgendamentoEspecial.service;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.entity.ConfiguradorAgendamentoEspecial;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.loader.ConfiguradorAgendamentoEspecialLoader;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.manager.ConfiguradorAgendamentoEspecialManager;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.repository.ConfiguradorAgendamentoEspecialRepository;
import app.api.agendaFacil.business.configuradorAgendamentoEspecial.validator.ConfiguradorAgendamentoEspecialValidator;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.configuradorAgendamentoEspecial.filter.ConfiguradorAgendamentoEspecialFilters.makeConfiguradorAgendamentoEspecialQueryStringByFilters;

@RequestScoped
public class ConfiguradorAgendamentoEspecialService extends ConfiguradorAgendamentoEspecialManager {


    List<TipoAgendamento> tiposAgendamentos;
    List<Long> tiposAgendamentosId;
    private ConfiguradorAgendamentoEspecial configuradorAgendamentoEspecial;
    private Usuario usuario;
    private Organizacao organizacao;


    public ConfiguradorAgendamentoEspecialService() {
        super();
    }

    @Inject
    public ConfiguradorAgendamentoEspecialService(SecurityContext context, ConfiguradorAgendamentoEspecialRepository configuradorAgendamentoEspecialRepository, ConfiguradorAgendamentoEspecialValidator configuradorAgendamentoEspecialValidator, ConfiguradorAgendamentoEspecialLoader configuradorAgendamentoEspecialLoader, TipoAgendamentoLoader tipoAgendamentoLoader, OrganizacaoLoader organizacaoLoader, UsuarioLoader usuarioLoader) {
        super(context, configuradorAgendamentoEspecialRepository, configuradorAgendamentoEspecialValidator, configuradorAgendamentoEspecialLoader, tipoAgendamentoLoader, organizacaoLoader, usuarioLoader);
    }

    public static List<ConfiguradorAgendamentoEspecialDTO> toDTO(List<ConfiguradorAgendamentoEspecial> listConfigurador) {

        List<ConfiguradorAgendamentoEspecialDTO> listaConfiguradorDTO = new ArrayList<>();

        listConfigurador.forEach(configurador -> {

            ConfiguradorAgendamentoEspecialDTO configuradorDTO = new ConfiguradorAgendamentoEspecialDTO(configurador);

            listaConfiguradorDTO.add(configuradorDTO);
        });
        return listaConfiguradorDTO;
    }

    public Response addConfiguradorAgendamentoEspecial(@NotNull ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadByOrganizacaoProfissionalData(pConfiguradorAgendamentoEspecial);

            loadByConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial);

            validaConfiguradorAgendamentoEspecial();

            configuradorAgendamentoEspecial = new ConfiguradorAgendamentoEspecial();

            if (!responses.hasMessages()) {

                configuradorAgendamentoEspecial = new ConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial,
                        tiposAgendamentos, organizacao, usuario, context);

                configuradorAgendamentoEspecialRepository.create(configuradorAgendamentoEspecial);

                responses.setData(new ConfiguradorAgendamentoEspecialDTO(configuradorAgendamentoEspecial));
                responses.setMessages("Configurador de Agendamento cadastrado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateConfiguradorAgendamentoEspecial(
            @NotNull ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadConfiguradorEspecialById(pConfiguradorAgendamentoEspecial);

            if (BasicFunctions.isNotEmpty(configuradorAgendamentoEspecial)) {
                loadByConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial);
            }

            if (!responses.hasMessages()) {
                configuradorAgendamentoEspecial = configuradorAgendamentoEspecial.configuradorAgendamentoEspecial(configuradorAgendamentoEspecial, pConfiguradorAgendamentoEspecial,
                        tiposAgendamentos, organizacao, usuario, usuarioAuth);

                configuradorAgendamentoEspecialRepository.update(configuradorAgendamentoEspecial);

                responses.setData(new ConfiguradorAgendamentoEspecialDTO(configuradorAgendamentoEspecial));
                responses.setMessages("Configurador de Agendamento atualizado com sucesso!");
                responses.setStatus(200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteConfiguradorAgendamentoEspecial(@NotNull List<Long> pListIdConfiguradorAgendamento) {

        try {

            List<ConfiguradorAgendamentoEspecial> configuradorAgendamentos;
            List<ConfiguradorAgendamentoEspecial> agendamentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            configuradorAgendamentos = configuradorAgendamentoEspecialLoader.listByIds(pListIdConfiguradorAgendamento);
            int count = configuradorAgendamentos.size();


            validaConfiguradores(configuradorAgendamentos);

            if (BasicFunctions.isNotEmpty(configuradorAgendamentos)) {

                configuradorAgendamentos.forEach((configAendamento) -> {
                    agendamentosAux.add(configAendamento);
                    responses.setData(configAendamento);
                    configuradorAgendamentoEspecialRepository.remove(configAendamento);

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

    private void loadByConfiguradorAgendamentoEspecial(@NotNull ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {

        tiposAgendamentosId = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial.getTiposAgendamentos())) {
            pConfiguradorAgendamentoEspecial.getTiposAgendamentos()
                    .forEach(tipoAgendamento -> tiposAgendamentosId.add(tipoAgendamento.getId()));
        }

        tiposAgendamentos = tipoAgendamentoLoader.listByIds(tiposAgendamentosId);

        organizacao = organizacaoLoader.loadByConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial);

        usuario = usuarioLoader.findByConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial);

        configuradorAgendamentoEspecialValidator.validaConfiguradorEspecial(responses, pConfiguradorAgendamentoEspecial, organizacao, usuario);

    }

    private void loadConfiguradorEspecialById(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {

        configuradorAgendamentoEspecial = configuradorAgendamentoEspecialLoader.findById(new ConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial));
    }

    private void loadByOrganizacaoProfissionalData(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial) {

        configuradorAgendamentoEspecial = configuradorAgendamentoEspecialLoader.findByConfiguradorAgendamentoEspecial(pConfiguradorAgendamentoEspecial);
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            configuradorAgendamentoEspecial = configuradorAgendamentoEspecialLoader.findById(pId);
            return Response.ok(new ConfiguradorAgendamentoEspecialDTO(configuradorAgendamentoEspecial)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
            return Response.ok(responses).status(responses.getStatus()).build();
        }
    }

    public Response count(String nome, Long profissionalId, Long tipoAgendamentoId, LocalDate dataInicio, LocalDate dataFim, Long organizacaoId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeConfiguradorAgendamentoEspecialQueryStringByFilters(nome, profissionalId, tipoAgendamentoId, dataInicio, dataFim, organizacaoId, sortQuery, pageIndex, pageSize, strgOrder);

            Integer count = configuradorAgendamentoEspecialLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
            return Response.ok(responses).status(responses.getStatus()).build();
        }
    }

    public Response list(String nome, Long profissionalId, Long tipoAgendamentoId, LocalDate dataInicio, LocalDate dataFim, Long organizacaoId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeConfiguradorAgendamentoEspecialQueryStringByFilters(nome, profissionalId, tipoAgendamentoId, dataInicio, dataFim, organizacaoId, sortQuery, pageIndex, pageSize, strgOrder);

            List<ConfiguradorAgendamentoEspecial> configuradoresAgendamentoEspeciais = configuradorAgendamentoEspecialLoader.list(queryString);

            return Response.ok(toDTO(configuradoresAgendamentoEspeciais)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
            return Response.ok(responses).status(responses.getStatus()).build();
        }
    }

    private void validaConfiguradorAgendamentoEspecial() {

        if (BasicFunctions.isNotEmpty(configuradorAgendamentoEspecial)) {
            responses.setData(configuradorAgendamentoEspecial);
            responses.setMessages("Configurador de Agendamento Especial já existente!");
            responses.setStatus(400);
        }
    }

    private void validaConfiguradores(List<ConfiguradorAgendamentoEspecial> configuradorAgendamentos) {

        if (BasicFunctions.isNotEmpty(configuradorAgendamentos)) {
            responses.setMessages("Configuradores Agendamentos Especiais não localizados ou já excluídos.");
            responses.setStatus(400);
        }
    }
}
