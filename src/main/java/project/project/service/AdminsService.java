package project.project.service;

import project.project.model.Admins;

import java.util.List;

public interface AdminsService {

    List<Admins> getAllAdmins();

    // 관리자 정보(login)
    Integer login(String adminId, String adminPwd);

    void insertAdmin(Admins admin);
}
