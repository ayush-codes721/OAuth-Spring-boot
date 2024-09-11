package OauthTodo.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long userID;
    private String accessToken;
    private String refreshToken;
}
