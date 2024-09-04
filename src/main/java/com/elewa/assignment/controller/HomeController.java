package com.elewa.assignment.controller;

import com.elewa.assignment.model.AuthenticationResponse;
import com.elewa.assignment.model.JwtResponse;
import com.elewa.assignment.model.RefreshToken;
import com.elewa.assignment.model.Users;
import com.elewa.assignment.service.RefreshTokenService;
import com.elewa.assignment.service.UsersService;
import com.elewa.assignment.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("companyName", "Duffenschmirtz Evil Incorporated");
        model.addAttribute("companyDetails", "Offering rewards for bringing Perry the Platipus");
        return "landing";
    }
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody Users users) throws Exception {
        try {
            System.out.println(users.getUsername());
            System.out.println(users.getPassword());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtTokenUtil.generateToken(userDetails);
            Users getCredentials = usersService.findByUsername(userDetails.getUsername());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(getCredentials.getId());

            return ResponseEntity.ok(new JwtResponse(
                    getCredentials.getId(),
                    getCredentials.getUsername(),
                    getCredentials.getEmail(),
                    jwt,
                    refreshToken.getToken(),
                    getCredentials.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("User does not exist. Please go back and sign up");
        }
    }
}
