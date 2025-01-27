package app.api.agendaFacil.business.perfilAcesso.service;

import app.api.agendaFacil.business.perfilAcesso.DTO.PerfilAcessoDTO;
import app.api.agendaFacil.business.perfilAcesso.entity.PerfilAcesso;
import app.api.agendaFacil.business.perfilAcesso.manager.PerfilAcessoManager;
import app.api.agendaFacil.business.perfilAcesso.repository.PerfilAcessoRepository;
import app.api.agendaFacil.management.rotina.entity.Rotina;
import app.api.agendaFacil.management.rotina.repository.RotinaRepository;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.perfilAcesso.filter.PerfilAcessoFilters.makePerfilAcessoQueryStringByFilters;

@RequestScoped
@Transactional
public class PerfilAcessoService extends PerfilAcessoManager {

    private final List<PerfilAcesso> perfilAcessoFiltrados = new ArrayList<>();
    private PerfilAcesso perfilAcesso = new PerfilAcesso();

    public PerfilAcessoService() {
        super();
    }

    @Inject
    public PerfilAcessoService(SecurityContext context, PerfilAcessoRepository perfilAcessoRepository) {
        super(context, perfilAcessoRepository);
    }

    private static List<Rotina> loadRotinasByPerfilAcesso(@NotNull PerfilAcesso pPerfilAcesso) {

        List<Rotina> rotinas = new ArrayList<>();

        List<Long> rotinasId = new ArrayList<>();

        if (pPerfilAcesso.hasRotinas()) {
            pPerfilAcesso.getRotinas().forEach(tipoAgendamento -> rotinasId.add(tipoAgendamento.getId()));
            rotinas = RotinaRepository.listByIds(rotinasId);
        }
        return rotinas;
    }

    public Response addPerfilAcesso(@NotNull PerfilAcessoDTO entityDTO) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            validaPerfilAcesso(entityDTO);

            if (!responses.hasMessages()) {

                perfilAcesso = new PerfilAcesso(entityDTO);

                perfilAcesso.persist();

                responses.setData(new PerfilAcessoDTO(perfilAcesso));
                responses.setMessages("Perfil de Acesso cadastrado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updatePerfilAcesso(@NotNull PerfilAcessoDTO entityDTO) throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            if (BasicFunctions.isNotEmpty(entityDTO, entityDTO.getId())) {
                perfilAcesso = PerfilAcessoRepository.findById(entityDTO.getId());
            }

            validaPerfilAcessoAtualizar();

            validaPerfilAcesso(entityDTO);

            if (!responses.hasMessages()) {

                perfilAcesso.perfilAcesso(perfilAcesso, entityDTO);

                perfilAcesso.persist();

                responses.setData(new PerfilAcessoDTO(perfilAcesso));
                responses.setMessages("Perfil de Acesso atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deletePerfilAcesso(@NotNull List<Long> pListPerfilAcesso) {

        try {

            List<PerfilAcesso> perfilAcessos;
            List<PerfilAcesso> perfilAcessosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            perfilAcessos = PerfilAcessoRepository.listByIds(pListPerfilAcesso);
            int count = perfilAcessos.size();

            validaPerfisAcessos(perfilAcessos);

            if (BasicFunctions.isNotEmpty(perfilAcessos)) {

                perfilAcessos.forEach((perfilAcesso) -> {
                    perfilAcessosAux.add(perfilAcesso);
                    perfilAcesso.delete();

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

    public Response findById(Long pId) {

        try {

            responses = new Responses();

            perfilAcesso = PerfilAcessoRepository.findById(pId);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        } finally {
            return Response.ok(new PerfilAcessoDTO(perfilAcesso)).status(responses.getStatus()).build();
        }
    }

    public Response count(Long id, String nome, Boolean criar, Boolean ler, Boolean atualizar, Boolean apagar, Long usuarioId, List<Long> rotinaId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makePerfilAcessoQueryStringByFilters(id, nome, criar, ler, atualizar, apagar, usuarioId, rotinaId, sortQuery, pageIndex, pageSize, strgOrder);

            Integer count = perfilAcessoRepository.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(Long id, String nome, Boolean criar, Boolean ler, Boolean atualizar, Boolean apagar, Long usuarioId, List<Long> rotinaId, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makePerfilAcessoQueryStringByFilters(id, nome, criar, ler, atualizar, apagar, usuarioId, rotinaId, sortQuery, pageIndex, pageSize, strgOrder);

            List<PerfilAcesso> perfilAcesso = perfilAcessoRepository.list(queryString);

            return Response.ok(toDTO(perfilAcesso)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaPerfilAcesso(PerfilAcessoDTO entityDTO) {

        if (BasicFunctions.isEmpty(entityDTO)) {
            responses.setMessages("O Perfil de Acesso qual você deseja alterar os dados não foi localizado!");
            responses.setStatus(400);
            return;
        }
        if (BasicFunctions.isNotEmpty(entityDTO)) {
            if (BasicFunctions.isNull(entityDTO.getAtualizar())) {
                responses.setMessages("Por favor, informe se é possível atualizar o Perfil de Acesso.");
                responses.setStatus(400);
                return;
            }
            if (BasicFunctions.isNull(entityDTO.getCriar())) {
                responses.setMessages("Por favor, informe se é possível criar o Perfil de Acesso.");
                responses.setStatus(400);
                return;
            }
            if (BasicFunctions.isNull(entityDTO.getApagar())) {
                responses.setMessages("Por favor, informe se é possível apagar o Perfil de Acesso.");
                responses.setStatus(400);
                return;
            }
            if (BasicFunctions.isNull(entityDTO.getLer())) {
                responses.setMessages("Por favor, informe se é possível ler o Perfil de Acesso");
                responses.setStatus(400);
                return;
            }
            if (BasicFunctions.isEmpty(entityDTO.getNome())) {
                responses.setMessages("Por favor, informe o nome do Perfil de Acesso.");
                responses.setStatus(400);
            }
        }
    }


    private void validaPerfilAcessoAtualizar() {

        if (BasicFunctions.isEmpty(perfilAcesso)) {
            responses.setMessages("O Perfil de Acesso qual você deseja alterar os dados não foi localizado!");
            responses.setStatus(400);
        }
    }

    private void validaPerfisAcessos(List<PerfilAcesso> perfilAcessos) {

        if (BasicFunctions.isEmpty(perfilAcessos)) {
            responses.setMessages("Perfis de Acesso não localizados ou já excuídos.");
            responses.setStatus(400);
        }
    }

    private List<PerfilAcessoDTO> toDTO(List<PerfilAcesso> entityList) {

        List<PerfilAcessoDTO> entityDTOList = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(entityList)) {
            entityList.forEach(entity -> {
                entityDTOList.add(new PerfilAcessoDTO(entity));
            });
        }
        return entityDTOList;
    }
}
