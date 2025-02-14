package app.api.agendaFacil.management.timeZone.DTO;

import app.api.agendaFacil.management.timeZone.entity.TimeZone;

public class TimeZoneDTO {

    private Long id;

    private String timeZoneId;

    private String timeZoneOffset;

    public TimeZoneDTO() {

    }

    public TimeZoneDTO(String timeZoneId, String timeZoneOffset) {
        this.timeZoneId = timeZoneId;
        this.timeZoneOffset = timeZoneOffset;
    }


    public TimeZoneDTO(TimeZone timeZone) {
        this.id = timeZone.getId();
        this.timeZoneId = timeZone.getTimeZoneId();
        this.timeZoneOffset = timeZone.getTimeZoneOffset();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(String timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }
}