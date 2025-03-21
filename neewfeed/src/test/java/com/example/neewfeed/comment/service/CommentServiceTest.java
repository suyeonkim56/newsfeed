package com.example.neewfeed.comment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.comment.dto.CommentCreateResponseDto;
import com.example.neewfeed.comment.dto.CommentUpdateRequestDto;
import com.example.neewfeed.comment.entity.Comment;
import com.example.neewfeed.comment.repository.CommentRepository;
import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.post.repository.PostRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Ref;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void 정상적으로_댓글을_생성한다() {
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        Post post = new Post("contents", user);
        ReflectionTestUtils.setField(post, "id", 1L);

        Comment comment = new Comment("test", user, post);
        ReflectionTestUtils.setField(comment, "id", 1L);


        //when
        CommentCreateResponseDto responseDto = new CommentCreateResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getUpdatedAt(),
                comment.getCreatedAt(),
                comment.getUser().getId(),
                comment.getPost().getId());

        //then
        assertThat(responseDto.getId()).isEqualTo(comment.getId());
        assertThat(responseDto.getContent()).isEqualTo(comment.getContent());
        assertThat(responseDto.getUserId()).isEqualTo(comment.getUser().getId());
        assertThat(responseDto.getPostId()).isEqualTo(comment.getPost().getId());
    }

    @Test
    void 정상적으로_댓글을_수정한다(){
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        Post post = new Post("contents", user);
        ReflectionTestUtils.setField(post, "id", 1L);

        Comment comment = new Comment("test", user, post);
        ReflectionTestUtils.setField(comment, "id", 1L);

        CommentUpdateRequestDto requestDto = new CommentUpdateRequestDto();
        String newContent = "new";
        requestDto.setNewContent(newContent);

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        //when
        commentService.updateComment(authUser,requestDto,1L);

        //then
        assertThat(comment.getContent()).isEqualTo(newContent);
    }

    @Test
    void 정상적으로_댓글을_삭제한다() {
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        Post post = new Post("contents", user);
        ReflectionTestUtils.setField(post, "id", 1L);

        Comment comment = new Comment("test", user, post);
        ReflectionTestUtils.setField(comment, "id", 1L);

        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        //when
        commentService.deleteComment(authUser,comment.getId());

        //then
        assertThat(commentRepository.findAll()).isEmpty();
    }
}
