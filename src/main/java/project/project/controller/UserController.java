package project.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.project.model.Users;
import project.project.service.UsersService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/all")
    public List<Users> getAll() {
        return usersService.findAll();
    }

    @GetMapping("/userInfo")
    public List<Users> getUserInfo() {
        return usersService.findUserInfo();
    }

    @GetMapping("/insert")
    public String insertUser() {
        Users newUser = new Users();

        newUser.setUser_id("park123");
        newUser.setPwd("park123");
        newUser.setName("park");
        newUser.setPhone_num("010-5555-6666");
        newUser.setEmail("park123@test.com");
        newUser.setGender(0);
        newUser.setBirth(LocalDate.of(1998,3,22));
        newUser.setState(1);

        usersService.insertUser(newUser);

        return "success";
    }

}
