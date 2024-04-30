package project.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import project.project.error.ErrorCode;
import project.project.handler.CustomException;

import project.project.model.Users;
import project.project.provider.JwtProvider;
import project.project.service.UsersService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value={"/admins", "/clients"})
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/users/all")
    public ResponseEntity<List<Users>> getAll() {
        List<Users> users = usersService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        Users user = usersService.getUserById(id);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            throw new CustomException("해당하는 정보의 사용자를 찾을 수 없습니다.", ErrorCode.USER_NOT_FOUND);
        }
    }

    @GetMapping("/users/join")
    public ResponseEntity<String> signup(@RequestBody Users user) {
        usersService.insertUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    // Kakao Login
//    @GetMapping("/oauth2/login/kakao")
//    public ResponseEntity<Users> kakaoLogin(@RequestParam("code") String code) {
//        if (code == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        String accessToken = usersService.getKakaoAccessToken(code);
//        System.out.println("accessToken : " + accessToken);
//
//        ResponseEntity<Users> response = usersService.getKakaoUserInfo(accessToken);
//
//        String token = jwtProvider.createJwt(accessToken);
//        System.out.println("jwt token : " + token);
//
//        // Access Token을 응답 헤더에 추가하여 클라이언트에게 전달
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token);
//
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response.getBody());
//    }
}
