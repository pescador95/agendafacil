package app.api.agendaFacil.business.endereco.service;

import app.api.agendaFacil.business.endereco.DTO.EnderecoDTO;
import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.endereco.loader.EnderecoLoader;
import app.api.agendaFacil.business.endereco.manager.EnderecoManager;
import app.api.agendaFacil.business.endereco.repository.EnderecoRepository;
import app.api.agendaFacil.business.endereco.validator.EnderecoValidator;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.endereco.filter.EnderecoFilters.makeEnderecoQueryStringByFilters;

@RequestScoped
public class EnderecoService extends EnderecoManager {

    private Endereco endereco = new Endereco();

    public EnderecoService() {
        super();
    }

    @Inject
    public EnderecoService(SecurityContext context, EnderecoRepository enderecoRepository, EnderecoValidator enderecoValidator, EnderecoLoader enderecoLoader) {
        super(context, enderecoRepository, enderecoValidator, enderecoLoader);
    }

    public static List<EnderecoDTO> toDTO(List<Endereco> listaEndereco) {

        List<EnderecoDTO> listDTOS = new ArrayList<>();

        listaEndereco.forEach(endereco -> {

            EnderecoDTO enderecoDTO = new EnderecoDTO(endereco);

            listDTOS.add(enderecoDTO);
        });
        return listDTOS;
    }

    public Response addEndereco(@NotNull EnderecoDTO pEnderecoDTO) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Endereco pEndereco = new Endereco(pEnderecoDTO);

            loadEnderecoById(pEndereco);

            if (BasicFunctions.isEmpty(endereco)) {

                endereco = new Endereco(context);

                validaEndereco(pEndereco);

            }

            if (!responses.hasMessages()) {

                endereco = new Endereco(pEndereco, context);

                enderecoRepository.create(endereco);

                responses.setData(new EnderecoDTO(endereco));
                responses.setMessages("Endereco cadastrado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();

    }

    public Response updateEndereco(@NotNull EnderecoDTO pEnderecoDTO) throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Endereco pEndereco = new Endereco(pEnderecoDTO);

            loadEnderecoById(pEndereco);

            if (BasicFunctions.isNotEmpty(endereco)) {
                validaEndereco(pEndereco);
            }
            if (!responses.hasMessages()) {
                endereco = endereco.endereco(endereco, pEndereco, context);

                enderecoRepository.update(endereco);

                responses.setData(new EnderecoDTO(endereco));
                responses.setMessages("Endereço atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteEndereco(@NotNull List<Long> pListEndereco) {

        try {

            List<Endereco> enderecos;
            List<Endereco> enderecosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            enderecos = enderecoLoader.listByIds(pListEndereco, Boolean.TRUE);
            int count = enderecos.size();

            validaEnderecos(enderecos);

            if (BasicFunctions.isNotEmpty(enderecos)) {
                enderecos.forEach((endereco) -> {

                    Endereco enderecoDeleted = endereco.deletarEndereco(endereco, context);

                    enderecoRepository.delete(enderecoDeleted);
                    enderecosAux.add(enderecoDeleted);
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

    public Response reactivateEndereco(@NotNull List<Long> pListaIdEndereco) {

        try {

            List<Endereco> enderecos;
            List<Endereco> enderecosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            enderecos = enderecoLoader.listByIds(pListaIdEndereco, Boolean.FALSE);
            int count = enderecos.size();

            validaEnderecosReativar(enderecos);

            if (BasicFunctions.isNotEmpty(enderecos)) {

                enderecos.forEach((endereco) -> {

                    Endereco enderecoReactivated = endereco.reativarEndereco(endereco, context);

                    enderecoRepository.restore(enderecoReactivated);
                    enderecosAux.add(enderecoReactivated);
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

    private void validaEndereco(Endereco pEndereco) {

        if (!enderecoValidator.validaEndereco(pEndereco)) {
            responses.setMessages("Não foi possível localizar o Endereço.");
            responses.setStatus(400);
        }
    }

    private void loadEnderecoById(Endereco pEndereco) {

        endereco = enderecoLoader.loadEnderecoById(pEndereco);
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();
            endereco = enderecoLoader.findById(pId);
            return Response.ok(new EnderecoDTO(endereco)).status(responses.getStatus()).build();


        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(String cep, String logradouro, Long numero, String complemento, String cidade, String estado, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeEnderecoQueryStringByFilters(cep, logradouro, numero, complemento, cidade, estado, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            Integer count = enderecoLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(String cep, String logradouro, Long numero, String complemento, String cidade, String estado, String sortQuery, Integer pageIndex, Integer pageSize, Boolean ativo, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeEnderecoQueryStringByFilters(cep, logradouro, numero, complemento, cidade, estado, sortQuery, pageIndex, pageSize, ativo, strgOrder);

            List<Endereco> endereco = enderecoLoader.list(queryString);

            return Response.ok(toDTO(endereco)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaEnderecos(List<Endereco> enderecos) {


        if (BasicFunctions.isEmpty(enderecos)) {
            responses.setMessages("Pessoas não localizadas ou já excuídas.");
            responses.setStatus(400);
        }
    }

    private void validaEnderecosReativar(List<Endereco> enderecos) {
        if (enderecos.isEmpty()) {

            responses.setMessages("Pessoas não localizadas ou já reativadas.");
            responses.setStatus(400);
        }
    }
}
