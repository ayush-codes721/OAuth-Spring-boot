package OauthTodo.service.Todo;

import OauthTodo.DTO.TodoDTO;
import OauthTodo.exception.ResourceNotFoundException;
import OauthTodo.model.Todo;
import OauthTodo.model.User;
import OauthTodo.repo.TodoRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService implements ITodoService {
    private final ModelMapper modelMapper;
    private final TodoRepo todoRepo;


    @Transactional
    @Override
    public TodoDTO createTodo(TodoDTO todoDTO) {

        User user = getUser();
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        todo.setUser(user);
        Todo savedTodo = todoRepo.save(todo);

        return modelMapper.map(savedTodo, TodoDTO.class);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        User user = getUser();

        return todoRepo.findByUser(user)
                .stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .toList();
    }

    @Override
    public TodoDTO getTodById(Long id) {
        User user = getUser();

        Todo todo = todoRepo.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Todo not found")); // Throw if not found

        if (!todo.getUser().getId().equals(user.getId())) {  // Check if the todo belongs to the logged-in user
            throw new BadCredentialsException("You are not authorized to view this todo");  // Unauthorized access
        }

        return modelMapper.map(todo, TodoDTO.class);
    }

    @Override
    public void deleteTodo(Long id) {

        User user = getUser();

        todoRepo.findById(id)
                .ifPresentOrElse(todo1 -> {
                    if (!todo1.getUser().getId().equals(user.getId())) {
                        throw new BadCredentialsException("You are not authorized to view this todo.");
                    }
                    todoRepo.delete(todo1);
                }, () -> {
                    throw new ResourceNotFoundException("todo not found");
                });
    }


    private User getUser() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
