package OauthTodo.service.Auth;

import OauthTodo.request.LoginRequest;
import OauthTodo.request.SignupRequest;
import OauthTodo.response.LoginResponse;
import OauthTodo.response.SignupResponse;
import jakarta.servlet.http.Cookie;

public interface IAuthService {

    SignupResponse signup(SignupRequest signupRequest);

    LoginResponse Login(LoginRequest loginRequest);

    Cookie createCookie(String refreshToken);

    LoginResponse oAuthSuccess(String token);

}
