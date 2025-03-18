package com.example.neewfeed.like.controller;

import com.example.neewfeed.common.config.JwtUtil;
import com.example.neewfeed.like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    //게시물 좋아요 생성
    @PostMapping
    public ResponseEntity<Map<String, String>> createPostLike(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable Long postId
    ) {
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserId(authorization);


        postLikeService.createPostLike(userId, postId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물 좋아요 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //게시물 좋아요 삭제
    @DeleteMapping("/{likeId}")
    public ResponseEntity<Map<String, String>> deletePostLike(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable Long postId,
            @PathVariable Long likeId
    ) {
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserId(authorization);

        postLikeService.deletePostLike(userId, postId, likeId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물 좋아요 삭 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
