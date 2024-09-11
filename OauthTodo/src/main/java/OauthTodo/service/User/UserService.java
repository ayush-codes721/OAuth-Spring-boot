package OauthTodo.service.User;

import OauthTodo.exception.ResourceNotFoundException;
import OauthTodo.model.User;
import OauthTodo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public User updateUser(Map<String, Object> updates) {
        return null;
    }

    @Override
    public User findByUserName(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }
}
