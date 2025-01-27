package app.api.agendaFacil.management.rotina.service;

import app.api.agendaFacil.management.rotina.DTO.RotinaDTO;
import app.api.agendaFacil.management.rotina.entity.Rotina;
import app.api.agendaFacil.management.rotina.manager.RotinaManager;
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

import static app.api.agendaFacil.management.rotina.filter.RotinaFilters.makeRotinaQueryStringByFilters;

@RequestScoped
@Transactional
public class RotinaService extends RotinaManager {

    final RotinaRepository rotinaRepository;
    private final List<Rotina> rotinaFiltrados = new ArrayList<>();
    private Rotina rotina = new Rotina();
    private Rotina rotinaOld = new Rotina();

    public RotinaService() {
        super();
        this.rotinaRepository = null;
    }

    @Inject
    public RotinaService(SecurityContext context, RotinaRepository rotinaRepository) {
        super(context);
        this.rotinaRepository = rotinaRepository;
    }

    public Response addRotina(@NotNull RotinaDTO pRotina) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            validaRotina(pRotina);

            if (!responses.hasMessages()) {

                rotina = new Rotina(pRotina);
                rotina.persist();

                responses.setData(new RotinaDTO(rotina));
                responses.setMessages("Rotina cadastrado com sucesso!");
                responses.setStatus(201);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateRotina(@NotNull RotinaDTO pRotina) throws BadRequestException {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            if (BasicFunctions.isValid(pRotina.getId())) {
                rotinaOld = RotinaRepository.findById(pRotina.getId());
            }

            validaRotinaAtualizar();

            validaRotina(pRotina);

            if (!responses.hasMessages()) {

                rotina = rotina.rotina(rotinaOld, pRotina, usuarioAuth);

                rotina.persist();

                responses.setData(new RotinaDTO(rotina));
                responses.setMessages("Rotina atualizado com sucesso!");
                responses.setStatus(200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteRotina(@NotNull List<Long> pListRotina) {

        try {

            List<Rotina> rotinas;
            List<Rotina> rotinasAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            rotinas = RotinaRepository.listByIds(pListRotina);
            int count = rotinas.size();

            validaRotinas(rotinas);

            if (BasicFunctions.isNotEmpty(rotinas)) {

                rotinas.forEach((rotina) -> {
                    rotinasAux.add(rotina);
                    rotina.delete();

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

            rotina = RotinaRepository.findById(pId);
            responses.setData(new RotinaDTO(rotina));
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(Long id, String nome, String icon, String path, String titulo, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeRotinaQueryStringByFilters(id, nome, icon, path, titulo, sortQuery, pageIndex, pageSize, strgOrder);

            Integer count = rotinaRepository.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        } finally {
            return Response.ok(count).status(responses.getStatus()).build();
        }
    }

    public Response list(Long id, String nome, String icon, String path, String titulo, String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeRotinaQueryStringByFilters(id, nome, icon, path, titulo, sortQuery, pageIndex, pageSize, strgOrder);

            List<Rotina> rotinas = rotinaRepository.list(queryString);

            return Response.ok(toDTO(rotinas)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaRotinaAtualizar() {
        if (BasicFunctions.isEmpty(rotina)) {
            responses.setMessages("A Rotina qual você deseja alterar os dados não foi localizada!");
        }
    }

    private void validaRotinas(List<Rotina> rotinas) {
        if (BasicFunctions.isEmpty(rotinas)) {

            responses.setMessages("Rotinas não localizadas ou já excuídas.");
            responses.setStatus(400);
        }
    }

    private void validaRotina(RotinaDTO pRotina) {

        if (BasicFunctions.isEmpty(pRotina.getNome())) {
            responses.setMessages("Por favor, informe o nome da Rotina.");
            responses.setStatus(400);
            return;
        }
        if (BasicFunctions.isEmpty(pRotina.getIcon())) {
            responses.setMessages("Por favor, informe o ícone da Rotina.");
            responses.setStatus(400);
            return;
        }
        if (BasicFunctions.isEmpty(pRotina.getPath())) {
            responses.setMessages("Por favor, informe o caminho da Rotina.");
            responses.setStatus(400);
            return;
        }
        if (BasicFunctions.isEmpty(pRotina.getTitulo())) {
            responses.setMessages("Por favor, informe o Título da Rotina.");
            responses.setStatus(400);

        }
    }

    private List<RotinaDTO> toDTO(List<Rotina> entityList) {

        List<RotinaDTO> entityDTOList = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(entityList)) {
            entityList.forEach(entity -> {
                entityDTOList.add(new RotinaDTO(entity));
            });
        }
        return entityDTOList;
    }
}
