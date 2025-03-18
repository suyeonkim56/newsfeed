package com.example.neewfeed.like.controller;

import com.example.neewfeed.auth.annotation.Auth;
import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.like.repository.CommentLikeRepository;
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
            @Auth AuthUser authUser,
            @PathVariable Long commentId,
            @PathVariable Long postId) {
        commentLikeService.createCommentLike(authUser, commentId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글 좋아요 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 좋아요 삭제
    @DeleteMapping("/{likeId}")
    public ResponseEntity<Map<String, String>> deleteCommentLike(
            @Auth AuthUser authUser,
            @PathVariable Long commentId,
            @PathVariable Long postId,
            @PathVariable Long likeId
    ) {
        commentLikeService.deleteCommentLike(authUser, commentId, likeId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글 좋아요 삭제 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
