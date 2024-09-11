package OauthTodo.controller;

import OauthTodo.request.LoginRequest;
import OauthTodo.request.SignupRequest;
import OauthTodo.response.LoginResponse;
import OauthTodo.response.SignupResponse;
import OauthTodo.service.Auth.IAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        SignupResponse response = authService.signup(signupRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpResponse) {
        LoginResponse response = authService.Login(loginRequest);

        // Create a cookie for the refresh token
        Cookie cookie = authService.createCookie(response.getRefreshToken());
        httpResponse.addCookie(cookie);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/oauth-success")
    ResponseEntity<LoginResponse> oAuthLogin(@RequestParam String token, HttpServletResponse response) {

        LoginResponse loginResponse = authService.oAuthSuccess(token);
        Cookie cookie = authService.createCookie(loginResponse.getRefreshToken());
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponse);
    }
}
