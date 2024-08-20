package com.elewa.assignment.controller;

import com.elewa.assignment.exception.RefreshTokenException;
import com.elewa.assignment.model.*;
import com.elewa.assignment.service.NewUserDetailsService;
import com.elewa.assignment.service.RefreshTokenService;
import com.elewa.assignment.service.UsersService;
import com.elewa.assignment.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private AuthenticationManager authenticationManager;

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
        String rawPassword = users.getPassword();

        users.setPassword(passwordEncoder.encode(rawPassword));
        // Assign default role
        users.setRole("EMPLOYEE");
        usersService.saveUser(users);
        Users savedUser = usersService.findByUsername(users.getUsername());

        Users rawUser = new Users();
        rawUser.setUsername(savedUser.getUsername());
        //pass uncoded password
        rawUser.setPassword(rawPassword);
        rawUser.setId(savedUser.getId());
        rawUser.setEmail(savedUser.getEmail());
        rawUser.setRole(savedUser.getRole());

        return authenticateUser(rawUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) throws Exception{

        return authenticateUser(users);
    }

    public ResponseEntity<?> authenticateUser(Users users) {
        try {
            System.out.println("JWT Line one: " );
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
            );
            System.out.println("JWT Line Two: ");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("JWT Line Three: ");
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("JWT Line Four: ");
            String jwt = jwtTokenUtil.generateToken(userDetails);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(users.getId());

            return ResponseEntity.ok(new JwtResponse(
                    users.getId(),
                    userDetails.getUsername(),
                    users.getEmail(),
                    jwt,
                    refreshToken.getToken(),
                    users.getRole()
            ));
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUsers)
                .map(users -> {
                    String token = jwtTokenUtil.generateTokenFromUsername(users.getUsername());
                    return ResponseEntity.ok(new RefreshResponse(
                            token, requestRefreshToken
                    ));
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken,
                        "Refresh token is not in the database!"));
    }
}
