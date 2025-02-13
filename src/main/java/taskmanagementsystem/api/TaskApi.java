package taskmanagementsystem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;


@RequestMapping("/api/v1/")
@Validated
@Tag(name = "Task Controller", description = "Task  API")
public interface TaskApi {
    @Operation(summary = "Создание task ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task создан",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)))

    })
    @PostMapping("tasks")
    ResponseEntity<TaskResponse> create(@RequestBody TaskRequest dto);

    @Operation(summary = "Обновление task ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные task  обновлены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)))

    })
    @PutMapping("tasks")
    ResponseEntity<TaskResponse> update( Long id,@RequestBody TaskRequest dto,Authentication authentication);

    @Operation(summary = "Получить Task по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные task получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)))

    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("task/{id}")
    ResponseEntity<TaskResponse> getById(@PathVariable Long id,Authentication authentication);

    @Operation(summary = "Получить все Task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные task получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)))

    })
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("task/")
    ResponseEntity<Page<TaskResponse>> getAllTask(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication);

    @Operation(summary = "Удаление Task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные Task удалены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)))

    })

    @DeleteMapping("tasks/{id}")
    ResponseEntity<Void> deleteById(@PathVariable final Long id,  Authentication authentication);


}
