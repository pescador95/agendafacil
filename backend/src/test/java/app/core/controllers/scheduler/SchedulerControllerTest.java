package app.core.controllers.scheduler;

import app.core.entities.context.AuthContextTest;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
class SchedulerControllerTest extends AuthContextManager {

    private String message;
    private Long whatsppId;
    private Long telegramId;

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/scheduler/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
        removeAuthTokens(defaultContext);
    }

    @Test
    @Order(1)
    void enviarLembrete() {

        message = "hello! :)";
        whatsppId = 9999999L;
        telegramId = 999999L;

        sendMessage(defaultContext, message, whatsppId, telegramId);
    }

    @Test
    @Order(2)
    void execute() {
        executeScheduler(defaultContext);
    }

    void sendMessage(AuthContextTest context, String mensagem, Long whatsppId, Long telegramId) {


        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("mensagem", mensagem)
                .queryParam("whatsppId", whatsppId)
                .queryParam("telegramId", telegramId)
                .when()
                .post("enviarLembrete")
                .then()
                .statusCode(OK);
    }

    void executeScheduler(AuthContextTest context) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .when()
                .post("execute")
                .then()
                .statusCode(OK);
    }
}