package project.project.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import project.project.model.Users;

import java.util.List;

@Mapper
public interface UsersMapper {

//    @Select("SELECT * FROM users")
    List<Users> findAll();

//    @Select("SELECT * FROM users WHERE no=1")
    List<Users> findUserInfo();

//    @Insert("insert into users(name,id,pwd,tel) values (#{name},#{id},#{pwd},#{tel})")
//    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "no", before=false, resultType = Integer.class)
//    void insert(Users users);
}
