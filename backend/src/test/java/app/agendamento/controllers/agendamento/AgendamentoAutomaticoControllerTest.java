package app.agendamento.controllers.agendamento;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.core.entities.context.AuthContextTest;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
class AgendamentoAutomaticoControllerTest extends AuthContextManager {

    AgendamentoDTO newEntity;
    AgendamentoDTO updateNewEntity;
    List<AgendamentoDTO> reschedules = new ArrayList<>();

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/agendamento/bot/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
        removeAuthTokens(defaultContext);
    }

    @Test
    @Order(1)
    void listFreeAppointmentByUsuario() {

        createNewEntity();

        listAppointmentsByEntity(defaultContext, newEntity);
    }

    @Test
    @Order(2)
    void listAppointmentsByPessoa() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        listAppointmentsCustomerEntity(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(3)
    void schedule() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(4)
    void reschedule() {

        createNewEntity();

        updateNewEntity();

        createEntity(defaultContext, newEntity);

        updateEntity(defaultContext, reschedules);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(5)
    void checkDate() {

        createNewEntity();

        checkDateByEntity(defaultContext, newEntity);
    }

    void listAppointmentsByEntity(AuthContextTest context, AgendamentoDTO entity) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .post("listar")
                .then()
                .statusCode(OK);
    }

    void listAppointmentsCustomerEntity(AuthContextTest context, AgendamentoDTO entity) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .post("listar/meusAgendamentos")
                .then()
                .statusCode(OK);
    }

    void createEntity(AuthContextTest context, AgendamentoDTO entity) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .post("marcar");

        response.then()
                .statusCode(CREATED);

        JsonPath jsonPath = response.jsonPath();


        newEntity = jsonPath.getObject("data", AgendamentoDTO.class);

        if (BasicFunctions.isNotEmpty(newEntity)) {

            deletedIds.add(newEntity.getId());

            reschedules.add(newEntity);
        }
    }

    void updateEntity(AuthContextTest context, List<AgendamentoDTO> entities) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entities))
                .when()
                .post("remarcar");

        response.then()
                .statusCode(CREATED);

        JsonPath jsonPath = response.jsonPath();

        newEntity = jsonPath.getObject("data", AgendamentoDTO.class);

        if (BasicFunctions.isNotEmpty(newEntity)) {

            deletedIds.add(newEntity.getId());

            reschedules.add(newEntity);
        }
    }

    void checkDateByEntity(AuthContextTest context, AgendamentoDTO entity) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .post("verificarData")
                .then()
                .statusCode(OK);
    }

    void deleteEntity(AuthContextTest context, Set<Long> ids) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(ids))
                .when()
                .delete();

        response.then()
                .statusCode(OK);

        reactivatedIds.addAll(ids);

    }

    void createNewEntity() {
        newEntity = new AgendamentoDTO();
        newEntity.setDataAgendamento(Contexto.proximaDataByIntervalo(1, DayOfWeek.TUESDAY));
        newEntity.setHorarioAgendamento(LocalTime.of(13, 00));
        newEntity.setOrganizacaoAgendamento(new OrganizacaoDTO(1L));
        newEntity.setPessoaAgendamento(new PessoaDTO(7L));
        newEntity.setProfissionalAgendamento(new UsuarioDTO(2L));
        newEntity.setTipoAgendamento(new TipoAgendamentoDTO(1L));
    }

    void updateNewEntity() {
        updateNewEntity = new AgendamentoDTO();
        updateNewEntity.setDataAgendamento(Contexto.proximaDataByIntervalo(1, DayOfWeek.TUESDAY));
        updateNewEntity.setHorarioAgendamento(LocalTime.of(14, 00));
        updateNewEntity.setOrganizacaoAgendamento(new OrganizacaoDTO(1L));
        updateNewEntity.setPessoaAgendamento(new PessoaDTO(7L));
        updateNewEntity.setProfissionalAgendamento(new UsuarioDTO(2L));
        updateNewEntity.setTipoAgendamento(new TipoAgendamentoDTO(1L));
        reschedules.add(updateNewEntity);
    }
}