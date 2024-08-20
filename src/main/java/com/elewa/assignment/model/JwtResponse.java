package com.elewa.assignment.model;

public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String role;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JwtResponse(Long id, String username, String email, String accessToken, String refreshToken, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
