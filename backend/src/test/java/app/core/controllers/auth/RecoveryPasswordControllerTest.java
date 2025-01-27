package app.core.controllers.auth;

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
                .contentType("application/json")
                .header("X-Tenant", context.getTenant())
                .pathParam("login", "iedio")
                .when()
                .post("{login}")
                .then()
                .statusCode(OK)
                .extract()
                .path("data.token");
    }

    private void doUpdate(AuthContextTest context) {

        String tokenJson = context.attributeToJson("token", context.getAccessToken());
        String passwordJson = context.attributeToJson("password", context.getPassword());

        String requestBody = tokenJson.substring(0, tokenJson.length() - 1) + "," + passwordJson.substring(1);

        given()
                .baseUri(context.getBaseUrl())
                .contentType("application/json")
                .header("Authorization", "Bearer " + context.getAccessToken())
                .header("X-Tenant", context.getTenant())
                .queryParam("passToken", passwordToken)
                .body(requestBody)
                .when()
                .put()
                .then()
                .statusCode(OK);
    }


    void doCryptPassword(AuthContextTest context) {
        given()
                .baseUri(context.getBaseUrl())
                .contentType("application/json")
                .header("Authorization", "Bearer " + context.getAccessToken())
                .header("X-Tenant", context.getTenant())
                .queryParam("password", context.getPassword())
                .when()
                .post("crypt")
                .then()
                .statusCode(FORBIDDEN);
    }
}