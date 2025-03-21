package com.example.neewfeed.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.post.dto.PostCreateRequestDto;
import com.example.neewfeed.post.dto.PostCreateResponseDto;
import com.example.neewfeed.post.dto.PostResponseDto;
import com.example.neewfeed.post.dto.PostUpdateRequestDto;
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
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void 정상적인_게시물_생성(){
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        PostCreateRequestDto requestDto = new PostCreateRequestDto();
        requestDto.setContents("test");

        Post post = new Post(requestDto.getContents(),user);
        ReflectionTestUtils.setField(post,"id",1L);

        //when
        PostCreateResponseDto responseDto = new PostCreateResponseDto(
                post.getId(),
                post.getContents(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );

        //then
        assertThat(responseDto.getId()).isEqualTo(post.getId());
        assertThat(responseDto.getContents()).isEqualTo(post.getContents());

    }

    @Test
    void 정상적인_게시물_단일조회(){
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        Post post = new Post("test",user);
        ReflectionTestUtils.setField(post,"id",1L);

        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        //when
        PostResponseDto findPost = postService.findById(1L);

        //then
        assertThat(findPost.getId()).isEqualTo(post.getId());
        assertThat(findPost.getContent()).isEqualTo(post.getContents());

    }

    @Test
    void 정상적인_게시물_수정(){
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        Post post = new Post("contents", user);
        ReflectionTestUtils.setField(post, "id", 1L);

        PostUpdateRequestDto requestDto = new PostUpdateRequestDto();
        requestDto.setNewcontents("new");

        given(postRepository.findById(1L)).willReturn(Optional.of(post));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        PostResponseDto result = postService.updatePost(1L,authUser,requestDto);

        //then
        assertThat(result.getContent()).isEqualTo(requestDto.getNewcontents());


    }

    @Test
    void 정상적인_게시물_삭제(){
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        Post post = new Post("contents", user);
        ReflectionTestUtils.setField(post, "id", 1L);


        //when
        postRepository.delete(post);

        //then
        assertThat(postRepository.findAll()).isEmpty();

    }
}
