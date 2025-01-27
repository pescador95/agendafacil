package app.api.agendaFacil.business.contrato.service;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.api.agendaFacil.business.contrato.entity.Contrato;
import app.api.agendaFacil.business.contrato.entity.TipoContrato;
import app.api.agendaFacil.business.contrato.loader.ContratoLoader;
import app.api.agendaFacil.business.contrato.manager.ContratoManager;
import app.api.agendaFacil.business.contrato.repository.ContratoRepository;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.contrato.filter.ContratoFilters.makeContratoQueryStringByFilters;

@RequestScoped
@Transactional
public class ContratoService extends ContratoManager {

    private final List<ContratoDTO> contratosDTO = new ArrayList<>();
    private Contrato contrato = new Contrato();
    private TipoContrato tipoContrato = new TipoContrato();

    public ContratoService() {
        super();
    }

    @Inject
    public ContratoService(SecurityContext context, ContratoLoader contratoLoader) {
        super(context, contratoLoader);
    }

    public Response addContrato(@NotNull ContratoDTO pContrato) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            if (BasicFunctions.isNotEmpty(pContrato, pContrato.getTenant())) {

                contrato = ContratoRepository.findByTenant(pContrato.getTenant());
            }

            validaContrato(pContrato);

            loadTipoContrato(pContrato);

            if (BasicFunctions.isEmpty(contrato)) {

                contrato = new Contrato(context, pContrato, tipoContrato);

                if (!responses.hasMessages()) {

                    contrato.setAtivo(Boolean.TRUE);
                    contrato.persist();

                    responses.setData(new ContratoDTO(contrato));
                    responses.setMessages("Contrato cadastrado com sucesso!");
                    responses.setStatus(201);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateContrato(@NotNull ContratoDTO pContrato) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            if (BasicFunctions.isValid(pContrato)) {
                contrato = ContratoRepository.findById(pContrato.getId());
            }
            validaContratoAtualizar(pContrato);

            loadTipoContrato(pContrato);

            if (!responses.hasMessages()) {

                contrato = contrato.contrato(contrato, pContrato, tipoContrato);

                contrato.persist();

                responses.setData(new ContratoDTO(contrato));
                responses.setMessages("Cadastro de Contrato atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteContrato(@NotNull List<Long> pListIdContrato) {

        try {

            List<Contrato> contratos;
            List<Contrato> contratosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            contratos = ContratoRepository.listByIds(pListIdContrato, Boolean.TRUE);
            int count = contratos.size();

            validaContratos(contratos);

            if (BasicFunctions.isNotEmpty(contratos)) {

                contratos.forEach((contrato) -> {

                    Contrato contratoDeleted = contrato.deletarContrato(contrato);

                    contratoDeleted.persist();
                    contratosAux.add(contratoDeleted);
                });
            }

            responses.setMessages(Responses.DELETED, count);
            responses.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response reactivateContrato(@NotNull List<Long> pListIdContrato) {

        try {

            List<Contrato> contratos;
            List<Contrato> contratosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            contratos = ContratoRepository.listByIds(pListIdContrato, Boolean.FALSE);
            int count = contratos.size();

            validaContratosReativar(contratos);

            if (BasicFunctions.isNotEmpty(contratos)) {

                contratos.forEach((contrato) -> {

                    Contrato contratoReactivated = contrato.reativarContrato(contrato);

                    contratoReactivated.persist();
                    contratosAux.add(contratoReactivated);
                });
            }

            responses.setMessages(Responses.REACTIVATED, count);
            responses.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            contrato = ContratoRepository.findById(pId);
        } catch (Exception e) {
            responses.setStatus(500);
        } finally {
            return Response.ok(new ContratoDTO(contrato)).status(responses.getStatus()).build();
        }
    }

    public Response count(String tenant, Long tipoContrato, Integer numeroMaximoSessoes, String consideracoes, LocalDate dataContrato, LocalDate dataTermino, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeContratoQueryStringByFilters(tenant, tipoContrato, numeroMaximoSessoes, consideracoes, dataContrato, dataTermino, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            responses = new Responses();

            Integer count = contratoLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(String tenant, Long tipoContrato, Integer numeroMaximoSessoes, String consideracoes, LocalDate dataContrato, LocalDate dataTermino, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            QueryFilter queryString = makeContratoQueryStringByFilters(tenant, tipoContrato, numeroMaximoSessoes, consideracoes, dataContrato, dataTermino, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            responses = new Responses();

            List<Contrato> contratos = contratoLoader.list(queryString);

            return Response.ok(toDTO(contratos)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaContrato(ContratoDTO pContrato) {

        if (BasicFunctions.isNotEmpty(contrato)) {
            responses.setData(contrato);
            responses.setMessages("Contrato já cadastrado!");
            responses.setStatus(400);
        }

        if (BasicFunctions.isNotEmpty(pContrato) && BasicFunctions.isInvalid(pContrato.getNumeroMaximoSessoes())) {

            responses.setData(contrato);
            responses.setMessages("Informe o número máximo de sessões!");
            responses.setStatus(400);

        }
    }

    private void validaContratoAtualizar(ContratoDTO pContrato) {
        if (BasicFunctions.isNotEmpty(pContrato) && (BasicFunctions.isEmpty(pContrato.getTenant()) || BasicFunctions.isEmpty(pContrato.getDataContrato())
                || (BasicFunctions.isEmpty(pContrato.getNumeroMaximoSessoes())))) {
            responses.setMessages("Informe os dados para atualizar o cadastro do Contrato.");
        }
    }

    private void validaContratos(List<Contrato> contratos) {

        if (BasicFunctions.isEmpty(contratos)) {

            responses.setMessages("Contratos não localizados ou já excluídos.");
            responses.setStatus(400);
        }
    }

    private void validaContratosReativar(List<Contrato> contratos) {

        if (BasicFunctions.isEmpty(contratos)) {

            responses.setMessages("Contratos não localizados ou já reativados.");
            responses.setStatus(400);
        }
    }

    private List<ContratoDTO> toDTO(List<Contrato> entityList) {

        List<ContratoDTO> entityDTOList = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(entityList)) {
            entityList.forEach(entity -> {
                entityDTOList.add(new ContratoDTO(entity));
            });
        }
        return entityDTOList;
    }

    private void loadTipoContrato(ContratoDTO entityDTO) {
        if (BasicFunctions.isNotEmpty(entityDTO.getTipoContrato()) && BasicFunctions.isValid(entityDTO.getTipoContrato())) {
            tipoContrato = TipoContrato.findById(entityDTO.getTipoContrato());
        }
        if (BasicFunctions.isEmpty(tipoContrato)) {
            responses.setMessages("Tipo de Contrato não localizado.");
            responses.setStatus(400);
        }
    }
}
