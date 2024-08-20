package com.elewa.assignment.mapper;

import com.elewa.assignment.model.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
    RefreshToken findByToken(String token);

    void deleteToken(Long id);
    void saveTokens(RefreshToken refreshToken);
}
