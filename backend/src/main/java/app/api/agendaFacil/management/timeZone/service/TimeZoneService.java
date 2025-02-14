package app.api.agendaFacil.management.timeZone.service;

import app.api.agendaFacil.management.timeZone.DTO.TimeZoneDTO;
import app.api.agendaFacil.management.timeZone.entity.TimeZone;
import app.api.agendaFacil.management.timeZone.repository.TimeZoneRepository;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
@Transactional
public class TimeZoneService {

    private TimeZone timeZone;

    private Responses responses;

    public Response list() {

        try {

            responses = new Responses();

            List<TimeZone> allTimeZones = ZoneId.getAvailableZoneIds().stream()
                    .sorted()
                    .map(zoneId -> {
                        ZoneId zone = ZoneId.of(zoneId);
                        String offset = zone.getRules().getOffset(Instant.now()).toString();
                        return new TimeZone(zoneId, offset);
                    })
                    .toList();

            Responses responses = new Responses();

            responses.setDatas(Collections.singletonList(toDTO(allTimeZones)));
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }


    public Response get(Long id) {

        try {

            responses = new Responses();

            TimeZone timeZone = TimeZoneRepository.findById(id);

            Responses responses = new Responses();

            responses.setData(new TimeZoneDTO(timeZone));
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response create(TimeZoneDTO pTimeZone) {

        try {

            responses = new Responses();

            TimeZone timeZone = TimeZoneRepository.findByTimeZoneOffset(pTimeZone.getTimeZoneOffset());

            if (timeZone != null) {
                timeZone = new TimeZone(pTimeZone);
                timeZone.persist();
                responses.setData(new TimeZoneDTO(timeZone));
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response createByList(List<TimeZoneDTO> timeZones) {

        try {

            List<TimeZone> newTimeZones = new ArrayList<>();
            List<TimeZone> allTimeZones = TimeZone.listAll();

            responses = new Responses();

            timeZones.forEach(timezone -> {
                if (!allTimeZones.contains(timezone)) {
                    newTimeZones.add(timeZone);
                }
            });

            if (BasicFunctions.isNotEmpty(newTimeZones)) {
                newTimeZones.forEach(timezone -> {
                    timezone.persist();
                });
            }

            Responses responses = new Responses();
            responses.setDatas(Collections.singletonList(toDTO(newTimeZones)));
            responses.setStatus(201);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        } finally {
            return Response.ok(responses).status(responses.getStatus()).build();
        }
    }


    public Response update(TimeZoneDTO pTimeZone) {

        try {

            responses = new Responses();

            timeZone = new TimeZone(pTimeZone);

            timeZone.persist();
            responses.setData(new TimeZoneDTO(timeZone));
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }


    public Response delete(List<Long> id) {

        try {

            responses = new Responses();

            List<TimeZone> timeZones = TimeZoneRepository.findById(id);

            int count = timeZones.size();

            if (BasicFunctions.isNotEmpty(timeZones)) {
                timeZones.forEach(timezone -> {
                    timezone.delete();
                });
            }
            responses.setMessages(Responses.DELETED, count);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private List<TimeZoneDTO> toDTO(List<TimeZone> entityList) {

        List<TimeZoneDTO> entityDTOList = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(entityList)) {
            entityList.forEach(entity -> {
                entityDTOList.add(new TimeZoneDTO(entity));
            });
        }
        return entityDTOList;
    }
}
