package app.api.agendaFacil.management.timeZone.entity;

import app.api.agendaFacil.management.timeZone.DTO.TimeZoneDTO;
import app.core.application.entity.EntityBase;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Entity
@Table(name = "timezone", schema = "config")
public class TimeZone extends EntityBase {

    @Column()
    @SequenceGenerator(name = "timezoneIdSequence", sequenceName = "timezone_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column()
    private String timeZoneId;
    @Column()
    private String timeZoneOffset;

    public TimeZone() {
        super();
    }

    public TimeZone(String timeZoneId, String timeZoneOffset) {
        super();
        this.timeZoneId = timeZoneId;
        this.timeZoneOffset = timeZoneOffset;
    }

    public TimeZone(TimeZoneDTO timeZone) {
        super();
        this.id = timeZone.getId();
        this.timeZoneId = timeZone.getTimeZoneId();
        this.timeZoneOffset = timeZone.getTimeZoneOffset();
    }


    @Inject
    protected TimeZone(SecurityContext context) {
        super(context);
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public String getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
