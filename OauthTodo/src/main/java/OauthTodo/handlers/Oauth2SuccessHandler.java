package OauthTodo.handlers;

import OauthTodo.model.User;
import OauthTodo.service.JWT.JwtService;
import OauthTodo.service.User.IUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final IUserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User auth2User = (DefaultOAuth2User) token.getPrincipal();

        String email = auth2User.getAttribute("email");
        log.info(auth2User.getAttribute("email"));
        User user = userService.findByUserName(email);
        if (user == null) {
            User newuser = new User();
            newuser.setName(auth2User.getName());
            newuser.setUsername(email);

            user = userService.createUser(newuser);

        }






        String accessToken = jwtService.createAccessToken(user);

        String redirectUrl = String.format("http://localhost:5000/api/auth/oauth-success?token=%s", accessToken);
//        log.info(redirectUrl);
        getRedirectStrategy().sendRedirect(request,response,redirectUrl);




    }
}
