package com.elewa.assignment.controller;

import com.elewa.assignment.model.AuthenticationResponse;
import com.elewa.assignment.model.Users;
import com.elewa.assignment.service.NewUserDetailsService;
import com.elewa.assignment.service.UsersService;
import com.elewa.assignment.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private NewUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @RequestMapping("/hello")
//    public String hello() {
//        return "uwuyvwcyuvec";
//    }
    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("companyName", "Duffenschmirtz Evil Incorporated");
        model.addAttribute("companyDetails", "Offering rewards for bringing Perry the Platipus");
        return "landing";
    }
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody Users users) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            users.getUsername(),
                            users.getPassword())
            );
        }
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                users.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        System.out.println(jwt);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

//    @PostMapping("/refreshtoken")
//    public ResponseEntity<?> refreshToken(@Valid @RequestBody Refre) {
//        String requestRefreshToken = request.getRefreshToken();
//
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUser)
//                .map(user -> {
//                    String token = jwtTokenUtil.generateTokenFromUsername(user.getUsername());
//                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//                        "Refresh token is not in the database!"));
//    }

}
