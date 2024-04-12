package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.AdminsMapper;
import project.project.model.Admins;

import java.awt.*;
import java.util.List;

@Service
public class AdminsServiceImpl implements AdminsService {

    @Autowired
    private AdminsMapper adminsMapper;

    @Override
    public List<Admins> getAllAdmins() {
        return adminsMapper.getAllAdmins();
    }

    @Override
    public Integer login(String adminId, String adminPwd) {
        System.out.println("------- admin login service -----------");

        Admins admin = adminsMapper.getAdminById(adminId);
        if (admin != null) {// 조회된 관리자 정보가 있다면
            if (admin.getAdminPwd().equals(adminPwd)) {// 로그인폼에서 입력한 아이디, 비밀번호가 일치하면 로그인
                return admin.getId();
            }
        }
        return null;
    }

    @Override
    public void insertAdmin(Admins admin) {
        adminsMapper.insertAdmin(admin);
    }
}
