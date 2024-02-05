package project.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.project.model.Users;
import project.project.service.UsersService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/users")
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

}
