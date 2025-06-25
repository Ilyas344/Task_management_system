package taskmanagementsystem.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import taskmanagementsystem.model.user.RoleEnum;


@Builder
public record RoleDto(
                      @Schema(description = "Role name", example = "ROLE_USER") RoleEnum name)
{

}