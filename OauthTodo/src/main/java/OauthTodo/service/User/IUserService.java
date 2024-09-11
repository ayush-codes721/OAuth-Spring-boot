package OauthTodo.service.User;

import OauthTodo.model.User;

import java.util.Map;

public interface IUserService {


    User findUserById(Long id);

    User updateUser(Map<String, Object> updates);

    User findByUserName(String username);

    User createUser(User user);
}
