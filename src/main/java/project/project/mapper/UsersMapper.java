package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Users;

import java.util.List;

@Mapper
public interface UsersMapper {

    List<Users> findAll();

    List<Users> findUserInfo();

    void insertUser(Users user);
}
