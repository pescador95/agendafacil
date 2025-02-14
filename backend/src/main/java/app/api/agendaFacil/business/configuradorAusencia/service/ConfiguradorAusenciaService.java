package app.api.agendaFacil.business.configuradorAusencia.service;

import app.api.agendaFacil.business.configuradorAusencia.DTO.ConfiguradorAusenciaDTO;
import app.api.agendaFacil.business.configuradorAusencia.entity.ConfiguradorAusencia;
import app.api.agendaFacil.business.configuradorAusencia.loader.ConfiguradorAusenciaLoader;
import app.api.agendaFacil.business.configuradorAusencia.manager.ConfiguradorAusenciaManager;
import app.api.agendaFacil.business.configuradorAusencia.repository.ConfiguradorAusenciaRepository;
import app.api.agendaFacil.business.configuradorAusencia.validator.ConfiguradorAusenciaValidator;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.configuradorAusencia.filter.ConfiguradorAusenciaFilters.makeConfiguradorAusenciaQueryStringByFilters;

@RequestScoped
public class ConfiguradorAusenciaService extends ConfiguradorAusenciaManager {

    List<Usuario> profissionaisAusentes;
    List<Long> profissionaisAusentesId;
    private ConfiguradorAusencia configuradorAusencia;

    public ConfiguradorAusenciaService() {
        super();
    }

    @Inject
    public ConfiguradorAusenciaService(SecurityContext context,
                                       ConfiguradorAusenciaRepository configuradorAusenciaRepository,
                                       ConfiguradorAusenciaValidator configuradorAusenciaValidator,
                                       ConfiguradorAusenciaLoader configuradorAusenciaLoader, UsuarioLoader usuarioLoader) {
        super(context, configuradorAusenciaRepository, configuradorAusenciaValidator, configuradorAusenciaLoader,
                usuarioLoader);
    }

    public static List<ConfiguradorAusenciaDTO> toDTO(List<ConfiguradorAusencia> listConfigurador) {

        List<ConfiguradorAusenciaDTO> listaConfiguradorDTO = new ArrayList<>();

        listConfigurador.forEach(configurador -> {

            ConfiguradorAusenciaDTO configuradorDTO = new ConfiguradorAusenciaDTO(configurador);

            listaConfiguradorDTO.add(configuradorDTO);
        });
        return listaConfiguradorDTO;
    }

    public Response addConfiguradorAusencia(@NotNull ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadByConfiguradorAusenciaByDataInicioFim(pConfiguradorAusencia);

            validaConfigurador();

            configuradorAusencia = new ConfiguradorAusencia();

            if (!responses.hasMessages()) {
                responses.setMessages(new ArrayList<>());

                configuradorAusencia = new ConfiguradorAusencia(pConfiguradorAusencia, profissionaisAusentes, context, usuarioAuth);

                configuradorAusenciaRepository.create(configuradorAusencia);

                responses.setData(new ConfiguradorAusenciaDTO(configuradorAusencia));
                responses.setMessages("Configurador de Ausência cadastrado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateConfiguradorAusencia(@NotNull ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadConfiguradorAusenciaById(pConfiguradorAusencia);

            validaconfiguradorExistente();

            if (BasicFunctions.isNotEmpty(configuradorAusencia)) {

                loadByConfiguradorAusencia(pConfiguradorAusencia);

                if (!responses.hasMessages()) {
                    responses.setMessages(new ArrayList<>());

                    configuradorAusencia = configuradorAusencia.configuradorAusencia(configuradorAusencia,
                            pConfiguradorAusencia, profissionaisAusentes, usuarioAuth);

                    configuradorAusenciaRepository.update(configuradorAusencia);

                    responses.setData(new ConfiguradorAusenciaDTO(configuradorAusencia));
                    responses.setMessages("Configurador de Ausência atualizado com sucesso!");
                    responses.setStatus(200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteConfiguradorAusencia(@NotNull List<Long> pListIdConfiguradorAusencia) {

        try {

            List<ConfiguradorAusencia> configuradorAusencias;
            List<ConfiguradorAusencia> configuradorAusenciasAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            configuradorAusencias = configuradorAusenciaLoader.listByIds(pListIdConfiguradorAusencia);
            int count = configuradorAusencias.size();

            validaconfiguradores(configuradorAusencias);

            if (BasicFunctions.isNotEmpty(configuradorAusencias)) {

                configuradorAusencias.forEach((configAusencia) -> {
                    configuradorAusenciasAux.add(configAusencia);
                    responses.setData(configAusencia);
                    configuradorAusenciaRepository.remove(configAusencia);

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

    private void loadByConfiguradorAusencia(ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        profissionaisAusentes = new ArrayList<>();
        profissionaisAusentesId = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(pConfiguradorAusencia.getProfissionaisAusentes())) {
            pConfiguradorAusencia.getProfissionaisAusentes()
                    .forEach(profissional -> profissionaisAusentesId.add(profissional.getId()));
        }

        profissionaisAusentes = usuarioLoader.listByIds(profissionaisAusentesId, Boolean.TRUE);

        if (!configuradorAusenciaValidator.validarConfiguradorAusencia(pConfiguradorAusencia)) {
            responses.setMessages("Informe os dados para atualizar o Configurador de Ausência.");
            responses.setStatus(400);
        }
    }

    private void loadConfiguradorAusenciaById(ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        configuradorAusencia = configuradorAusenciaLoader.findById(pConfiguradorAusencia);
    }

    private void loadByConfiguradorAusenciaByDataInicioFim(ConfiguradorAusenciaDTO pConfiguradorAusencia) {

        configuradorAusencia = configuradorAusenciaLoader.findByDataAusencia(pConfiguradorAusencia);
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            configuradorAusencia = configuradorAusenciaLoader.findById(pId);
            return Response.ok(new ConfiguradorAusenciaDTO(configuradorAusencia)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(String nomeAusencia, LocalDate dataInicio, LocalDate dataFim, LocalTime horaInicio,
                          LocalTime horaFim, Long usuarioId, String observacao, String sortQuery, Integer pageIndex, Integer pageSize,
                          String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeConfiguradorAusenciaQueryStringByFilters(nomeAusencia, dataInicio, dataFim, horaInicio, horaFim, usuarioId, observacao, sortQuery, pageIndex, pageSize, strgOrder);

            Integer count = configuradorAusenciaLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();

    }

    public Response list(String nomeAusencia, LocalDate dataInicio, LocalDate dataFim, LocalTime horaInicio,
                         LocalTime horaFim, Long usuarioId, String observacao, String sortQuery, Integer pageIndex, Integer pageSize,
                         String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeConfiguradorAusenciaQueryStringByFilters(nomeAusencia, dataInicio, dataFim, horaInicio, horaFim, usuarioId, observacao, sortQuery, pageIndex, pageSize, strgOrder);

            List<ConfiguradorAusencia> configuradoresAusencias = configuradorAusenciaLoader.list(queryString);

            return Response.ok(toDTO(configuradoresAusencias)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaConfigurador() {

        if (BasicFunctions.isNotEmpty(configuradorAusencia)) {
            responses.setData(configuradorAusencia);
            responses.setMessages("Configurador de Ausência já existente!");
            responses.setStatus(400);
        }
    }

    private void validaconfiguradorExistente() {
        if (BasicFunctions.isEmpty(configuradorAusencia)) {

            responses.setData(configuradorAusencia);
            responses.setMessages("Não foi possível localizar o Configurador de Ausência.");
            responses.setStatus(400);
        }
    }

    private void validaconfiguradores(List<ConfiguradorAusencia> configuradorAusencias) {

        if (configuradorAusencias.isEmpty()) {
            responses.setMessages("Configuradores de Ausência não localizados ou já excluídos.");
            responses.setStatus(400);
        }

    }
}
