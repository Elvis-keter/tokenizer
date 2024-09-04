package com.elewa.assignment.mapper;

import com.elewa.assignment.model.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
    RefreshToken findByToken(String token);

    void confirmEmail(String token);
    void deleteByToken(String token);
    void deleteByUserId(Long id);
    void saveTokens(RefreshToken refreshToken);
}
