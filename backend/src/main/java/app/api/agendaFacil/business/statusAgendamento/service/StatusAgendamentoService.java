package app.api.agendaFacil.business.statusAgendamento.service;

import app.api.agendaFacil.business.statusAgendamento.DTO.StatusAgendamentoDTO;
import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.api.agendaFacil.business.statusAgendamento.loader.StatusAgendamentoLoader;
import app.api.agendaFacil.business.statusAgendamento.manager.StatusAgendamentoManager;
import app.api.agendaFacil.business.statusAgendamento.repository.StatusAgendamentoRepository;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.statusAgendamento.filter.StatusAgendamentoFilters.makeStatusAgendamentoQueryStringByFilters;

@RequestScoped
public class StatusAgendamentoService extends StatusAgendamentoManager {


    private StatusAgendamento statusAgendamento = new StatusAgendamento();

    public StatusAgendamentoService() {
        super();
    }

    @Inject
    public StatusAgendamentoService(StatusAgendamentoRepository statusAgendamentoRepository, StatusAgendamentoLoader statusAgendamentoLoader, SecurityContext context) {
        super(statusAgendamentoRepository, statusAgendamentoLoader, context);
    }

    public static List<StatusAgendamentoDTO> toDTO(List<StatusAgendamento> listaStatusAgendamento) {

        List<StatusAgendamentoDTO> listDTOS = new ArrayList<>();

        listaStatusAgendamento.forEach(atendimento -> {

            StatusAgendamentoDTO atendimentoDTO = new StatusAgendamentoDTO(atendimento);

            listDTOS.add(atendimentoDTO);
        });
        return listDTOS;
    }

    public Response addStatusAgendamento(@NotNull StatusAgendamentoDTO pStatusAgendamento) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadStatusByStatusAgendamento(pStatusAgendamento);

            loadAndValidaStatus(pStatusAgendamento);

            if (!responses.hasMessages()) {

                statusAgendamento = new StatusAgendamento(pStatusAgendamento, context);

                statusAgendamentoRepository.create(statusAgendamento);

                responses.setData(new StatusAgendamentoDTO(statusAgendamento));
                responses.setMessages("Status do Agendamento cadastrado com sucesso!");
                responses.setStatus(201);

            }
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateStatusAgendamento(@NotNull StatusAgendamentoDTO pStatusAgendamento) {


        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadStatusByStatusAgendamento(pStatusAgendamento);

            if (validaStatusAgendamento(pStatusAgendamento)) {

                statusAgendamento = statusAgendamento.statusAgendamento(statusAgendamento, pStatusAgendamento);

                statusAgendamentoRepository.update(statusAgendamento);

                responses.setData(new StatusAgendamentoDTO(statusAgendamento));
                responses.setMessages("Status do Agendamento atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteStatusAgendamento(@NotNull List<Long> pListIdStatusAgendamento) {


        try {

            List<StatusAgendamento> statusAgendamentos;
            List<StatusAgendamento> statusAgendamentosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            statusAgendamentos = StatusAgendamentoLoader.listByIds(pListIdStatusAgendamento);
            int count = statusAgendamentos.size();

            if (statusAgendamentos.isEmpty()) {

                responses.setMessages("Status dos Agendamentos não localizados ou já excluídos.");
                responses.setStatus(400);
                return Response.ok(responses).status(Response.Status.BAD_REQUEST).build();
            }

            statusAgendamentos.forEach((StatusAgendamento) -> {
                statusAgendamentosAux.add(StatusAgendamento);
                statusAgendamentoRepository.remove(StatusAgendamento);
            });

            responses.setMessages(Responses.DELETED, count);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void loadStatusByStatusAgendamento(StatusAgendamentoDTO pStatusAgendamento) {

        statusAgendamento = statusAgendamentoLoader.findByStatusAgendamento(pStatusAgendamento);
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            statusAgendamento = StatusAgendamentoLoader.findById(pId);

            if (BasicFunctions.isEmpty(statusAgendamento)) {
                responses.setMessages("Status não localizado.");
                responses.setStatus(404);
            }
            return Response.ok(new StatusAgendamentoDTO(statusAgendamento)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(Long id, String status, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeStatusAgendamentoQueryStringByFilters(id, status, sortQuery, pageIndex, pageSize, strgOrder);

            responses = new Responses();

            Integer count = statusAgendamentoLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(Long id, String status, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeStatusAgendamentoQueryStringByFilters(id, status, sortQuery, pageIndex, pageSize, strgOrder);

            List<StatusAgendamento> statusAgendamentos = statusAgendamentoLoader.list(queryString);

            return Response.ok(toDTO(statusAgendamentos)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Boolean validaStatusAgendamento(StatusAgendamentoDTO pStatusAgendamento) {
        if (BasicFunctions.isInvalid(pStatusAgendamento) && BasicFunctions.isEmpty(pStatusAgendamento.getStatus())) {
            responses.setStatus(400);
            responses.setMessages("Informe os dados para atualizar o cadastro do Status do Agendamento.");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void loadAndValidaStatus(StatusAgendamentoDTO pStatusAgendamento) {

        if (BasicFunctions.isNotEmpty(statusAgendamento)) {
            responses.setData(statusAgendamento);
            responses.setMessages("Status do Agendamento já cadastrado!");
            responses.setStatus(400);
        }
        if (BasicFunctions.isEmpty(pStatusAgendamento.getStatus())) {
            responses.setMessages("Informe o Status do Agendamento a cadastrar.");
        }

        if (BasicFunctions.isEmpty(statusAgendamento)) {
            statusAgendamento = new StatusAgendamento();
        }
        if (BasicFunctions.isNotEmpty(pStatusAgendamento.getStatus())) {
            statusAgendamento.setStatus(pStatusAgendamento.getStatus());
        }
    }
}
