package app.api.agendaFacil.business.genero.service;

import app.api.agendaFacil.business.genero.DTO.GeneroDTO;
import app.api.agendaFacil.business.genero.entity.Genero;
import app.api.agendaFacil.business.genero.loader.GeneroLoader;
import app.api.agendaFacil.business.genero.manager.GeneroManager;
import app.api.agendaFacil.business.genero.repository.GeneroRepository;
import app.api.agendaFacil.business.genero.validator.GeneroValidator;
import app.core.application.DTO.Responses;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static app.api.agendaFacil.business.genero.filter.GeneroFilters.makeGeneroQueryStringByFilters;

@RequestScoped
public class GeneroService extends GeneroManager {

    private Genero genero = new Genero();

    public GeneroService() {
        super();
    }

    @Inject
    public GeneroService(GeneroRepository generoRepository, GeneroValidator generoValidator,
                         GeneroLoader generoLoader) {
        super(generoRepository, generoValidator, generoLoader);
    }

    public static List<GeneroDTO> toDTO(List<Genero> parameter) {

        List<GeneroDTO> generoDTOS = new ArrayList<>();

        parameter.forEach(genero -> {

            GeneroDTO generoDTO = new GeneroDTO(genero);

            generoDTOS.add(generoDTO);
        });
        return generoDTOS;
    }

    public Response addGenero(@NotNull GeneroDTO entity) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadByGenero(entity);

            validaGenero();

            if (BasicFunctions.isEmpty(genero)) {

                genero = new Genero();

                if (BasicFunctions.isNotEmpty(entity.getGenero())) {
                    genero.setGenero(entity.getGenero());
                }
                if (!responses.hasMessages()) {
                    generoRepository.create(genero);

                    responses.setData(new GeneroDTO(genero));
                    responses.setMessages("Gênero cadastrado com sucesso!");
                    responses.setStatus(201);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updateGenero(@NotNull GeneroDTO entity) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadByGenero(entity);

            if (!responses.hasMessages() && BasicFunctions.isNotEmpty(genero)) {

                if (BasicFunctions.isNotEmpty(entity.getGenero())) {
                    genero.setGenero(entity.getGenero());
                }
                generoRepository.update(genero);

                responses.setData(new GeneroDTO(genero));
                responses.setMessages("Gênero atualizado com sucesso!");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response deleteGenero(@NotNull List<Long> pListIdGenero) {

        try {

            List<Genero> generos;
            List<Genero> generosAux = new ArrayList<>();
            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            generos = generoLoader.listByIds(pListIdGenero);
            int count = generos.size();

            validaGeneros(generos);

            if (BasicFunctions.isNotEmpty(generos)) {

                generos.forEach((genero) -> {
                    generosAux.add(genero);
                    generoRepository.remove(genero);
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

    private void loadByGenero(GeneroDTO pGenero) {

        if (BasicFunctions.isNotEmpty(pGenero)) {
            genero = generoLoader.findByGenero(pGenero);
        }

        if (!generoValidator.validarGenero(pGenero)) {
            responses.setMessages("Informe o Gênero a cadastrar.");
            responses.setStatus(400);
        }
    }

    public Response findById(Long pId) {

        try {

            responses = new Responses();
            genero = generoLoader.findById(pId);
            return Response.ok(new GeneroDTO(genero)).status(responses.getStatus()).build();

        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count(Long id, String genero, String sortQuery, Integer pageIndex, Integer pageSize,
                          String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeGeneroQueryStringByFilters(id, genero, sortQuery, pageIndex, pageSize, strgOrder);

            Integer count = generoLoader.count(queryString);

            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response list(Long id, String genero, String sortQuery, Integer pageIndex, Integer pageSize,
                         String strgOrder) {

        try {

            responses = new Responses();

            QueryFilter queryString = makeGeneroQueryStringByFilters(id, genero, sortQuery, pageIndex, pageSize, strgOrder);

            List<Genero> generos = generoLoader.list(queryString);

            return Response.ok(toDTO(generos)).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaGenero() {

        if (BasicFunctions.isNotEmpty(genero)) {
            responses.setData(new GeneroDTO(genero));
            responses.setMessages("Gênero já cadastrado!");
            responses.setStatus(400);
        }
    }

    private void validaGeneros(List<Genero> generos) {

        if (BasicFunctions.isNotEmpty(generos)) {

            responses.setMessages("Gêneros não localizados ou já excluídos.");
            responses.setStatus(400);
        }
    }
}
