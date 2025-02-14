package app.api.agendaFacil.business.usuario.query;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@SuppressWarnings("unchecked")
@RequestScoped
@Transactional
public class UsuarioQueries {

    @PersistenceContext
    EntityManager entityManager;

    public List<Usuario> loadListUsuariosByOrganizacaoAndDataAgendamento(AgendamentoDTO pAgendamento) {
        try {
            Query query = entityManager.createNativeQuery(
                    "select distinct u.* from usuario u join tipoagendamentousuarios t on t.profissionalid = u.id join organizacao o on o.id = u.organizacaodefaultid left join configuradoragendamentoespecial c on c.profissionalid = u.id where t.tipoagendamentoid = :tipoAgendamentoId and ( (:organizacaoId = c.organizacaoid and :dataAgendamento between c.datainicio and c.datafim) or (:organizacaoId = u.organizacaodefaultid) ) and ( not exists ( select 1 from configuradoragendamentoespecial c2 where c2.profissionalid = u.id and :dataAgendamento between c2.datainicio and c2.datafim ) or exists ( select 1 from configuradoragendamentoespecial c3 where c3.organizacaoid = :organizacaoId and c3.profissionalid = u.id and :dataAgendamento between c3.datainicio and c3.datafim ) ) and u.id not in (SELECT c5.usuarioid FROM configuradorausencia c4 LEFT JOIN configuradorausenciausuario c5 ON c5.configuradorausenciaid = c4.id WHERE COALESCE(c4.datainicioausencia <= :dataAgendamento, true) AND COALESCE(c4.datafimausencia >= :dataAgendamento, true)) and u.ativo = true",
                    Usuario.class);
            query.setParameter("organizacaoId", pAgendamento.getOrganizacaoAgendamento().getId());
            query.setParameter("tipoAgendamentoId", pAgendamento.getTipoAgendamento().getId());
            query.setParameter("dataAgendamento", pAgendamento.getDataAgendamento());
            return (List<Usuario>) query.getResultList();

        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    public Usuario loadUsuarioByOrganizacaoAndProfissionalAndTipoAgendamento(AgendamentoDTO pAgendamento) {
        try {
            Query query = entityManager.createNativeQuery(
                    "select distinct u.* from usuario u join tipoagendamentousuarios t on t.profissionalid = u.id join organizacao o on o.id = u.organizacaodefaultid left join configuradoragendamentoespecial c on c.profissionalid = u.id where u.id = :profissionalId and t.tipoagendamentoid = :tipoAgendamentoId and ( (:organizacaoId = c.organizacaoid and :dataAgendamento between c.datainicio and c.datafim) or (:organizacaoId = u.organizacaodefaultid) ) and ( not exists ( select 1 from configuradoragendamentoespecial c2 where c2.profissionalid = u.id and :dataAgendamento between c2.datainicio and c2.datafim ) or exists ( select 1 from configuradoragendamentoespecial c3 where c3.organizacaoid = :organizacaoId and c3.profissionalid = u.id and :dataAgendamento between c3.datainicio and c3.datafim ) ) and not exists (SELECT 1 FROM configuradorausencia c4 LEFT JOIN configuradorausenciausuario c5 ON c5.configuradorausenciaid = c4.id WHERE c5.usuarioid in (:profissionalId) AND COALESCE(c4.datainicioausencia <= :dataAgendamento, true) AND COALESCE(c4.datafimausencia >= :dataAgendamento, true)) and u.ativo = true",
                    Usuario.class);
            query.setParameter("organizacaoId", pAgendamento.getOrganizacaoAgendamento().getId());
            query.setParameter("tipoAgendamentoId", pAgendamento.getTipoAgendamento().getId());
            query.setParameter("profissionalId", pAgendamento.getProfissionalAgendamento().getId());
            query.setParameter("dataAgendamento", pAgendamento.getDataAgendamento());
            return (Usuario) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }

    public Usuario loadUsuarioByLogin(String login) {
        try {
            Query query = entityManager.createNativeQuery(
                    "select distinct u.* from usuario u join pessoa p on p.id = u.pessoaid where u.login = :key or p.email = :key and u.ativo = true",
                    Usuario.class);
            query.setParameter("key", login.toLowerCase());
            return (Usuario) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            return null;
        }
    }
}