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
class AutomaticLiquibaseControllerTest extends AuthContextManager {

    ContratoDTO newEntity;

    @BeforeEach
    void setUp() {
        RestAssured.basePath = "/automaticLiquibase/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
    }

    @Test
    void createAutomaticMigrations() {

        newEntity = new ContratoDTO();
        newEntity.setTenant("base");

        copyCreateAndExecuteMigrationsByContrato(defaultContext, newEntity);
    }

    @Test
    void copyAutomaticMigrations() {
        copyNewMigrationsAndRun(defaultContext);
    }

    @Test
    void executeAutomaticMigrations() {
        executeMigration(defaultContext);
    }

    void copyCreateAndExecuteMigrationsByContrato(AuthContextTest context, ContratoDTO entity) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .body(context.objectToJson(entity))
                .when()
                .post("/createAutomatic")
                .then()
                .statusCode(OK);
    }

    void copyNewMigrationsAndRun(AuthContextTest context) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .when()
                .post("/copy")
                .then()
                .statusCode(OK);
    }

    void executeMigration(AuthContextTest context) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .queryParam("dropAll", Boolean.FALSE)
                .queryParam("validate", Boolean.TRUE)
                .queryParam("update", Boolean.FALSE)
                .when()
                .post("/executeAutomatic")
                .then()
                .statusCode(OK);
    }
}