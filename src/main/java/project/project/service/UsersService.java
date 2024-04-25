package project.project.service;

import org.springframework.http.ResponseEntity;
import project.project.model.Users;

import java.util.List;

public interface UsersService {
    List<Users> findAll();
    Users getUserById(Integer id);
    Users getUserByUserId(String userId);
    void insertUser(Users user);
    void updateUser(Users user);
    void deleteUser(Integer id);
    String getKakaoAccessToken(String code);
    ResponseEntity<Users> getKakaoUserInfo(String kakaoAccessToken);
}
