package app.agendamento.controllers.pessoa;

import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
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
class PessoaControllerTest extends AuthContextManager {

    EntidadeDTO newEntity;

    @BeforeEach
    void setUp() {
        getAuthTokens(defaultContext);
        RestAssured.basePath = "/pessoa/";
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

    @Test
    @Order(8)
    void listByCPF() {
        String cpf = "08899900015";
        getEntityByCPF(defaultContext, cpf);
    }

    @Test
    @Order(9)
    void listByPhone() {
        String phone = "45991000000";
        getEntityByPhone(defaultContext, phone);
    }

    @Test
    @Order(10)
    void listByIdent() {
        String ident = "45991000000";
        getEntityByIdent(defaultContext, ident);
    }

    @Test
    @Order(11)
    void getByTelegram() {
        String telegram = "";
        getEntityByTelegram(defaultContext, telegram);
    }

    @Test
    @Order(12)
    void getByWhatsapp() {
        String whatsappId = "";
        getEntityByWhatsapp(defaultContext, whatsappId);
    }

    @Test
    @Order(13)
    void addByBot() {
    }

    @Test
    @Order(14)
    void updateByTelegram() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        updateEntityByTelegram(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(15)
    void updateByWhatsapp() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        updateEntityByWhatsapp(defaultContext, newEntity);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(16)
    void removeByTelegram() {
        String telegramId = "1234567890";
        removeTelegramId(defaultContext, telegramId);
    }

    @Test
    @Order(17)
    void removeByWhatsapp() {
        String whatsappId = "1234567890";
        removeWhatsappId(defaultContext, whatsappId);
    }

    @Test
    @Order(18)
    void configureNotificationByTelegram() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        enableTelegramNotification(defaultContext, newEntity, Boolean.TRUE, Boolean.TRUE);

        deleteEntity(defaultContext, deletedIds);
    }

    @Test
    @Order(19)
    void configureNotificationByWhatsapp() {

        createNewEntity();

        createEntity(defaultContext, newEntity);

        enableWhatsappNotification(defaultContext, newEntity, Boolean.TRUE, Boolean.TRUE);

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

    void createEntity(AuthContextTest context, EntidadeDTO entity) {

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

        newEntity = jsonPath.getObject("data", EntidadeDTO.class);

        deletedIds.add(newEntity.getId());
    }

    void updateEntity(AuthContextTest context, EntidadeDTO entity) {

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

        newEntity = jsonPath.getObject("data", EntidadeDTO.class);

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

        deletedIds.addAll(ids);
    }

    void getEntityByIdent(AuthContextTest context, String ident) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("ident", ident)
                .when()
                .get("ident")
                .then()
                .statusCode(OK);
    }

    void getEntityByCPF(AuthContextTest context, String cpf) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("cpf", cpf)
                .when()
                .get("cpf")
                .then()
                .statusCode(OK);
    }

    void getEntityByPhone(AuthContextTest context, String phone) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("phone", phone)
                .when()
                .get("phone")
                .then()
                .statusCode(OK);
    }

    void getEntityByTelegram(AuthContextTest context, String telegram) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("telegram", telegram)
                .when()
                .get("telegram")
                .then()
                .statusCode(OK);
    }

    void getEntityByWhatsapp(AuthContextTest context, String whatsapp) {

        given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("whatsapp", whatsapp)
                .when()
                .get("whatsapp")
                .then()
                .statusCode(OK);
    }

    void removeTelegramId(AuthContextTest context, String telegram) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("telegram", telegram)
                .when()
                .delete("telegram");

        response.then()
                .statusCode(OK);

    }

    void removeWhatsappId(AuthContextTest context, String whatsapp) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("whatsapp", whatsapp)
                .when()
                .delete("whatsapp");

        response.then()
                .statusCode(OK);
    }

    void updateEntityByWhatsapp(AuthContextTest context, EntidadeDTO entity) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("whatsappId", entity.getWhatsappId())
                .body(context.objectToJson(entity))
                .when()
                .post("whatsapp");

        response.then()
                .statusCode(OK);

        JsonPath jsonPath = response.jsonPath();

        newEntity = jsonPath.getObject("data", EntidadeDTO.class);
    }

    void updateEntityByTelegram(AuthContextTest context, EntidadeDTO entity) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("telegramId", entity.getTelegramId())
                .body(context.objectToJson(entity))
                .when()
                .post("telegram");

        response.then()
                .statusCode(OK);

        JsonPath jsonPath = response.jsonPath();

        newEntity = jsonPath.getObject("data", EntidadeDTO.class);
    }

    void enableTelegramNotification(AuthContextTest context, EntidadeDTO entity, Boolean ativo, Boolean enabled) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("enable", enabled)
                .queryParam("ativo", ativo)
                .body(context.objectToJson(entity))
                .when()
                .post("telegram/{enable}", enabled);

        response.then()
                .statusCode(OK);
    }

    void enableWhatsappNotification(AuthContextTest context, EntidadeDTO entity, Boolean ativo, Boolean enabled) {

        Response response = given()
                .baseUri(context.getBaseUrl())
                .contentType(context.getContentTypeJson())
                .header(context.getAuthorizationHeaderKey(), context.getAuthorizationHeaderValue())
                .header(context.getTenantHeaderKey(), context.getTenant())
                .queryParam("ativo", ativo)
                .body(context.objectToJson(entity))
                .when()
                .post("whatsapp/{enable}", enabled);

        response.then()
                .statusCode(OK);
    }

    void createNewEntity() {
        newEntity = new EntidadeDTO();
        newEntity.setTenant("test");
        newEntity.setNome("Allah");
        newEntity.setCelular("45991000000");
        newEntity.setDataNascimento(LocalDate.now().minusYears(20));
        newEntity.setEmail("email@email.com");
        newEntity.setTelegramId(1234567890L);
        newEntity.setRecebeNotifacaoTelegram(true);
        newEntity.setRecebeNotifacaoWhatsapp(true);
        newEntity.setWhatsappId(1234567890L);
        newEntity.setCpf("08899900015");
    }

    void updateNewEntity() {
        newEntity.setNome("Joseph");
    }
}