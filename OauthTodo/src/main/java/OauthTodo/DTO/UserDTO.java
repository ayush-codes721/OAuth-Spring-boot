package OauthTodo.DTO;

import OauthTodo.model.Todo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private Long id;

    private String username;
    private String name;
    private String password;

    private List<TodoDTO> todos ;

}
