package java.taskmanagementsystem.service;


import java.taskmanagementsystem.dto.user.UserRequest;
import java.taskmanagementsystem.dto.user.UserResponse;
import java.taskmanagementsystem.model.user.User;

public interface UserService {

    UserResponse getById(Long id);

    User getUserById(Long id);

    UserResponse getByUsername(String username);

    User getByEmail(String username);

    UserResponse update(UserRequest user);

    UserResponse create(UserRequest user);

    void delete(Long id);

}
