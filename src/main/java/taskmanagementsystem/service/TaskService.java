package taskmanagementsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;

/**
 * @author Ilyas
 */
public interface TaskService {
    Page<TaskResponse> getAll(Pageable pageable, final Authentication authentication);

    TaskResponse getTaskById(Long id);

    TaskResponse createTask(TaskRequest taskDto);

    String getEmailByTask(Long id);

    TaskResponse updateTask(Long id, TaskRequest taskDto, Authentication authentication);

    void delete(Long id);
}
