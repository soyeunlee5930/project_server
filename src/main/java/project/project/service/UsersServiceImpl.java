package project.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import project.project.mapper.UsersMapper;
import project.project.model.*;
import project.project.provider.JwtProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedireactURI;

    @Value("${kakao.token.uri}")
    private String kakaoTokenURI;

    @Value("${kakao.userinfo.uri}")
    private String kakaoUserInfoURI;

    @Override
    public List<Users> findAll() {
        return usersMapper.findAll();
    }

    @Override
    public Users getUserById(Integer id) {
        return usersMapper.getUserById(id);
    }

    @Transactional
    @Override
    public Users getUserByUserId(String userId) {
        return usersMapper.getUserByUserId(userId);
    }

    @Override
    public void insertUser(Users user) {
        usersMapper.insertUser(user);
    }

    @Override
    public void updateUser(Users user) {
        usersMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        usersMapper.deleteUser(id);
    }

    // kakao
    @Transactional
    public String getKakaoAccessToken(String code) {
        // POST 방식으로 카카오에 데이터를 요청함
        RestTemplate rt = new RestTemplate();

        // HttpHeader Object 생성
        HttpHeaders headers = new HttpHeaders();

        // header에 내가 전송한 데이터값이 key=value 형태임을 알려주기 위함
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody Object 생성 : body 데이터를 받을 객체
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","authorization_code");
        body.add("client_id",kakaoClientId);
        body.add("client_secret",kakaoClientSecret);
        body.add("redirect_uri",kakaoRedireactURI);
        body.add("code",code);

        // 위에서 작성한 header, body 값을 가진 하나의 entity 생성
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = new HttpEntity<>(body,headers);

        // AccessToken 요청하기(Response type을 String으로 받음)
        ResponseEntity<String> response = rt.exchange(
                kakaoTokenURI,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON 데이터로 parsing
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String kakaoAccessToken = oauthToken.getAccess_token();

        return kakaoAccessToken;
    }

    public ResponseEntity<LoginResponseDto> kakaoLogin(String kakaoAccessToken) {
        Users user = getKakaoUserInfo(kakaoAccessToken);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setLoginSuccess(true);
        loginResponseDto.setUser(user);

        // jwt 생성
        String token = jwtProvider.createJwt(user.getUserId());

        // jwt를 응답 헤더에 추가하여 클라이언트에게 전달
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginResponseDto);
    }

    @Transactional
    public Users getKakaoUserInfo(String kakaoAccessToken) {
        // 카카오에 사용자정보를 요청함
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer "+ kakaoAccessToken);

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = rt.exchange(
                kakaoUserInfoURI,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // userId -> 카카오 계정 이메일 + "_" + 카카오 사용자 아이디
        String kakaoUserId = "kakaoUser" + "_" + kakaoProfile.getId();

        // 기존에 가입한 사용자인지, 새로 가입한 사용자인지 분기 처리
        Users registeredUser = usersMapper.getUserByUserId(kakaoUserId);

        if (registeredUser == null) {// 신규 가입자
            System.out.println("신규 회원입니다.");

            // Users Object : userId, name, phoneNum, email, gender(0 : 남성, 1 : 여성), birth, state
            Users kakaoUser = new Users();

            kakaoUser.setUserId(kakaoUserId);

            kakaoUser.setName(kakaoProfile.getKakao_account().getName());

            // phoneNum -> 카카오 전화번호 형식 +82 10-1234-5678을 010-1234-5678로 변환
            String convertedPhoneNumber = convertPhoneNumber(kakaoProfile.getKakao_account().getPhone_number());
            kakaoUser.setPhoneNum(convertedPhoneNumber);

            kakaoUser.setEmail(kakaoProfile.getKakao_account().getEmail());

            // DB의 gender type = int, kakao의 gender type = String이므로 변환
            int kakaoGender; // 남성(male) : 0, 여성(female) : 1
            if ("male".equals(kakaoProfile.getKakao_account().getGender())) {
                kakaoGender = 0;
            } else {
                kakaoGender = 1;
            }
            kakaoUser.setGender(kakaoGender);

            // DB의 birth type = LocalDate, kakao의 birthyear, birthday type = String이므로 더해서 변환
            String birthyear = kakaoProfile.getKakao_account().getBirthyear();
            String birthday = kakaoProfile.getKakao_account().getBirthday();
            String kakaoBirth = birthyear + birthday;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birth = LocalDate.parse(kakaoBirth, formatter);
            kakaoUser.setBirth(birth);
            kakaoUser.setUserType("kakao"); // kakao 로그인 및 회원가입으로 진행 시

            usersMapper.insertUser(kakaoUser);

            return kakaoUser;
        } else {
            System.out.println("기존 회원입니다.");
            return registeredUser;
        }
    }

    // 전화번호 형식 변환 메서드
    private String convertPhoneNumber(String phoneNumber) {
        String regex = "\\+82 (\\d{2})-(\\d{4})-(\\d{4})";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.find()) {
            String group1 = matcher.group(1);
            String group2 = matcher.group(2);
            String group3 = matcher.group(3);
            return "010-" + group2 + "-" + group3;
        } else {
            // 패턴에 매칭되는 부분이 없을 경우 원본 문자열 그대로 반환
            return phoneNumber;
        }
    }
}
