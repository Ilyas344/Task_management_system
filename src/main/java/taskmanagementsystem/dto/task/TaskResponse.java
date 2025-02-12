package taskmanagementsystem.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import taskmanagementsystem.model.task.Priority;
import taskmanagementsystem.model.task.Status;

import java.util.List;


@Builder
public record TaskResponse(
        @Schema(description = "Title of the task", example = "Task Title")
        String title,

        @Schema(description = "Description of the task", example = "Task Description")
        String description,

        @Schema(description = "Status of the task", example = "TO_DO")
        Status status,

        @Schema(description = "Priority of the task", example = "HIGH")
        Priority priority,

        @Schema(description = "Author of the task", example = "John Doe")
        String author,

        @Schema(description = "Assignee of the task", example = "Jane Doe")
        String assignee,

        @Schema(description = "List of comments", example = "[\"Comment 1\", \"Comment 2\"]")
        List<String> comments) {
}