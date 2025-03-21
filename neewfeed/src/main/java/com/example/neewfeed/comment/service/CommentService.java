package com.example.neewfeed.comment.service;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.comment.dto.CommentCreateRequestDto;
import com.example.neewfeed.comment.dto.CommentCreateResponseDto;
import com.example.neewfeed.comment.dto.CommentResponseDto;
import com.example.neewfeed.comment.dto.CommentUpdateRequestDto;
import com.example.neewfeed.comment.entity.Comment;
import com.example.neewfeed.comment.repository.CommentRepository;
import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.post.repository.PostRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //댓글 생성
    @Transactional
    public CommentCreateResponseDto createComment(AuthUser authUser, CommentCreateRequestDto requestDto, Long postId) {
        User findUser = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물입니다."));
        Comment savecomment = new Comment(requestDto.getContent(), findUser, findPost);
        commentRepository.save(savecomment);
        System.out.println("댓글 저장 완료");
        return new CommentCreateResponseDto(
                savecomment.getId(),
                savecomment.getContent(),
                savecomment.getLikeCount(),
                savecomment.getUpdatedAt(),
                savecomment.getCreatedAt(),
                savecomment.getUser().getId(),
                savecomment.getPost().getId()
        );
    }

    //댓글 전체 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(AuthUser authUser, CommentUpdateRequestDto requestDto, Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글입니다."));

        if (!findComment.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("본인의 댓글만 수정할 수 있습니다.");
        }

        findComment.updateContent(requestDto.getNewContent());
        return new CommentResponseDto(
                findComment.getId(),
                findComment.getContent(),
                findComment.getLikeCount(),
                findComment.getCreatedAt(),
                findComment.getUpdatedAt()
        );
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(AuthUser authUser, Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalStateException("존재하지 않는 댓글입니다."));

        if (!findComment.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("본인의 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(findComment);
    }
}
