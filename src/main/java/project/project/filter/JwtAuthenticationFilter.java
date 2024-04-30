package project.project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import project.project.model.Users;
import project.project.provider.JwtProvider;
import project.project.service.UsersService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            // userId가 있는 경우
            Users user = usersService.getUserByUserId(userId);
            String role = user.getUserRole(); // user_role : ROLE_USER, ROLE_ADMIN 와 같이 ROLE_권한이름으로 설정

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));

            // user 정보를 담는 SecurityContext 만들기
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

            // 사용자 정보(Object 가능하지만 여기서는 userId), 비밀번호(설정 안해서 null처리), 인증권한 배열로 넣어주기
            AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext); // 만든 context 등록

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
