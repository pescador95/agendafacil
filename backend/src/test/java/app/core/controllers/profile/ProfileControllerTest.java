package app.core.controllers.profile;

import app.core.manager.AuthContextManager;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ProfileControllerTest extends AuthContextManager {

    /*
    TODO Reimplementar o upload e os testes
    */

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/profile/";
    }

    @AfterEach
    void tearDown() {
        RestAssured.basePath = "";
        removeAuthTokens(defaultContext);
    }

    @Test
    void count() {
    }

    @Test
    void listUploads() {
    }

    @Test
    void findOne() {
    }

    @Test
    void sendUpload() {
    }

    @Test
    void removeUpload() {
    }

    void createNewEntity() {

    }

    void updateNewEntity() {

    }
}