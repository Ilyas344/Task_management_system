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
import org.springframework.web.bind.annotation.*;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;


/**
 * @author Ilyas
 */
@RequestMapping("/api/v1/tasks/")
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

    @PostMapping()
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

    @PreAuthorize("hasRole('ADMIN') or authentication.name == @taskServiceImpl.getEmailByTask(#id)")
    @PutMapping("{id}")
    ResponseEntity<TaskResponse> update(@PathVariable Long id,
                                        @RequestBody TaskRequest dto,
                                        @RequestBody Authentication authentication);

    @Operation(summary = "Получить Task по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные task получены",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class)))

    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
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

    @GetMapping
    ResponseEntity<Page<TaskResponse>> getAllTask(
            @RequestParam(defaultValue = "0") int page,
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

    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and authentication.name == @taskServiceImpl.getEmailByTask(#id))")
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteById(@PathVariable final Long id);

}
