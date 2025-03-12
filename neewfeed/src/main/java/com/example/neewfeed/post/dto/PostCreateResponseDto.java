package com.example.neewfeed.post.dto;

import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PostCreateResponseDto {
    private final Long id;
    private final String contents;
    private final LocalDateTime createdAt;

    public PostCreateResponseDto(Long id, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
    }
}
