package com.elewa.assignment.util;

import com.elewa.assignment.exception.JwtExceptionHandler;
import com.elewa.assignment.service.NewUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//class for intercepting request one time
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private NewUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String username = null; String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtTokenUtil.extractUsername(jwt);

            }
            catch (SignatureException e) {
                handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid signature token");
                return;
            }
            catch (MalformedJwtException e) {
                handleException(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid jwt token");
                return;
            }
            catch (ExpiredJwtException e) {
                handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            }
            catch (UsernameNotFoundException e) {
                handleException(response, HttpServletResponse.SC_NOT_FOUND, "Username not found in DB");
                return;
            }
            catch (IllegalArgumentException e) {
                handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Jwt claims string is empty");
                return;
            }
            catch (UnsupportedJwtException e) {
                handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Jwt token is unsupported");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //rest of the filters in the filter chain
        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
