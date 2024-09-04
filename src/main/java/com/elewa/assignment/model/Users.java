package com.elewa.assignment.model;

import java.time.Instant;

public class Users {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean isVerified;
    private String verificationToken;
    private Instant verificationExpiry;

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Instant getVerificationExpiry() {
        return verificationExpiry;
    }

    public void setVerificationExpiry(Instant verificationExpiry) {
        this.verificationExpiry = verificationExpiry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() { return email;}

    public void setEmail(String email) {
        this.email = email;
    }

}
