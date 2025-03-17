package com.example.neewfeed.comment.controller;


import com.example.neewfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;


    //댓글 작성
    @PostMapping
    public ResponseEntity<>
    //댓글 전체조회

    //댓글 수정

    //댓글 삭제
}
