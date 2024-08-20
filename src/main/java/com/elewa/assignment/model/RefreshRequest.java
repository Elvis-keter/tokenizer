package com.elewa.assignment.model;

import jakarta.validation.constraints.NotEmpty;

public class RefreshRequest {
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    @NotEmpty
    private String refreshToken;
}
