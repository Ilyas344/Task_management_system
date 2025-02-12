package taskmanagementsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;

public interface TaskService {
    Page<TaskResponse> getAll(Pageable pageable, Authentication authentication);

    TaskResponse getTaskById(Long id, Authentication authentication) ;

    TaskResponse createTask(TaskRequest taskDto);

    TaskResponse updateTask(Long id, TaskRequest taskDto, Authentication authentication);

    void delete(Long id, Authentication authentication);
}
