package taskmanagementsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import taskmanagementsystem.dto.task.TaskRequest;
import taskmanagementsystem.dto.task.TaskResponse;
import taskmanagementsystem.model.task.Task;
import taskmanagementsystem.model.user.User;


@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface TaskMapper {

    @Mapping(target = "author", source = "task.author.username")
    TaskResponse toTaskResponse(Task task);

    default Page<TaskResponse> toTaskResponsePage(Page<Task> tasks) {
        return tasks.map(this::toTaskResponse);
    }

    @Mapping(target = "author", ignore = true)
    Task toEntity(TaskRequest taskRequest);

    TaskRequest toTaskRequest(Task task);

    // Явное определение метода для преобразования User в String (username)
    default String map(User user) {
        return user != null ? user.getUsername() : null;
    }
}
