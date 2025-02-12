package taskmanagementsystem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;


@RequestMapping("/api/v1/")
@Validated
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
    @PutMapping("users")
    ResponseEntity<UserResponse> update(@RequestBody UserRequest dto);

    @Operation(summary = "Получить user по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные user получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))

    })
    @GetMapping("users/{id}")
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

    @DeleteMapping("users/{id}")
    ResponseEntity<Void> deleteById(@PathVariable final Long id);


}
