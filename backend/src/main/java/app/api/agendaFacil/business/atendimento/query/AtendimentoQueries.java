package app.api.agendaFacil.business.atendimento.query;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class AtendimentoQueries {

    @PersistenceContext
    EntityManager entityManager;
}
