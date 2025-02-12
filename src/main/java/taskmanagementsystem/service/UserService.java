package taskmanagementsystem.service;


import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.model.user.User;

public interface UserService {

    UserResponse getById(Long id);

    User getUserById(Long id);

    UserResponse getByUsername(String username);

    User getByEmail(String username);

    UserResponse update(UserRequest user);

    UserResponse create(UserRequest user);

    void delete(Long id);

}
