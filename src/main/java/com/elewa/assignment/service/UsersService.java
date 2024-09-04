package com.elewa.assignment.service;

import com.elewa.assignment.mapper.RefreshTokenMapper;
import com.elewa.assignment.mapper.UsersMapper;
import com.elewa.assignment.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsersService {
    public UsersService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    private UsersMapper usersMapper;

    @Autowired
    EmailService emailService;
    private RefreshTokenMapper refreshTokenMapper;

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

//    public void saveUser(Users users) {
////        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
//        usersMapper.saveUser(users);
//    }
    public Users findByUsername(String username) {
        return usersMapper.findByUsername(username);
    }
    public Users findByEmail(String email) {return usersMapper.findByEmail(email);}
//    public void confirmEmail(String token) {refreshTokenMapper.findByToken(token)}
    public void updateUser(Users users) {
        usersMapper.updateUser(users);
    }

    public void deleteUser(Long id) {
        usersMapper.deleteUser(id);
    }

    private final ConcurrentHashMap<String, Users> temporaryUserStorage = new ConcurrentHashMap<>();

    public void saveUser(Users users) {
        users.setVerified(false);
        String verificationToken = UUID.randomUUID().toString();
        users.setVerificationToken(verificationToken);
        users.setVerificationExpiry(Instant.now().plusMillis(86400000)); // Token expires in 24 hours
//        usersMapper.saveUser(users);
        temporaryUserStorage.put(verificationToken, users);
        sendVerificationEmail(users);
    }

    private void sendVerificationEmail(Users users) {
        String subject = "Verify your email";
//        String senderName = "Just know the mail got from here";
        String mailContent = "Please verify your email by clicking the link below: \n";
        mailContent += "http://localhost:5050/verify?token=" + users.getVerificationToken();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Elvis Dashboard");
        message.setTo(users.getEmail());
        message.setSubject(subject);
        message.setText(mailContent);

        emailService.sendEmail(message);
    }

    public boolean verifyUser(String verificationToken) {
        Users users = temporaryUserStorage.get(verificationToken);
        if (users == null || users.getVerificationExpiry().isBefore(Instant.now())) {
            return false;
        }
        users.setVerified(true);
        users.setVerificationToken(null);
        users.setVerificationExpiry(null);
        usersMapper.saveUser(users);
        temporaryUserStorage.remove(verificationToken);
        return true;
    }

}