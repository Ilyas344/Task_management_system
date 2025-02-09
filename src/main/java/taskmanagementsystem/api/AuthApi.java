package java.taskmanagementsystem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.taskmanagementsystem.dto.auth.JwtRequest;
import java.taskmanagementsystem.dto.auth.JwtResponse;
import java.taskmanagementsystem.dto.user.UserRequest;
import java.taskmanagementsystem.dto.user.UserResponse;


@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Auth API")
public interface AuthApi {
    @Operation(summary = "Создание токена")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Токен создан",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)))

    })
    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@Validated @RequestBody JwtRequest loginRequest);

    @Operation(summary = "Регистрация user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User создан",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))

    })
    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody UserRequest userDto);

    @Operation(summary = "Создание рефреш токена")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Токен создан",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)))

    })
    @PostMapping("/refresh")
    ResponseEntity<JwtResponse> refresh(@RequestBody String refreshToken);
}
