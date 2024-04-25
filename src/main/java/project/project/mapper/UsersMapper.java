package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Users;

import java.util.List;

@Mapper
public interface UsersMapper {
    List<Users> findAll();
    Users getUserById(Integer id);
    Users getUserByUserId(String userId);
    void insertUser(Users user);
    void updateUser(Users user);
    void deleteUser(Integer id);
}
