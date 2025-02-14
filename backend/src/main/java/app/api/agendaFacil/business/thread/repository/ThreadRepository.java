package app.api.agendaFacil.business.thread.repository;

import app.api.agendaFacil.business.thread.entity.Thread;
import app.core.application.persistence.PersistenceRepository;
import jakarta.enterprise.context.RequestScoped;

import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
public class ThreadRepository extends PersistenceRepository<Thread> {

    public ThreadRepository() {
        super();
    }

    public static Thread findById(Long id) {
        return Thread.findById(id);
    }

    public static Thread findByStatus(Long status) {
        return Thread.find("status = ?1", status).firstResult();
    }

    public static List<Thread> listByStatusAndData(Long status, LocalDateTime data) {
        return Thread.list("status = ?1 and dataAcao < ?2", status, data);
    }

    public static Long countByStatus(Long status) {
        return Thread.count("status = ?1", status);
    }

}
