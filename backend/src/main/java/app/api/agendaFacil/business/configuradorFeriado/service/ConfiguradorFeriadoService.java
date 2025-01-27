package app.api.agendaFacil.business.configuradorFeriado.service;

import app.api.agendaFacil.business.configuradorFeriado.DTO.ConfiguradorFeriadoDTO;
import app.api.agendaFacil.business.configuradorFeriado.entity.ConfiguradorFeriado;
import app.api.agendaFacil.business.configuradorFeriado.loader.ConfiguradorFeriadoLoader;
import app.api.agendaFacil.business.configuradorFeriado.manager.ConfiguradorFeriadoManager;
import app.api.agendaFacil.business.configuradorFeriado.repository.ConfiguradorFeriadoRepository;
import app.api.agendaFacil.business.configuradorFeriado.validator.ConfiguradorFeriadoValidator;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.configuradorFeriado.filter.ConfiguradorFeriadoFilters.makeConfiguradorFeriadoQueryStringByFilters;

@RequestScoped
public class ConfiguradorFeriadoService extends ConfiguradorFeriadoManager {

    List<Organizacao> organizacoesExcecoes;
    List<Long> organizacoesExcecoesId;
    private ConfiguradorFeriado configuradorFeriado;

    public ConfiguradorFeriadoService() {
        super();
    }

    @Inject
    public ConfiguradorFeriadoService(SecurityContext context,
                                      ConfiguradorFeriadoRepository configuradorFeriadoRepository,
                                      ConfiguradorFeriadoValidator configuradorFeriadoValidator,
                                      ConfiguradorFeriadoLoader configuradorFeriadoLoader, OrganizacaoLoader organizacaoLoader) {
        super(context, configuradorFeriadoRepository, configuradorFeriadoValidator, configuradorFeriadoLoader,
                organizacaoLoader);
    }

    public static List<ConfiguradorFeriadoDTO> toDTO(List<ConfiguradorFeriado> listConfigurador) {

        List<ConfiguradorFeriadoDTO> listaConfiguradorDTO = new ArrayList<>();

        listConfigurador.forEach(configurador -> {

            ConfiguradorFeriadoDTO configuradorDTO = new ConfiguradorFeriadoDTO(configurador);

            listaConfiguradorDTO.add(configuradorDTO);
        });
        return listaConfiguradorDTO;
    }

    public Response addConfiguradorFeriado(@NotNull ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadConfiguradorFeriadoByDataFeriado(pConfiguradorFeriado);

            validaConfigurador();

            if (BasicFunctions.isEmpty(configuradorFeriado)) {

                configuradorFeriado = new ConfiguradorFeriado();

                loadByConfiguradorFeriado(pConfiguradorFeriado);

                if (!responses.hasMessages()) {

                    configuradorFeriado = new ConfiguradorFeriado(pConfiguradorFeriado, organizacoesExcecoes, context);

                    configuradorFeriadoRepository.create(configuradorFeriado);

                    responses.setData(new ConfiguradorFeriadoDTO(configuradorFeriado));
                    responses.setMessages("Configurador de Feriado cadastrado com sucesso!");
                    responses.setStatus(201);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateConfiguradorFeriado(@NotNull ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadConfiguradorFeriadoById(pConfiguradorFeriado);

            if (BasicFunctions.isNotEmpty(configuradorFeriado)) {

                loadByConfiguradorFeriado(pConfiguradorFeriado);

                if (!responses.hasMessages()) {

                    configuradorFeriado = configuradorFeriado.configuradorFeriado(configuradorFeriado,
                            pConfiguradorFeriado, organizacoesExcecoes, usuarioAuth);

                    configuradorFeriadoRepository.update(configuradorFeriado);

                    responses.setData(new ConfiguradorFeriadoDTO(configuradorFeriado));
                    responses.setMessages("Configurador de Feriado atualizado com sucesso!");
                    responses.setStatus(200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteConfiguradorFeriado(@NotNull List<Long> pListIdConfiguradorFeriado) {

        try {

            List<ConfiguradorFeriado> configuradorFeriados;
            List<ConfiguradorFeriado> configuradorFeriadosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            configuradorFeriados = configuradorFeriadoLoader.listByIds(pListIdConfiguradorFeriado);
            int count = configuradorFeriados.size();

            validaConfiguradores(configuradorFeriados);

            if (BasicFunctions.isNotEmpty(configuradorFeriados)) {

                configuradorFeriados.forEach((configFeriado) -> {
                    configuradorFeriadosAux.add(configFeriado);
                    responses.setData(configFeriado);
                    configuradorFeriadoRepository.remove(configFeriado);

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

    private void loadByConfiguradorFeriado(ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        organizacoesExcecoes = new ArrayList<>();
        organizacoesExcecoesId = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(pConfiguradorFeriado.getOrganizacoesFeriado())) {
            pConfiguradorFeriado.getOrganizacoesFeriado()
                    .forEach(organizacao -> organizacoesExcecoesId.add(organizacao.getId()));
        }

        organizacoesExcecoes = organizacaoLoader.list(organizacoesExcecoesId);

        if (!configuradorFeriadoValidator.validaConfiguradorFeriado(pConfiguradorFeriado)) {
            responses.setMessages("Informe os dados para atualizar o Configurador de Feriado.");
            responses.setStatus(400);
        }
    }

    private void loadConfiguradorFeriadoByDataFeriado(ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        configuradorFeriado = configuradorFeriadoLoader.findByDataFeriado(pConfiguradorFeriado);
    }

    private void loadConfiguradorFeriadoById(ConfiguradorFeriadoDTO pConfiguradorFeriado) {

        configuradorFeriado = configuradorFeriadoLoader.findById(pConfiguradorFeriado);
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            configuradorFeriado = configuradorFeriadoLoader.findById(pId);
            return Response.ok(new ConfiguradorFeriadoDTO(configuradorFeriado)).status(responses.getStatus()).build();

        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(String nomeFeriado, LocalDate dataFeriado, LocalDate dataInicio, LocalDate dataFim,
                          LocalTime horaInicio, LocalTime horaFim, Long organizacaoId, String observacao, String sortQuery,
                          Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeConfiguradorFeriadoQueryStringByFilters(nomeFeriado, dataFeriado, dataInicio, dataFim, horaInicio, horaFim, organizacaoId, observacao, sortQuery, pageIndex, pageSize, strgOrder);

            responses = new Responses();

            Integer count = configuradorFeriadoLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(String nomeFeriado, LocalDate dataFeriado, LocalDate dataInicio, LocalDate dataFim,
                         LocalTime horaInicio, LocalTime horaFim, Long organizacaoId, String observacao, String sortQuery,
                         Integer pageIndex, Integer pageSize, String strgOrder) {


        try {

            QueryFilter queryString = makeConfiguradorFeriadoQueryStringByFilters(nomeFeriado, dataFeriado, dataInicio, dataFim, horaInicio, horaFim, organizacaoId, observacao, sortQuery, pageIndex, pageSize, strgOrder);

            responses = new Responses();

            List<ConfiguradorFeriado> configuradorFeriado = configuradorFeriadoLoader.list(queryString);

            return Response.ok(toDTO(configuradorFeriado)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaConfigurador() {

        if (BasicFunctions.isNotEmpty(configuradorFeriado)) {
            responses.setData(configuradorFeriado);
            responses.setMessages("Configurador de Feriado já existente!");
            responses.setStatus(400);
        }
    }

    private void validaConfiguradores(List<ConfiguradorFeriado> configuradorFeriados) {

        if (BasicFunctions.isEmpty(configuradorFeriados)) {
            responses.setMessages("Configuradores de Feriados não localizados ou já excluídos.");
            responses.setStatus(400);
        }
    }
}
