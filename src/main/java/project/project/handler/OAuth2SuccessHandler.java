package project.project.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import project.project.model.CustomOAuth2User;
import project.project.provider.JwtProvider;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${kakao.redirect.uri}")
    private String kakaoRedireactURI;

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2User.getName();
        String jwt = jwtProvider.createJwt(userId);
        String url = makeRedirectUrl(jwt);

//        response.sendRedirect("http://localhost:3000/auth/oauth-response/" + jwt + "/3600");
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(String jwt) {
        return UriComponentsBuilder.fromUriString(kakaoRedireactURI + "/" + jwt)
                .build().toUriString();
    }
}
