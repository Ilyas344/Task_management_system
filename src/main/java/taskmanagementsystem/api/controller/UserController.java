package taskmanagementsystem.api.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taskmanagementsystem.api.UserApi;
import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.service.UserService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {

    private final UserService userService;


    public ResponseEntity<UserResponse> update(final Long id, final UserRequest dto) {
        log.info("UserController.update");
        return ResponseEntity.ok(userService.update(id, dto));
    }

    public Authentication me(Authentication authentication) {
        return authentication;
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
