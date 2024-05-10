package project.project.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    public String createJwt(String kakaoAccessToken, String userId) {

        // 만료기간 설정하기 : 현재 시간으로부터 1시간까지
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // JWT Key 설정
        Key key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

        // jwt 생성하기 : secret key, token, 현재 시간, 만료 시간
        String jwt = Jwts.builder().signWith(key, SignatureAlgorithm.HS256).setSubject(userId).claim("kakaoAccessToken", kakaoAccessToken).setIssuedAt(new Date()).setExpiration(expiredDate).compact();

        return jwt;
    }

    // jwt를 검증하는 메서드
    public String validate(String jwt) {

        String subject = null;
        Key key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

        try {
            // jwt를 검증한(validate) 결과값을 받을 claims
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            subject = claims.getSubject();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return subject;
    }

    // jwt 만료시키기
    public void invalidateJwtToken(String jwt) {

        Key key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));

        // 토큰 해독하여 클레임 가져오기
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        // jwt 만료시간을 현재 시간으로부터 5분 전으로 설정하여 토큰을 즉시 만료시킴
        Date expirationDate = new Date(System.currentTimeMillis() - 5 * 60 * 1000);
        claims.setExpiration(expirationDate);
    }

    // 카카오 액세스 토큰 가져오기
    public String getKakaoAccessTokenFromJwt(String jwt) {
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(jwt).getBody();
        return (String) claims.get("kakaoAccessToken");
    }

    // JWT 만료 여부 확인
    public boolean isTokenExpired(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(jwt).getBody();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true; // 토큰 파싱 오류 등이 발생한 경우에도 만료된 것으로 처리
        }
    }
}
