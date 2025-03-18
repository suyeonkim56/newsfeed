package com.example.neewfeed.post.service;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.follow.repository.FollowingRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    //게시물 생성
    @Transactional
    public PostCreateResponseDto createPost(AuthUser authUser, PostCreateRequestDto requestDto) {
        User findUser = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        Post post = new Post(requestDto.getContents(), findUser);
        Post savedpost = postRepository.save(post);
        return new PostCreateResponseDto(
                savedpost.getId(),
                savedpost.getContents(),
                savedpost.getCreatedAt(),
                savedpost.getUpdatedAt()
        );
    }

    //게시물 전체 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findFollowedPosts(AuthUser authUser, int page, int size) {
        // 1. 페이지 인덱스 조정
        int adjustedPage = Math.max(page - 1, 0);
        PageRequest pageable = PageRequest.of(adjustedPage, size);

        // 2. 팔로우 리스트 가져오기 + 본인 추가
        User currentUser = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        if (followingRepository.existsByFromId(authUser.getId())) {
            List<User> followers = followingRepository.findByFromId(authUser.getId());
        }
        List<User> followers = new ArrayList<User>();
        followers.add(currentUser);

        // 3. 팔로우한 사람들의 게시물 페이징 조회
        Page<Post> posts = postRepository.findByFollowers(followers, pageable);

        // 4. DTO 변환
        return posts.stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getContents(),
                        post.getLikeCount(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    //수정일자 기준 정렬
    @Transactional(readOnly = true)
    public List<PostResponseDto> findFollowedPostsOrderByUpdatedAt(AuthUser authUser, int page, int size) {
        // 1. 페이지 인덱스 조정
        int adjustedPage = Math.max(page - 1, 0);
        PageRequest pageable = PageRequest.of(adjustedPage, size);

        // 2. 팔로우 리스트 가져오기 + 본인 추가
        User currentUser = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
        if (followingRepository.existsByFromId(authUser.getId())) {
            List<User> followers = followingRepository.findByFromId(authUser.getId());
        }
        List<User> followers = new ArrayList<User>();
        followers.add(currentUser);

        // 3. 팔로우한 사람들의 게시물 페이징 조회
        Page<Post> posts = postRepository.findByFollowersOrderByUpdatedAt(followers, pageable);

        // 4. DTO 변환
        return posts.stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getContents(),
                        post.getLikeCount(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    //게시물 기간별 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findOrderByDate(int page, int size, LocalDateTime startDate, LocalDateTime endDate) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts;
        posts = postRepository.findPostsBetweenDates(pageable, startDate, endDate);
        return posts.stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getContents(),
                        post.getLikeCount(),
                        post.getCreatedAt(),
                        post.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    //게시물 단일 조회
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long postId) {
        Post findpost = postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));
        return new PostResponseDto(
                findpost.getId(),
                findpost.getContents(),
                findpost.getLikeCount(),
                findpost.getCreatedAt(),
                findpost.getUpdatedAt());
    }


    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, AuthUser authUser, PostUpdateRequestDto requestDto) {
        Post findpost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        if (!findpost.getUser().equals(user)) {
            throw new IllegalStateException("본인의 게시물만 수정할 수 있습니다.");
        }
        if (findpost.getContents().equals(requestDto.getNewcontents())) {
            throw new IllegalStateException("기존과 동일한 내용으로 수정할 수 없습니다.");
        }
        findpost.updateContents(requestDto.getNewcontents());

        return new PostResponseDto(
                findpost.getId(),
                findpost.getContents(),
                findpost.getLikeCount(),
                findpost.getCreatedAt(),
                findpost.getUpdatedAt());
    }

    //게시물 삭제
    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        Post findpost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        if (!findpost.getUser().equals(user)) {
            throw new IllegalStateException("본인의 게시물만 삭제할 수 있습니다.");
        }
        postRepository.delete(findpost);
    }

}
