package com.elewa.assignment.service;

import com.elewa.assignment.mapper.UsersMapper;
import com.elewa.assignment.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    public UsersService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    UsersMapper usersMapper;

    public void assignManagerRole(Long userId) {
        usersMapper.updateUserRole(userId, "MANAGER");
    }

    public List<Users> findAll() {
        return usersMapper.findAll();
    }

    public Users findById(Long id) {
        return usersMapper.findById(id);
    }

    public void saveUser(Users users) {
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        usersMapper.saveUser(users);
    }

    public Users findByUsername(String username) {
        return usersMapper.findByUsername(username);
    }

    public void updateUser(Users users) {
        usersMapper.updateUser(users);
    }

    public void deleteUser(Long id) {
        usersMapper.deleteUser(id);
    }
}