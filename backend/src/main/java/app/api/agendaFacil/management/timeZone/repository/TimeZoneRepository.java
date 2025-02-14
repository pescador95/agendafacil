package app.api.agendaFacil.management.timeZone.repository;

import app.api.agendaFacil.management.timeZone.entity.TimeZone;
import app.core.application.persistence.PersistenceRepository;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class TimeZoneRepository extends PersistenceRepository<TimeZone> {

    public TimeZoneRepository() {
        super();
    }

    public static TimeZone findById(Long id) {
        return TimeZone.findById(id);
    }

    public static List<TimeZone> findById(List<Long> id) {
        return TimeZone.list("id in ?1", id);
    }

    public static TimeZone findByTimeZoneOffset(String timeZoneOffset) {
        return TimeZone.find("timeZoneOffset = ?1", timeZoneOffset).firstResult();
    }
}
