package project.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.requestParam.LoginParam;
import project.project.service.AdminsService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminsController {

    @Autowired
    private AdminsService adminsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginParam loginParam, HttpSession session) {
        Integer id = adminsService.login(loginParam.getAdminId(), loginParam.getAdminPwd());
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        // 세션에 id, adminId를 넣어줌
        session.setAttribute("id", id);
        session.setAttribute("adminId", loginParam.getAdminId());
        session.setMaxInactiveInterval(1800); // session 유효 시간 설정 : 30분
        return ResponseEntity.status(HttpStatus.OK).body("로그인되었습니다");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if(session != null) {
            session.invalidate();
        }
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃되었습니다");
    }
}
