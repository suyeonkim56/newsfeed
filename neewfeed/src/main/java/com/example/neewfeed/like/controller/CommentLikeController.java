package com.example.neewfeed.like.controller;

import com.example.neewfeed.common.config.JwtUtil;
import com.example.neewfeed.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments/{commentId}/likes")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    //댓글 좋아요 생성
    @PostMapping
    public ResponseEntity<Map<String, String>> createCommentLike(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable Long commentId,
            @PathVariable Long postId) {
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserId(authorization);

        commentLikeService.createCommentLike(userId, commentId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글 좋아요 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 좋아요 삭제
    @DeleteMapping("/{likeId}")
    public ResponseEntity<Map<String, String>> deleteCommentLike(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable Long commentId,
            @PathVariable Long postId,
            @PathVariable Long likeId
    ) {
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserId(authorization);
        commentLikeService.deleteCommentLike(userId, commentId, likeId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글 좋아요 삭제 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
