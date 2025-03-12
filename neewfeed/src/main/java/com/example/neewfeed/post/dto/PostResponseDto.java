package com.example.neewfeed.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostResponseDto {
    private final Long id;
    private final String content;
    private final int likeCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    public PostResponseDto(Long id, String content, int likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }
}
