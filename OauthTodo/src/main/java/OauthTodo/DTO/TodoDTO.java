package OauthTodo.DTO;

import lombok.Data;

@Data
public class TodoDTO {
    private Long id;

    private String title;
    private String content;
}
