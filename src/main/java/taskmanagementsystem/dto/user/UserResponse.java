package taskmanagementsystem.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {


    @Schema(description = "User name", example = "John Doe")
    private String username;

    @Schema(description = "User email", example = "johndoe@gmail.com")
    private String email;


}
