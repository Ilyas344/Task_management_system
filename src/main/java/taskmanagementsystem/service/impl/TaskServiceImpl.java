package taskmanagementsystem.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;
import taskmanagementsystem.mappers.TaskMapper;
import taskmanagementsystem.model.exception.TaskNotFoundException;
import taskmanagementsystem.model.task.Task;
import taskmanagementsystem.model.user.User;
import taskmanagementsystem.repository.TaskRepository;
import taskmanagementsystem.repository.UserRepository;
import taskmanagementsystem.service.TaskService;
import taskmanagementsystem.service.TaskSpecifications;

/**
 * @author Ilyas
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    public Page<TaskResponse> getAll(final Pageable pageable,
                                     final Authentication authentication) {
        Specification<Task> spec = TaskSpecifications.getTasksForUser(authentication);
        Page<Task> tasks = taskRepository.findAll(spec, pageable);
        return taskMapper.toTaskResponsePage(tasks);
    }

    @Override
    public TaskResponse getTaskById(final Long id) {
        return taskMapper.toTaskResponse(findTask(id));
    }

    @Override
    public TaskResponse createTask(final TaskRequest taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        User author = userRepository.findByUsername(taskDto.author())
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setAuthor(author);

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }


    @Override
    // @Cacheable(value = "taskEmail", key = "#taskId")
    public String getEmailByTask(Long taskId) {
        return taskRepository.findById(taskId)
                .map(task -> task.getAuthor().getEmail())
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
    }


    @Override
    public TaskResponse updateTask(final Long id,
                                   final TaskRequest taskDto,
                                   final Authentication authentication) {

        log.info("Updating task {} by user {}", id, authentication.getName());
        Task existingTask = findTask(id);
        taskMapper.updateTaskFromDto(taskDto, existingTask);
        Task savedTask = taskRepository.save(existingTask);
        log.info("Task {} successfully updated", id);
        return taskMapper.toTaskResponse(savedTask);
    }

    private Task findTask(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }


    @Override
    public void delete(Long id) {
        log.info("Delete task {} ", id);
        taskRepository.deleteById(id);
    }


}




