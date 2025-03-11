package com.example.neewfeed.auth.dto;

import lombok.Getter;

@Getter
public class UserSigninResponseDto {

    private final String bearerJwt;

    public UserSigninResponseDto(String bearerJwt) {
        this.bearerJwt = bearerJwt;
    }
}
