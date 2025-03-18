package com.example.neewfeed.comment.controller;


import com.example.neewfeed.comment.dto.CommentCreateRequestDto;
import com.example.neewfeed.comment.dto.CommentCreateResponseDto;
import com.example.neewfeed.comment.dto.CommentResponseDto;
import com.example.neewfeed.comment.dto.CommentUpdateRequestDto;
import com.example.neewfeed.comment.service.CommentService;
import com.example.neewfeed.common.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;


    //댓글 작성
    @PostMapping
    public ResponseEntity<CommentCreateResponseDto> createComment(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody CommentCreateRequestDto requestDto,
            @PathVariable Long postId
            ){
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserId(authorization);

        CommentCreateResponseDto dto = commentService.createComment(userId, requestDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //댓글 전체조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(){
        List<CommentResponseDto> dtos = commentService.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody CommentUpdateRequestDto requestDto,
            @PathVariable Long commentId
            ){
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserId(authorization);
        CommentResponseDto dto = commentService.updateComment(userId,requestDto,commentId);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable Long commentId
    ){
        // JWT에서 userId 추출
        Long userId = JwtUtil.extractUserId(authorization);
        commentService.deleteComment(userId,commentId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "댓글이 성공적으로 삭제되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
