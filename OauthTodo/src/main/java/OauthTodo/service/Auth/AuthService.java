package OauthTodo.service.Auth;

import OauthTodo.model.User;
import OauthTodo.request.LoginRequest;
import OauthTodo.request.SignupRequest;
import OauthTodo.response.LoginResponse;
import OauthTodo.response.SignupResponse;
import OauthTodo.service.JWT.JwtService;
import OauthTodo.service.User.UserService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public SignupResponse signup(SignupRequest signupRequest) {


        if (userService.findByUserName(signupRequest.getUsername()) != null) {
            throw new BadCredentialsException("user already exist");
        }
        User user = modelMapper.map(signupRequest, User.class);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User savedUser = userService.createUser(user);


        return modelMapper.map(savedUser, SignupResponse.class);
    }

    @Override
    public LoginResponse Login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);

        return LoginResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .userID(user.getId())
                .build();
    }

    @Override
    public Cookie createCookie(String refreshToken) {
        Cookie cookie = new Cookie("token", refreshToken);
        cookie.setHttpOnly(true);  // Secure the cookie with HttpOnly to prevent JavaScript access
        cookie.setMaxAge(180 * 24 * 60 * 60);  // Set cookie expiry to 6 months (in seconds)
        cookie.setPath("/");  // Optionally set the path for the cookie, "/" means it's available site-wide
        cookie.setSecure(true);

        return cookie;
    }

    @Override
    public LoginResponse oAuthSuccess(String token) {

        Long id = jwtService.getIdFromToken(token);
        User user= userService.findUserById(id);
        String refreshToken = jwtService.createRefreshToken(user);

        return LoginResponse.builder()
                .userID(user.getId())
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }
}
