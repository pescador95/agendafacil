package app.api.agendaFacil.business.genero.loader;

import app.api.agendaFacil.business.genero.DTO.GeneroDTO;
import app.api.agendaFacil.business.genero.entity.Genero;
import app.api.agendaFacil.business.genero.repository.GeneroRepository;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GeneroLoader {

    final GeneroLoader generoLoader;
    final GeneroRepository generoRepository;

    public GeneroLoader(GeneroLoader generoLoader, GeneroRepository generoRepository) {
        this.generoLoader = generoLoader;
        this.generoRepository = generoRepository;
    }

    public static PanacheQuery<Genero> find(String query) {
        return GeneroRepository.find(query);
    }

    public List<Genero> listByIds(List<Long> pListIdGenero) {
        if (BasicFunctions.isNotEmpty(pListIdGenero)) {
            return GeneroRepository.listByIds(pListIdGenero);
        }
        return null;
    }

    public Genero findByGenero(GeneroDTO pGenero) {
        if (BasicFunctions.isNotEmpty(pGenero)) {
            if (BasicFunctions.isNotEmpty(pGenero, pGenero.getId())) {
                return GeneroRepository.findById(pGenero.getId());
            }
            if (BasicFunctions.isNotEmpty(pGenero.getGenero())) {
                return GeneroRepository.findByGenero(pGenero.getGenero());
            }
        }
        return null;
    }

    public Genero findById(Genero pGenero) {
        if (BasicFunctions.isNotEmpty(pGenero, pGenero.getId())) {
            return GeneroRepository.findById(pGenero.getId());
        }
        return null;
    }

    public Genero findById(GeneroDTO pGenero) {
        if (BasicFunctions.isNotEmpty(pGenero, pGenero.getId())) {
            return GeneroRepository.findById(pGenero.getId());
        }
        return null;
    }

    public Genero findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return GeneroRepository.findById(pId);
        }
        return null;
    }

    public Genero findById(Pessoa pPessoa) {
        if (BasicFunctions.isNotEmpty(pPessoa.getGenero())) {
            return GeneroRepository.findById(pPessoa.getGenero().getId());
        }
        return null;
    }

    public List<Genero> list(QueryFilter queryFilter) {
        return generoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return generoRepository.count(queryFilter);
    }
}
