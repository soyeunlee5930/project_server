package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.project.model.Admins;

import java.util.List;

@Mapper
public interface AdminsMapper {
    List<Admins> getAllAdmins();

    // 관리자 정보
    Admins getAdminById(@Param("adminId") String adminId);

    void insertAdmin(Admins admin);
}
