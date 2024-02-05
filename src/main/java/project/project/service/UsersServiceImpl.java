package project.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.UsersMapper;
import project.project.model.Users;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<Users> findAll() {
        return usersMapper.findAll();
    }

    @Override
    public List<Users> findUserInfo() {
        return usersMapper.findUserInfo();
    }


}
