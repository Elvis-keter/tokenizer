package com.elewa.assignment.service;

import com.elewa.assignment.mapper.RefreshTokenMapper;
import com.elewa.assignment.mapper.UsersMapper;
import com.elewa.assignment.model.RefreshToken;
import com.elewa.assignment.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Autowired
    UsersMapper usersMapper;
    public RefreshToken createRefreshToken(Long userId) {
        Users user = usersMapper.findById(userId);

        if(user == null) {
            throw new IllegalArgumentException("Invalid user id:" + userId);
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(86400000)); // 24 hours
        refreshToken.setUsers(user);

        refreshTokenMapper.saveTokens(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return Optional.ofNullable(refreshTokenMapper.findByToken(token));
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenMapper.deleteToken(token.getUsers().getId());
            throw new RuntimeException("Refresh token was expired.");
        }
        return token;
    }

    public void deleteByUserId(Long userId) {
        refreshTokenMapper.deleteToken(userId);
    }
}
