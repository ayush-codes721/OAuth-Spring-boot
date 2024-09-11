package OauthTodo.repo;

import OauthTodo.model.Todo;
import OauthTodo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepo extends JpaRepository<Todo,Long> {

    List<Todo> findByUser(User user);
}
