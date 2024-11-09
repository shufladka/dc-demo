package by.bsuir.taskjpa.service;

import by.bsuir.taskjpa.model.dto.request.UserRequestTo;
import by.bsuir.taskjpa.model.dto.response.UserResponseTo;

import java.util.List;

public interface UserService {
    UserResponseTo save(UserRequestTo entity);
    List<UserResponseTo> findAll();
    UserResponseTo findById(Long id);
    UserResponseTo update(UserRequestTo updateRequest);
    void delete(Long id);
}
