package java.taskmanagementsystem.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.taskmanagementsystem.api.UserApi;
import java.taskmanagementsystem.dto.user.UserRequest;
import java.taskmanagementsystem.dto.user.UserResponse;
import java.taskmanagementsystem.service.UserService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;


    public ResponseEntity<UserResponse> update(final UserRequest dto) {
        log.info("UserController.update");
        return ResponseEntity.ok(userService.update(dto));
    }


    public ResponseEntity<UserResponse> getById(final Long id) {
        log.info("UserController.getById");
        return ResponseEntity.ok(userService.getById(id));
    }


    public ResponseEntity<Void> deleteById(final Long id) {
        log.info("UserController.deleteById");
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
