package app.core.controllers.liquibase;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.core.entities.context.AuthContextTest;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
class LiquibaseControllerTest extends AuthContextManager {

    ContratoDTO newEntity;

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/liquibase/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
        removeAuthTokens(defaultContext);
    }

    @Test
    void createMigrations() {

        newEntity = new ContratoDTO();
        newEntity.setTenant("base");

        copyCreateAndExecuteMigrationsByContrato(defaultContext, newEntity);
    }

    @Test
    void copyMigrations() {
        copyNewMigrationsAndRun(defaultContext);
    }

    @Test
    void executeMigrations() {
        executeMigration(defaultContext);
    }

    void copyCreateAndExecuteMigrationsByContrato(AuthContextTest context, ContratoDTO entity) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .body(context.objectToJson(entity))
                .when()
                .post("/create")
                .then()
                .statusCode(OK);
    }

    void copyNewMigrationsAndRun(AuthContextTest context) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .when()
                .post("/copy")
                .then()
                .statusCode(OK);
    }

    void executeMigration(AuthContextTest context) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .queryParam("dropAll", Boolean.FALSE)
                .queryParam("validate", Boolean.TRUE)
                .queryParam("update", Boolean.FALSE)
                .when()
                .post("/execute")
                .then()
                .statusCode(OK);
    }
}