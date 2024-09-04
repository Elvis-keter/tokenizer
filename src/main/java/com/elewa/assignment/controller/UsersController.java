package com.elewa.assignment.controller;

import com.elewa.assignment.exception.RefreshTokenException;
import com.elewa.assignment.model.*;
import com.elewa.assignment.service.NewUserDetailsService;
import com.elewa.assignment.service.RefreshTokenService;
import com.elewa.assignment.service.UsersService;
import com.elewa.assignment.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    private final UsersService usersService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody Users users) {
        if (usersService.findByEmail(users.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email is already registered");
        }

        if (usersService.findByUsername(users.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        String rawPassword = users.getPassword();
        users.setPassword(passwordEncoder.encode(rawPassword));
        // Assign default role
        users.setRole("EMPLOYEE");
        usersService.saveUser(users);

        return ResponseEntity.ok("Please check your email for verification.");
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        boolean isVerified = usersService.verifyUser(token);
        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired verification token.");
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    String token = jwtTokenUtil.generateTokenFromUsername(refreshToken.getUsers().getUsername());
                    return ResponseEntity.ok(new RefreshResponse(
                            token, requestRefreshToken,
                            refreshToken.getCreatedAt(),
                            refreshToken.getExpiryDate()
                    ));
                })
                .orElseThrow(() -> new RefreshTokenException(
                        requestRefreshToken,
                        "Refresh token has expired. Please make a new signin request"
                ));
    }
}
