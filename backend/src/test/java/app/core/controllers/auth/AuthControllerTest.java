package app.core.controllers.auth;

import app.core.entities.context.AuthContextTest;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.FORBIDDEN;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
public class AuthControllerTest extends AuthContextManager {

    public AuthControllerTest() {
        super();
    }

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/auth/";
    }

    @Test
    @Order(1)
    void auth() {
        getAccessToken(defaultContext);
    }

    @Test
    @Order(2)
    void refreshToken() {
        getRefreshToken(defaultContext);
    }

    @Test
    @Order(3)
    void logout() {
        getAccessToken(defaultContext);
        doLogout(defaultContext);
    }

    @Test
    @Order(4)
    void flush() {
        doFlush(defaultContext);
    }

    public void getAccessToken(AuthContextTest context) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .body(context.getRequestAuth())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .when()
                .post();

        response.then()
                .statusCode(OK);

        JsonPath jsonPath = response.jsonPath();

        accessToken = jsonPath.getString("data.accessToken");
        refreshToken = jsonPath.getString("data.refreshToken");

        context.setAccessToken(accessToken);
        context.setRefreshToken(refreshToken);
    }


    private void getRefreshToken(AuthContextTest context) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .body(context.getRefreshTokenToJson())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .when()
                .post("refresh");

        response.then()
                .statusCode(OK);

        JsonPath jsonPath = response.jsonPath();

        accessToken = jsonPath.getString("data.accessToken");
        refreshToken = jsonPath.getString("data.refreshToken");

        context.setAccessToken(accessToken);
        context.setRefreshToken(refreshToken);
    }


    void doLogout(AuthContextTest context) {
        given().baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .when()
                .post("logout").then()
                .statusCode((OK));
    }


    void doFlush(AuthContextTest context) {
        given().baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .when()
                .post("flush").then()
                .statusCode(FORBIDDEN);
    }
}