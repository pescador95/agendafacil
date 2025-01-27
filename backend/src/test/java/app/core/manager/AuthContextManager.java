package app.core.manager;

import app.core.entities.context.AuthContextTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AuthContextManager {

    protected AuthContextTest defaultContext;
    protected String accessToken;
    protected String refreshToken;
    protected Set<Long> deletedIds = new HashSet<>();
    protected Set<Long> reactivatedIds = new HashSet<>();

    public AuthContextManager() {
        this.defaultContext = new AuthContextTest();
    }

    public AuthContextManager(AuthContextTest defaultContext) {
        this.defaultContext = defaultContext;
    }

    protected void getAuthTokens(AuthContextTest context) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType("application/json")
                .body(context.getRequestAuth())
                .header("X-Tenant", context.getTenant())
                .when()
                .post("/auth/");

        response.then()
                .statusCode(OK);

        JsonPath jsonPath = response.jsonPath();

        accessToken = jsonPath.getString("data.accessToken");
        refreshToken = jsonPath.getString("data.refreshToken");

        context.setAccessToken(accessToken);
        context.setRefreshToken(refreshToken);
    }

    protected void removeAuthTokens(AuthContextTest context) {

        given().baseUri(context.getBaseUrl())
                .contentType("application/json")
                .header("Authorization", "Bearer " + context.getAccessToken())
                .header("X-Tenant", context.getTenant())
                .when()
                .post("/auth/logout").then()
                .statusCode(OK);
    }

}
