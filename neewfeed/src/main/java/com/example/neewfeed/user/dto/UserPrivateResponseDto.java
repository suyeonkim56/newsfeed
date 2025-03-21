package com.example.neewfeed.user.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserPrivateResponseDto extends UserResponseDto{
    private final Long id;
    private final String email;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    public UserPrivateResponseDto(Long id, String email, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, name);
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
