package taskmanagementsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;
import taskmanagementsystem.mappers.TaskMapper;
import taskmanagementsystem.model.exception.AccessDeniedException;
import taskmanagementsystem.model.exception.TaskNotFoundException;
import taskmanagementsystem.model.task.Task;
import taskmanagementsystem.model.user.RoleEnum;
import taskmanagementsystem.repository.TaskRepository;
import taskmanagementsystem.repository.UserRepository;
import taskmanagementsystem.service.TaskService;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;


    public Page<TaskResponse> getAll(Pageable pageable, Authentication authentication) {
        String currentUser = authentication.getName();
        RoleEnum userRole = getCurrentUserRole(authentication);

        Page<Task> tasks;
        if (userRole == RoleEnum.ROLE_ADMIN || userRole == RoleEnum.ROLE_MODERATOR) {
            tasks = taskRepository.findAll(pageable);
        } else {
            tasks = taskRepository.findByAuthorUsername(currentUser, pageable);
        }
        return taskMapper.toTaskResponsePage(tasks);
    }

    public TaskResponse getTaskById(Long id, Authentication authentication) {
        String currentUser = authentication.getName();
        RoleEnum userRole = getCurrentUserRole(authentication);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        if (!(userRole == RoleEnum.ROLE_ADMIN || userRole == RoleEnum.ROLE_MODERATOR) && !task.getAuthor().getUsername().equals(currentUser)) {
            throw new AccessDeniedException("You do not have permission to access this task");
        }
        return taskMapper.toTaskResponse(task);
    }


    public TaskResponse createTask(TaskRequest taskDto) {
        Task task = taskMapper.toEntity(taskDto);

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(Long id, TaskRequest taskDto, Authentication authentication) {
        String currentUser = authentication.getName();
        RoleEnum userRole = getCurrentUserRole(authentication);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        if (!(userRole == RoleEnum.ROLE_ADMIN || userRole == RoleEnum.ROLE_MODERATOR) && !task.getAuthor().getUsername().equals(currentUser)) {
            throw new AccessDeniedException("You do not have permission to update this task");
        }

        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());
        task.setPriority(taskDto.priority());
        task.setAuthor(userRepository.findByUsername(taskDto.author()).orElseThrow());
        task.setAssignee(taskDto.assignee());
        task.setComments(new ArrayList<>(taskDto.comments()) {{
            addAll(task.getComments());
        }});

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    public void delete(Long id, Authentication authentication) {
        String currentUser = authentication.getName();
        RoleEnum userRole = getCurrentUserRole(authentication);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        // Проверяем права доступа
        if (!(userRole == RoleEnum.ROLE_ADMIN || userRole == RoleEnum.ROLE_MODERATOR) && !task.getAuthor().getUsername().equals(currentUser)) {
            throw new AccessDeniedException("You do not have permission to delete this task");
        }

        taskRepository.deleteById(id);
    }

    private RoleEnum getCurrentUserRole(Authentication authentication) {

        String roleString = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("User role not found"));

        return RoleEnum.valueOf(roleString.replace("ROLE_", ""));
    }

}




