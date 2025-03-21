package com.example.neewfeed.like.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.comment.entity.Comment;
import com.example.neewfeed.comment.repository.CommentRepository;
import com.example.neewfeed.like.entity.CommentLike;
import com.example.neewfeed.like.repository.CommentLikeRepository;
import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentLikeServiceTest {
    @InjectMocks
    private CommentLikeService commentLikeService;

    @Mock
    private CommentLikeRepository commentLikeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void 정상적인_댓글_좋아요_생성(){
        //given
        User writer = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 1L);

        User liker = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 2L);

        AuthUser authUser = new AuthUser(2L);

        Post post = new Post("test",writer);
        ReflectionTestUtils.setField(post,"id",1L);

        Comment comment = new Comment("test",writer,post);

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(liker));
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        given(commentLikeRepository.findByUserAndComment(liker,comment)).willReturn(Optional.empty());

        //when
        commentLikeService.createCommentLike(authUser,comment.getId());

        //then
        assertThat(comment.getLikeCount()).isEqualTo(1);


    }

    @Test
    void 정상적인_댓글_좋아요_삭제(){
        //given
        User writer = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 1L);

        User liker = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 2L);

        AuthUser authUser = new AuthUser(2L);

        Post post = new Post("test",writer);
        ReflectionTestUtils.setField(post,"id",1L);

        Comment comment = new Comment("test",writer,post);
        ReflectionTestUtils.setField(comment,"likeCount",1);

        CommentLike commentLike = new CommentLike(liker,comment);

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(liker));
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        given(commentLikeRepository.findById(commentLike.getId())).willReturn(Optional.of(commentLike));

        //when
        commentLikeService.deleteCommentLike(authUser,comment.getId(),commentLike.getId());

        //then
        assertThat(comment.getLikeCount()).isEqualTo(0);

    }
}
