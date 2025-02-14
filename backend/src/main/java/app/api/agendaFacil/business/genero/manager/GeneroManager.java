package app.api.agendaFacil.business.genero.manager;

import app.api.agendaFacil.business.genero.loader.GeneroLoader;
import app.api.agendaFacil.business.genero.repository.GeneroRepository;
import app.api.agendaFacil.business.genero.validator.GeneroValidator;
import app.core.application.DTO.Responses;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public abstract class GeneroManager {

    protected final GeneroRepository generoRepository;
    protected final GeneroValidator generoValidator;
    protected final GeneroLoader generoLoader;

    protected Responses responses;

    protected String query;

    protected GeneroManager(GeneroRepository generoRepository, GeneroValidator generoValidator, GeneroLoader generoLoader) {
        this.generoRepository = generoRepository;
        this.generoValidator = generoValidator;
        this.generoLoader = generoLoader;
    }

    protected GeneroManager() {
        this.generoRepository = null;
        this.generoValidator = null;
        this.generoLoader = null;
    }
}
