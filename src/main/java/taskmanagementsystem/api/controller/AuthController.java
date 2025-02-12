package taskmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import taskmanagementsystem.api.AuthApi;
import taskmanagementsystem.dto.auth.JwtRequest;
import taskmanagementsystem.dto.auth.JwtResponse;
import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.service.AuthService;
import taskmanagementsystem.service.UserService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final UserService userService;


    public ResponseEntity<JwtResponse> login(final JwtRequest loginRequest) {
        log.info("AuthController.login");
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    public ResponseEntity<UserResponse> register(final UserRequest userDto) {
        log.info("AuthController.register");
        return ResponseEntity.ok(userService.create(userDto));
    }

    public ResponseEntity<JwtResponse> refresh(final String refreshToken) {
        log.info("AuthController.refresh");
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

}
