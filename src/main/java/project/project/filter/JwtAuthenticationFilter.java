package project.project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import project.project.model.Users;
import project.project.provider.JwtProvider;
import project.project.service.UsersService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // Authorization, Bearer 중 하나라도 없는 경우, 다음 필터로 진행
            String token = parseBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // jwtProvider 검증
            String userId = jwtProvider.validate(token);
            if (userId == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // userId가 있는 경우, userId를 기반으로 사용자의 역할 확인
            Users user = usersService.getUserByUserId(userId);
            String role = user.getUserRole(); // user_role : ROLE_USER, ROLE_ADMIN 와 같이 ROLE_권한이름으로 설정

            // 사용자의 역할을 request의 속성으로 추가
            request.setAttribute("userRole", role);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 다음 filter로 넘어가도록 처리
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // 1. request로부터 header 안에 있는 Authorization 값을 가져온다.
        String authorization = request.getHeader("Authorization");

        // 2. Authorization이 존재하는지 확인하기, hasText()는 null, 0, 공백으로 채워져있지 않는지를 체크함
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        // 3. Authorization이 Bearer로 시작하는지 체크
        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        String token = authorization.substring(7);
        return token;
    }
}
