package com.example.neewfeed.like.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.like.entity.PostLike;
import com.example.neewfeed.like.repository.PostLikeRepository;
import com.example.neewfeed.post.dto.PostCreateRequestDto;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostLikeServiceTest {
    @InjectMocks
    private PostLikeService postLikeService;

    @Mock
    private PostLikeRepository postLikeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void 정상적인_게시물_좋아요_생성(){
        //given
        User writer = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 1L);

        User liker = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 2L);

        AuthUser authUser = new AuthUser(2L);

        Post post = new Post("test",writer);
        ReflectionTestUtils.setField(post,"id",1L);

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(liker));

        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));

        given(postLikeRepository.findByUserAndPost(liker,post)).willReturn(Optional.empty());

        //when
        postLikeService.createPostLike(authUser,post.getId());

        //then
        assertThat(post.getLikeCount()).isEqualTo(1);
    }

    @Test
    void 정상적인_게시물_좋아요_삭제(){
        //given
        User writer = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 1L);

        User liker = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(writer, "id", 2L);

        AuthUser authUser = new AuthUser(2L);

        PostCreateRequestDto requestDto = new PostCreateRequestDto();
        requestDto.setContents("test");

        Post post = new Post(requestDto.getContents(),writer);
        ReflectionTestUtils.setField(post,"id",1L);
        ReflectionTestUtils.setField(post,"likeCount",1);
        PostLike postLike = new PostLike(liker,post);

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(liker));

        given(postRepository.findById(post.getId())).willReturn(Optional.of(post));

        given(postLikeRepository.findById(postLike.getId())).willReturn(Optional.of(postLike));

        //when
        postLikeService.deletePostLike(authUser,post.getId(),postLike.getId());

        //then
        assertThat(post.getLikeCount()).isEqualTo(0);
    }
}
