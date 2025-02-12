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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController implements TaskApi {

    private final TaskService taskService;


    @Override
    public ResponseEntity<TaskResponse> update(Long id,TaskRequest dto,Authentication authentication) {
        return ResponseEntity.ok(taskService.updateTask(id, dto,authentication));
    }

    @Override
    public ResponseEntity<TaskResponse> getById(Long id,Authentication authentication) {
        return ResponseEntity.ok(taskService.getTaskById(id,authentication));
    }

    @Override
    public ResponseEntity<Page<TaskResponse>> getAllTask(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<TaskResponse> taskPage = taskService.getAll(pageable, authentication);

        return ResponseEntity.ok(taskPage);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id,Authentication authentication) {
        taskService.delete(id, authentication);
        return ResponseEntity.noContent().build();
    }
}
