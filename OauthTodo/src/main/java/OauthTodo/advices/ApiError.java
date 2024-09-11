package OauthTodo.advices;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiError {

    @Builder.Default
    private boolean success = false;

    private String message;
}
