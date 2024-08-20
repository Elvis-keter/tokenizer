package com.elewa.assignment.model;

public class AuthenticationResponse {
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    private String jwt;
}
