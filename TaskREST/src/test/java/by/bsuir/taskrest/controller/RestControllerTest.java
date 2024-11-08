package by.bsuir.taskrest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import by.bsuir.taskrest.util.config.ServerConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class RestControllerTest<RQ, RS> {

    protected static final Random random = new Random();

    @Autowired
    private static ServerConfig serverConfig;

    @BeforeAll
    public static void setup() {

        if (serverConfig != null) {
            RestAssured.port = serverConfig.getPort();
            RestAssured.basePath = serverConfig.getServlet().getContextPath();
        } else {
            RestAssured.port = 24110;
            RestAssured.basePath = "/api/v1.0";
        }

        RestAssured.baseURI = "http://127.0.0.1";
    }

    @Test
    public void saveTest() {
        RQ request = getRequestTo();
        Response saveResponse = saveRequest(request);
        Long responseEntityId = getResponseId(saveResponse);

        saveResponse.then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

        deleteEntity(responseEntityId);
    }

    @Test
    public void findByIdTest() {
        RQ request = getRequestTo();
        Response saveResponse = saveRequest(request);
        Long responseEntityId = getResponseId(saveResponse);

        when()
                .get(getRequestsMappingPath() + "/" + responseEntityId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteEntity(responseEntityId);
    }

    @Test
    public void findAllTest() {
        List<RQ> requests = List.of(getRequestTo(), getRequestTo());
        List<Response> saveResponses = saveRequests(requests);

        when()
                .get(getRequestsMappingPath())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteEntities(getResponsesId(saveResponses));
    }

    @Test
    public void updateTest() {
        RQ request = getRequestTo();
        Response saveResponse = saveRequest(request);
        Long responseEntityId = getResponseId(saveResponse);
        RQ updateRequest = getUpdateRequestTo(request, responseEntityId);

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put(getRequestsMappingPath())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        deleteEntity(responseEntityId);
    }

    @Test
    public void deleteTest() {
        RQ request = getRequestTo();
        Response saveResponse = saveRequest(request);
        Long responseEntityId = getResponseId(saveResponse);

        deleteEntity(responseEntityId)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void findByDoesntExistId() {
        RQ request = getRequestTo();
        Response saveResponse = saveRequest(request);
        Long responseEntityId = getResponseId(saveResponse);

        when()
                .get(getRequestsMappingPath() + "/" + (responseEntityId + 1))
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

        deleteEntity(responseEntityId);
    }

    @Test
    public void updateDoesntExistId() {
        RQ request = getRequestTo();
        Response saveResponse = saveRequest(request);
        Long responseEntityId = getResponseId(saveResponse);
        RQ updateRequest = getUpdateRequestTo(request, responseEntityId + 1);

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put(getRequestsMappingPath())
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

        deleteEntity(responseEntityId);
    }

    @Test
    public void deleteDoesntExistId() {
        RQ request = getRequestTo();
        Response saveResponse = saveRequest(request);
        Long responseEntityId = getResponseId(saveResponse);

        deleteEntity(responseEntityId + 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

        deleteEntity(responseEntityId);
    }

    protected abstract RQ getRequestTo();
    protected abstract RQ getUpdateRequestTo(RQ originalRequest, Long updateEntityId);
    protected abstract String getRequestsMappingPath();

    protected Response saveRequest(RQ request) {
        return given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(getRequestsMappingPath());
    }

    protected List<Response> saveRequests(List<RQ> requests) {
        List<Response> saveResponses = new ArrayList<>();
        for (var request : requests) {
            saveResponses.add(saveRequest(request));
        }
        return saveResponses;
    }

    protected Long getResponseId(Response response) {
        return response.jsonPath().getLong("id");
    }

    protected List<Long> getResponsesId(List<Response> responses) {
        List<Long> responsesId = new ArrayList<>();
        for (var response : responses) {
            responsesId.add(getResponseId(response));
        }
        return responsesId;
    }

    protected Response deleteEntity(Long id) {
        return given()
                .delete(getRequestsMappingPath() + "/" + id);
    }

    protected List<Response> deleteEntities(List<Long> entitiesId) {
        List<Response> responses = new ArrayList<>();
        for (var entityId : entitiesId) {
            responses.add(deleteEntity(entityId));
        }
        return responses;
    }

    protected <FK> Response createForeignKeyEntity(FK entity, String controllerPath) {
        return given()
                .contentType(ContentType.JSON)
                .body(entity)
                .post(controllerPath);
    }

    protected static <FK> Response deleteForeignKeyEntity(Long id, String controllerPath) {
        return given()
                .delete(controllerPath + "/" + id);
    }
}