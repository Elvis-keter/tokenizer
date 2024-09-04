package com.elewa.assignment.model;

import java.time.Instant;

public class RefreshResponse {
    private String accessToken;
    private String refreshToken;
    private Instant createdAt;
    private Instant expiryDate;
    private String tokenType = "Bearer";

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public RefreshResponse(String accessToken, String refreshToken, Instant createdAt, Instant expiryDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
        this.expiryDate = expiryDate;
    }
}
