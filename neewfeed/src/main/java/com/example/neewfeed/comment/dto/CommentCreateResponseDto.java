package com.example.neewfeed.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;


    public CommentCreateResponseDto(Long id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }
}
