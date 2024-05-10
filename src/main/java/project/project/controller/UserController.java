package project.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import project.project.error.ErrorCode;
import project.project.handler.CustomException;

import project.project.model.LoginResponseDto;
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
    @GetMapping("/oauth2/login/kakao")
    public ResponseEntity<LoginResponseDto> kakaoLogin(@RequestParam("code") String code) {
        if (code == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        String kakaoAccessToken = usersService.getKakaoAccessToken(code);

        return usersService.kakaoLogin(kakaoAccessToken);
    }

    @PostMapping("/oauth2/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String accessToken) {
        if (accessToken == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        try {
            usersService.logout(accessToken);
            return ResponseEntity.status(HttpStatus.OK).body("로그아웃되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃에 실패했습니다.");
        }
    }
}
