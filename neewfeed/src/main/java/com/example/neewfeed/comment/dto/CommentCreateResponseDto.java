package com.example.neewfeed.comment.dto;

import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponseDto {
    private Long id;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Long postId;

    public CommentCreateResponseDto(Long id, String content, int likeCount, LocalDateTime updatedAt, LocalDateTime createdAt, Long userId, Long postId) {
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.userId = userId;
        this.postId = postId;
    }
}
