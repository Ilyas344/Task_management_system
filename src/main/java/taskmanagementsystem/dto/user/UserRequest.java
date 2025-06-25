package taskmanagementsystem.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User DTO")
public class UserRequest {


    @Schema(
            description = "User name",
            example = "John Doe"
    )
    @NotNull(
            message = "Name must be not null."
    )
    @Length(
            max = 255,
            message = "Name length must be smaller than 255 symbols."
    )
    private String username;

    @Schema(
            description = "User email",
            example = "johndoe@gmail.com"
    )
    @NotNull(
            message = "Username must be not null."
    )
    @Length(
            max = 255,
            message = "Username length must be smaller than 255 symbols."
    )
    @Email
    private String email;

    @Schema(
            description = "User encrypted password"
    )
    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @NotNull(
            message = "Password must be not null."
    )
    private String password;
    @Schema(
            description = "User role"
    )

    private Set<RoleDto> role;

}
