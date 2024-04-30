package project.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.project.mapper.UsersMapper;
import project.project.model.CustomOAuth2User;
import project.project.model.Users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

//        try {
//            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String userId = null;
        userId = "kakaoUser_" + oAuth2User.getAttributes().get("id"); // id를 key로 값을 꺼내오기

        // OAuth2User 객체에서 kakao_account 속성을 가져옴
        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");

        String name = (String) kakaoAccount.get("name");
        String email = (String) kakaoAccount.get("email");

        // phoneNum -> 카카오 전화번호 형식 +82 10-1234-5678을 010-1234-5678로 변환
        String phoneNumber = (String) kakaoAccount.get("phone_number");
        String convertedPhoneNumber = convertPhoneNumber(phoneNumber);

        // DB의 gender type = int, kakao의 gender type = String이므로 변환
        String kakaoGender = (String) kakaoAccount.get("gender");
        int gender;
        if ("male".equals(kakaoGender)) {
            gender = 0;
        } else {
            gender = 1;
        }

        // DB의 birth type = LocalDate, kakao의 birthyear, birthday type = String이므로 더해서 변환
        String kakaoBirth = (String) kakaoAccount.get("birthyear") + (String) kakaoAccount.get("birthday");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birth = LocalDate.parse(kakaoBirth, formatter);

        // 기존에 가입한 사용자인지, 새로 가입한 사용자인지 분기 처리
        Users registeredUser = usersMapper.getUserByUserId(userId);

        if (oauthClientName.equals("kakao")) {
            if (registeredUser == null) {// 신규 가입자
                Users kakaoUser = new Users();
                kakaoUser.setUserId(userId);
                kakaoUser.setName(name);
                kakaoUser.setPhoneNum(convertedPhoneNumber);
                kakaoUser.setEmail(email);
                kakaoUser.setGender(gender);
                kakaoUser.setBirth(birth);
                kakaoUser.setUserType("kakao");

                usersMapper.insertUser(kakaoUser);
            } else {
                System.out.println("이미 가입한 회원입니다. 로그인으로 진행합니다.");
            }
        }

        return new CustomOAuth2User(userId);
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
