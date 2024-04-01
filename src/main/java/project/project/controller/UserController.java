package project.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.Users;
import project.project.service.UsersService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/users/all")
    public ResponseEntity<List<Users>> getAll() {
        List<Users> users = usersService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/users/userInfo")
    public ResponseEntity<List<Users>> getUserInfo() {
        List<Users> user = usersService.findUserInfo();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users/insert")
    public ResponseEntity<String> insertUser() {
        Users newUser = new Users();

        newUser.setUserId("choi123");
        newUser.setPwd("choi123");
        newUser.setName("choi");
        newUser.setPhoneNum("010-9999-0000");
        newUser.setEmail("choi123@test.com");
        newUser.setGender(1);
        newUser.setBirth(LocalDate.of(1988,3,16));
        newUser.setState(1);

        usersService.insertUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

}
