package com.example.neewfeed.post.controller;


import com.example.neewfeed.auth.annotation.Auth;
import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.post.dto.PostCreateRequestDto;
import com.example.neewfeed.post.dto.PostCreateResponseDto;
import com.example.neewfeed.post.dto.PostResponseDto;
import com.example.neewfeed.post.dto.PostUpdateRequestDto;
import com.example.neewfeed.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    //게시물 생성
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> createPost(
            @Auth AuthUser authUser,
            @RequestBody PostCreateRequestDto requestDto
    ) {
        PostCreateResponseDto dto = postService.createPost(authUser, requestDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //본인 포함 팔로워 게시물 전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findFollowedAll(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponseDto> findList = postService.findFollowedPosts(authUser, page, size);
        return new ResponseEntity<>(findList, HttpStatus.OK);
    }

    //(본인 포함 팔로워)게시물 수정일자 기준 정렬
    @GetMapping("/sorted")
    public ResponseEntity<List<PostResponseDto>> findOrderByUpdatedAt(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponseDto> findList = postService.findFollowedPostsOrderByUpdatedAt(authUser, page, size);
        return new ResponseEntity<>(findList, HttpStatus.OK);
    }

    //게시물 기간별 조회
    @GetMapping("/period")
    public ResponseEntity<List<PostResponseDto>> findOrderByDate(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate
    ) {
        List<PostResponseDto> findList = postService.findOrderByDate(page, size, startDate, endDate);
        return new ResponseEntity<>(findList,HttpStatus.OK);
    }

    //게시물 단일 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findById(
            @PathVariable Long postId
    ) {
        PostResponseDto dto = postService.findById(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @Auth AuthUser authUser,
            @RequestBody PostUpdateRequestDto requestDto
    ) {
        PostResponseDto dto = postService.updatePost(postId, authUser, requestDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, String>> deletePost(
            @PathVariable Long postId,
            @Auth AuthUser authUser
    ) {
        postService.deletePost(authUser, postId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물이 성공적으로 삭제되었습니다.");

        return ResponseEntity.ok(response);
    }
}
