package java.taskmanagementsystem.dto.auth;

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

    private Long id;
    private String email;
    private String accessToken;
    private String refreshToken;

}
