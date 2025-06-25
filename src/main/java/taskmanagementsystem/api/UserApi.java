package taskmanagementsystem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;

@RestController
@RequestMapping("/api/v1/users/")
@Tag(name = "User Controller", description = "User API")
public interface UserApi {
    @Operation(summary = "Обновление user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные user обновлены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))

    })
    @PreAuthorize("hasRole('ADMIN') or #dto.username.equals(authentication.name)")
    @PutMapping("{id}")
    ResponseEntity<UserResponse> update(@PathVariable Long id, @Validated @RequestBody UserRequest dto);

    @GetMapping("me")
    Authentication me(Authentication authentication);
    @Operation(summary = "Получить user по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные user получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))

    })

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    ResponseEntity<UserResponse> getById(@PathVariable Long id);

    @Operation(summary = "Удаление user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные user удалены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))

    })

    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.isOwner(authentication.name, #id)")
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteById(@PathVariable final Long id);


}
