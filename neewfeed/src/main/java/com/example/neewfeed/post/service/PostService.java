package com.example.neewfeed.post.service;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.post.dto.PostCreateRequestDto;
import com.example.neewfeed.post.dto.PostCreateResponseDto;
import com.example.neewfeed.post.dto.PostResponseDto;
import com.example.neewfeed.post.dto.PostUpdateRequestDto;
import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.post.repository.PostRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //게시물 생성
    @Transactional
    public PostCreateResponseDto createPost(AuthUser authUser, PostCreateRequestDto requestDto) {
        User findUser = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        Post post = new Post(requestDto.getContents(), findUser);
        Post savedpost = postRepository.save(post);
        return new PostCreateResponseDto(
                savedpost.getId(),
                savedpost.getContents(),
                savedpost.getCreatedAt()
        );
    }

    //게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll(int page, int size) {
        // 클라이언트에서 1부터 전달된 페이지 번호를 0 기반으로 조정
        int adjustedPage = (page > 0) ? page - 1 : 0;
        PageRequest pageable = PageRequest.of(adjustedPage, size, Sort.by("createdAt").descending());
        // 1. Post page 조회
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostResponseDto> dtos = new ArrayList<>();

        for (Post post : posts) {
            dtos.add(new PostResponseDto(
                    post.getId(),
                    post.getContents(),
                    post.getLikeCount(),
                    post.getCreatedAt()
            ));
        }
        return dtos;
    }

    //게시물 단일 조회
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long postId) {
        Post findpost = postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));
        return new PostResponseDto(findpost.getId(), findpost.getContents(), findpost.getLikeCount(), findpost.getCreatedAt());
    }


    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, AuthUser authUser, PostUpdateRequestDto requestDto) {
        Post findpost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        if(!findpost.getUser().equals(user)){
            throw new IllegalStateException("본인의 게시물만 수정할 수 있습니다.");
        }
        if(findpost.getContents().equals(requestDto.getNewcontents()))
        {
            throw new IllegalStateException("기존과 동일한 내용으로 수정할 수 없습니다.");
        }
        findpost.updateContents(requestDto.getNewcontents());

        return new PostResponseDto(findpost.getId(),findpost.getContents(),findpost.getLikeCount(),findpost.getCreatedAt());
    }

    //게시물 삭제
    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        Post findpost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        if(!findpost.getUser().equals(user)){
            throw new IllegalStateException("본인의 게시물만 삭제할 수 있습니다.");
        }

        postRepository.delete(findpost);
    }
}
