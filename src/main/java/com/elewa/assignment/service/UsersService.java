package com.elewa.assignment.service;

import com.elewa.assignment.mapper.UsersMapper;
import com.elewa.assignment.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {
    public UsersService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    private UsersMapper usersMapper;

    public void assignManagerRole(Long userId) {
        usersMapper.updateUserRole(userId, "MANAGER");
    }

    public void assignEmployeeRole(Long userId) {
        usersMapper.updateUserRole(userId, "EMPLOYEE");
    }

    public List<Users> findAll() {
        return usersMapper.findAll();
    }

    public Users findById(Long id) {
        return usersMapper.findById(id);
    }

    public void saveUser(Users users) {
//        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
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
//
//    private JavaMailSender mailSender;
//
//    public void registerUser(Users users) {
//        users.setEnabled(false);
//        users.setVerificationToken(UUID.randomUUID().toString());
//        usersMapper.insertUser(users);
//        sendVerificationEmail(users);
//    }
//
//    private void sendVerificationEmail(Users users) {
//        String subject = "Verify your email";
//        String senderName = "Your Company Name";
//        String mailContent = "<p>Please verify your email:</p>";
//        mailContent += "<p><a href=\"http://localhost:5050/verify?token=" + users.getVerificationToken() + "\">Verify</a></p>";
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("ketere254@gmail.com");
//        message.setTo(users.getEmail());
//        message.setSubject(subject);
//        message.setText(mailContent);
//
//        mailSender.send(message);
//    }
//
//    public boolean verifyUser(String verificationToken) {
//        Users users = usersMapper.findByToken(verificationToken);
//        if (users == null) {
//            return false;
//        }
//        users.setEnabled(true);
//        users.setVerificationToken(null);
//        usersMapper.updateUser(users);
//        return true;
//    }
}