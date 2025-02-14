package app.core.controllers.profile;

import app.api.agendaFacil.management.timeZone.DTO.TimeZoneDTO;
import app.core.entities.context.AuthContextTest;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.FORBIDDEN;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
class TimeZoneControllerTest extends AuthContextManager {

    TimeZoneDTO newEntity;

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/timezones/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
        removeAuthTokens(defaultContext);
    }


    @Test
    @Order(1)
    void getById() {
        getEntityById(defaultContext, 1L);
    }

    @Test
    @Order(2)
    void list() {
        listEntity(defaultContext);
    }

    @Test
    @Order(3)
    void add() {

        createNewEntity();

        createEntity(defaultContext, newEntity);
    }

    @Test
    @Order(4)
    void update() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        updateNewEntity();

        updateEntity(defaultContext, newEntity);
    }

    @Test
    @Order(5)
    void createByList() {

        Set<TimeZoneDTO> payload = new HashSet<>();

        payload.add(new TimeZoneDTO("createdTest1", "createdTest1"));
        payload.add(new TimeZoneDTO("createdTest2", "createdTest2"));

        createEntitiesByList(defaultContext, payload);
    }

    @Test
    @Order(6)
    void deleteByIds() {

        deletedIds.add(999L);

        deleteEntity(defaultContext, deletedIds);
    }

    void getEntityById(AuthContextTest context, Long id) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .pathParam("id", id)
                .when()
                .get("{id}")
                .then()
                .statusCode(OK);
    }

    void listEntity(AuthContextTest context) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .when()
                .get()
                .then()
                .statusCode(OK);
    }

    void createEntity(AuthContextTest context, TimeZoneDTO entity) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .post();

        response.then()
                .statusCode(FORBIDDEN);
    }

    void updateEntity(AuthContextTest context, TimeZoneDTO entity) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .put();

        response.then()
                .statusCode(FORBIDDEN);
    }

    void createEntitiesByList(AuthContextTest context, Set<TimeZoneDTO> entities) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.listObjectToJson(new ArrayList<>(entities)))
                .when()
                .post("list");

        response.then()
                .statusCode(FORBIDDEN);
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
                .statusCode(FORBIDDEN);
    }

    void createNewEntity() {
        newEntity = new TimeZoneDTO();
        newEntity.setTimeZoneId("createdTest");
        newEntity.setTimeZoneOffset("createdTest");
    }

    void updateNewEntity() {
        newEntity.setTimeZoneId("newUpdatedTest");
        newEntity.setTimeZoneOffset("newUpdatedTest");
    }
}