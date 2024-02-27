package project.project.service;

import project.project.model.Users;

import java.util.List;

public interface UsersService {
    List<Users> findAll();
    List<Users> findUserInfo();
    void insertUser(Users user);
}
