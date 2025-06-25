package taskmanagementsystem.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import taskmanagementsystem.api.TaskApi;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;
import taskmanagementsystem.service.TaskService;

/**
 * @author Ilyas
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController implements TaskApi {

    private final TaskService taskService;


    @Override
    public ResponseEntity<TaskResponse> create(final TaskRequest dto) {
        log.info("TaskController.create");
        return ResponseEntity.ok(taskService.createTask(dto));
    }

    @Override
    public ResponseEntity<TaskResponse> update(final Long id,
                                               final TaskRequest dto,
                                               final Authentication authentication) {
        log.info("TaskController.update");
        return ResponseEntity.ok(taskService.updateTask(id, dto,authentication));
    }

    @Override
    public ResponseEntity<TaskResponse> getById(final Long id,
                                                final Authentication authentication) {
        log.info("TaskController.getById");
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Override
    public ResponseEntity<Page<TaskResponse>> getAllTask(
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam(defaultValue = "10") final int size,
            final Authentication authentication) {

        log.info("TaskController.getAllTask");
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<TaskResponse> taskPage = taskService.getAll(pageable, authentication);

        return ResponseEntity.ok(taskPage);
    }

    @Override
    public ResponseEntity<Void> deleteById(final Long id) {
        log.info("TaskController.deleteById");
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
