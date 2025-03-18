package com.example.neewfeed.like.controller;

import com.example.neewfeed.auth.annotation.Auth;
import com.example.neewfeed.auth.dto.AuthUser;
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
            @Auth AuthUser authUser,
            @PathVariable Long postId
            ){
        postLikeService.createPostLike(authUser,postId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물 좋아요 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //게시물 좋아요 삭제
    @DeleteMapping("/{likeId}")
    public ResponseEntity<Map<String,String>> deletePostLike(
            @Auth AuthUser authUser,
            @PathVariable Long postId,
            @PathVariable Long likeId
    ){
        postLikeService.deletePostLike(authUser,postId,likeId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물 좋아요 삭 성공.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
