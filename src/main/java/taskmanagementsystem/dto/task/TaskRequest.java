package taskmanagementsystem.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import taskmanagementsystem.model.task.Priority;
import taskmanagementsystem.model.task.Status;

import java.util.List;

@Builder
public record TaskRequest(
        @Schema(description = "Title of the task", example = "Task Title")
        @NotNull @NotEmpty
        String title,

        @Schema(description = "Description of the task", example = "Task Description")
        String description,

        @Schema(description = "Status of the task", example = "TO_DO")
        @NotNull
        Status status,

        @Schema(description = "Priority of the task", example = "HIGH")
        @NotNull
        Priority priority,

        @Schema(description = "Author of the task", example = "John Doe")
        @NotNull @NotEmpty @NotBlank
        String author,

        @Schema(description = "Assignee of the task", example = "Jane Doe")
        @NotNull @NotEmpty @NotBlank
        String assignee,

        @Schema(description = "List of comments", example = "[\"Comment 1\", \"Comment 2\"]")
        List<String> comments) {

}
