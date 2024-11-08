package by.bsuir.taskrest.controller;

import by.bsuir.taskrest.model.dto.request.UserRequestTo;
import by.bsuir.taskrest.model.dto.response.UserResponseTo;
import io.restassured.response.Response;

import java.util.List;

public class UserControllerTest extends RestControllerTest<UserRequestTo, UserResponseTo> {

    @Override
    protected UserRequestTo getRequestTo() {
        return new UserRequestTo(null,
                "login"     + random.nextInt(),
                "password"  + random.nextInt(),
                "firstname" + random.nextInt(),
                "lastname"  + random.nextInt());
    }

    @Override
    protected UserRequestTo getUpdateRequestTo(UserRequestTo originalRequest, Long updateEntityId) {
        return null;
    }

    @Override
    protected String getRequestsMappingPath() {
        return "";
    }
}
