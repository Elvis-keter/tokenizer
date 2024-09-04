package com.elewa.assignment.mapper;

import com.elewa.assignment.model.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {
    Users findById(Long id);
    Users findByUsername(String username);
    Users findByEmail(String email);
    Users findByToken(String token);
    List<Users> findAll();

    Boolean emailExists(String email);
    void saveUser(Users users);
    void insertUser(Users users);

    void updateUser(Users users);

    Users findByRoleId(String role);
//    void findByRoleId(@Param("id") Long id, @Param("role") String role);

    void updateUserRole(@Param("id") Long id, @Param("role") String role);
    void deleteUser(Long id);
}
