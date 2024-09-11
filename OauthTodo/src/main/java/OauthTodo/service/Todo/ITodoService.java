package OauthTodo.service.Todo;


import OauthTodo.DTO.TodoDTO;

import java.util.List;

public interface ITodoService {

    TodoDTO createTodo(TodoDTO todoDTO);

    List<TodoDTO> getAllTodos();

    TodoDTO getTodById(Long id);

    void deleteTodo(Long id);
}
