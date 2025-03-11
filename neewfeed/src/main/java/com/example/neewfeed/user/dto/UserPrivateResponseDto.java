package com.example.neewfeed.user.dto;


import lombok.Getter;

@Getter
public class UserPrivateResponseDto extends UserResponseDto{
    private final Long id;
    private final String email;
    private final String name;

    public UserPrivateResponseDto(Long id, String email, String name) {
        super(id, name);
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
