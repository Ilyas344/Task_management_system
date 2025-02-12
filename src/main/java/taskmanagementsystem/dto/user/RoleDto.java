package taskmanagementsystem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import taskmanagementsystem.model.user.RoleEnum;


@Builder
public record RoleDto(@Schema(description = "Role ID", example = "1") Long id,
                      @Schema(description = "Role name", example = "ROLE_USER") RoleEnum name)
{

}