package app.api.agendaFacil.business.tipoAgendamento.manager;

import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.tipoAgendamento.loader.TipoAgendamentoLoader;
import app.api.agendaFacil.business.tipoAgendamento.repository.TipoAgendamentoRepository;
import app.api.agendaFacil.business.tipoAgendamento.validator.TipoAgendamentoValidator;
import app.core.application.DTO.Responses;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public abstract class TipoAgendamentoManager {

    protected final TipoAgendamentoRepository tipoAgendamentoRepository;
    protected final TipoAgendamentoValidator tipoAgendamentoValidator;
    protected final TipoAgendamentoLoader tipoAgendamentoLoader;
    protected final OrganizacaoLoader organizacaoLoader;

    protected Responses responses;
    protected String query;

    protected TipoAgendamentoManager(TipoAgendamentoRepository tipoAgendamentoRepository, TipoAgendamentoValidator tipoAgendamentoValidator, TipoAgendamentoLoader tipoAgendamentoLoader, OrganizacaoLoader organizacaoLoader) {
        this.tipoAgendamentoRepository = tipoAgendamentoRepository;
        this.tipoAgendamentoValidator = tipoAgendamentoValidator;
        this.tipoAgendamentoLoader = tipoAgendamentoLoader;
        this.organizacaoLoader = organizacaoLoader;
    }

    protected TipoAgendamentoManager() {
        this.tipoAgendamentoRepository = null;
        this.tipoAgendamentoValidator = null;
        this.tipoAgendamentoLoader = null;
        this.organizacaoLoader = null;
    }
}
