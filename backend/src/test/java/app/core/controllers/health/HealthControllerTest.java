package app.core.controllers.health;

import app.core.entities.context.AuthContextTest;
import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@QuarkusTest
class HealthControllerTest extends AuthContextManager {

    @BeforeEach
    void setUp() {
        RestAssured.basePath = "/health/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
    }

    @Test
    void health() {
        checkHealth(defaultContext);
    }

    void checkHealth(AuthContextTest context) {
        Response response = given()
                .baseUri(context.getBaseUrl())
                .when()
                .get();

        response.then()
                .statusCode(OK);

        System.out.println("I'm Alive :) \n " + response.asPrettyString());
    }
}