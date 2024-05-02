package project.project.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.project.filter.JwtAuthenticationFilter;
import project.project.handler.OAuth2SuccessHandler;

import java.io.IOException;

@Configurable
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private DefaultOAuth2UserService oAuth2UserService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        // csrf(Cross Site Request Forgery) , httpBasic 기본설정(아이디, 비밀번호) 사용하지 않음(Bearer 인증방식 사용), sessionManagement 사용하지 않음, reqeustMatchers의 경로에 따라 permitAll(모두 허용), hasRole("USER" 혹은 "ADMIN"만 허용), 이 외는 모두 인증하도록 처리, exceptionHandling : 인가 실패에 따른 처리, 마지막에 필터 등록까지 완료
        httpSecurity
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request->request
                        .requestMatchers("/","/clients/oauth2/**","/oauth2/**").permitAll()
                        .requestMatchers("/clients/**").hasRole("USER")
                        .requestMatchers("/admins/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint -> endpoint
                                .baseUri("/api/v1/auth/oauth2"))
                        .redirectionEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/login/**")
                        )
                        .userInfoEndpoint(endpoint->endpoint
                                .userService(oAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        // CorsConfiguration 새 인스턴스 생성
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // 해당 출처에 대해서 허용
        corsConfiguration.addAllowedMethod("*"); // 모든 메서드에 대해서 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 헤더에 대해서 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 패턴에 적용, corsConfiguration

        return source;
    }
}

// 인가 실패에 대한 결과처리
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json"); // json 형태로 반환
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 권한 없음(State Code_FORBIDDEN)
        response.getWriter().write("{\"code\" : \"NP\", \"message\" : \"No permission\"}"); // { "code" : "NP", "message" : "No permission" }
    }
}