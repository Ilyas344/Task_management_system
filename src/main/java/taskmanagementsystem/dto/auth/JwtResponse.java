package taskmanagementsystem.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Request after login")
public class JwtResponse {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User email", example = "johndoe@gmail.com")
    private String email;

    @Schema(description = "Access token", example = "your-access-token")
    private String accessToken;

    @Schema(description = "Refresh token", example = "your-refresh-token")
    private String refreshToken;

}
