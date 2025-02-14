package app.core.controllers.auth;

import app.api.agendaFacil.management.auth.DTO.AuthDTO;
import app.core.entities.context.AuthContextTest;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.FORBIDDEN;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
class RecoveryPasswordControllerTest extends AuthContextManager {

    private String passwordToken;

    public RecoveryPasswordControllerTest() {
        super();
    }

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/recoverPassword/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
        removeAuthTokens(defaultContext);
    }

    @Test
    @Order(1)
    void sendMail() {
        doSendMail(defaultContext);
    }

    @Test
    @Order(2)
    void sendEmailAndUpdate() {
        doSendMail(defaultContext);
        doUpdate(defaultContext);
    }


    @Test
    @Order(3)
    void returnCryptPassword() {
        doCryptPassword(defaultContext);
    }

    void doSendMail(AuthContextTest context) {
        passwordToken = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .pathParam("login", "iedio")
                .when()
                .post("{login}")
                .then()
                .statusCode(OK)
                .extract()
                .path("data.token");
    }

    private void doUpdate(AuthContextTest context) {

        authDTO = new AuthDTO();
        authDTO.setToken(passwordToken);
        authDTO.setPassword(context.getPassword());

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .body(context.objectToJson(authDTO))
                .when()
                .put()
                .then()
                .statusCode(OK);
    }


    void doCryptPassword(AuthContextTest context) {
        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("password", context.getPassword())
                .when()
                .post("crypt")
                .then()
                .statusCode(FORBIDDEN);
    }
}