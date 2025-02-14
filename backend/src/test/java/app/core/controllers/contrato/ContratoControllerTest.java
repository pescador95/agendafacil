package app.core.controllers.contrato;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.api.agendaFacil.business.contrato.entity.TipoContrato;
import app.core.entities.context.AuthContextTest;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
class ContratoControllerTest extends AuthContextManager {

    ContratoDTO newEntity;

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/contrato/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
        removeAuthTokens(defaultContext);
    }

    @Test
    @Order(1)
    void getById() {
        createNewEntity();

        createEntity(defaultContext, newEntity);

        getEntityById(defaultContext, newEntity.getId());

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(2)
    void count() {
        countEntity(defaultContext);
    }

    @Test
    @Order(3)
    void list() {
        listEntity(defaultContext);
    }

    @Test
    @Order(4)
    void add() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(5)
    void update() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        updateNewEntity();

        updateEntity(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(6)
    void deleteByIds() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(7)
    void reactivateByIds() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);

        reactivateEntity(defaultContext, reactivatedIds);

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

    void countEntity(AuthContextTest context) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .when()
                .get("count")
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

    void createEntity(AuthContextTest context, ContratoDTO entity) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .post();

        response.then()
                .statusCode(CREATED);

        JsonPath jsonPath = response.jsonPath();

        newEntity = jsonPath.getObject("data", ContratoDTO.class);

        deletedIds.add(newEntity.getId());
    }

    void updateEntity(AuthContextTest context, ContratoDTO entity) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(entity))
                .when()
                .put();

        response.then()
                .statusCode(OK);

        JsonPath jsonPath = response.jsonPath();

        newEntity = jsonPath.getObject("data", ContratoDTO.class);

        deletedIds.add(newEntity.getId());
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

    void reactivateEntity(AuthContextTest context, Set<Long> ids) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(ids))
                .when()
                .put("/reactivate");

        response.then()
                .statusCode(OK);
    }

    void createNewEntity() {
        newEntity = new ContratoDTO();
        newEntity.setTenant("foo");
        newEntity.setConsideracoes("Contrato renovado automaticamente ao término.");
        newEntity.setDataContrato(LocalDate.now());
        newEntity.setDataTerminoContrato(LocalDate.now().plusYears(1));
        newEntity.setNumeroMaximoSessoes(10);
        newEntity.setTipoContrato(TipoContrato.SESSAO_UNICA);
    }

    void updateNewEntity() {
        newEntity.setDataTerminoContrato(LocalDate.now().plusYears(2));
        newEntity.setTipoContrato(TipoContrato.SESSAO_COMPARTILHADA);
    }
}