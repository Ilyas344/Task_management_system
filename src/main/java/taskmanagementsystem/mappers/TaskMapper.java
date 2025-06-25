package taskmanagementsystem.mappers;

import org.mapstruct.*;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", source = "comments")
    void updateTaskFromDto(TaskRequest taskRequest, @MappingTarget Task task);


    @Mapping(target = "author", source = "task.author.username")
    TaskResponse toTaskResponse(Task task);

    default Page<TaskResponse> toTaskResponsePage(Page<Task> tasks) {
        return tasks.map(this::toTaskResponse);
    }
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "comments", source = "comments")
    Task toEntity(TaskRequest dto);


    TaskRequest toTaskRequest(Task task);


    default String map(User user) {
        return user != null ? user.getUsername() : null;
    }
}
