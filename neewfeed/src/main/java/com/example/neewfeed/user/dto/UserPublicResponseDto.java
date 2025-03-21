package com.example.neewfeed.user.dto;

import lombok.Getter;

@Getter
public class UserPublicResponseDto extends UserResponseDto{
    public UserPublicResponseDto(Long id, String name)
    {
        super(id,name);
    }
}
