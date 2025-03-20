package com.example.neewfeed.like.repository;

import com.example.neewfeed.like.entity.PostLike;
import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.post.repository.PostRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostLikeRepositoryTest {
    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Test
    void 유저와_게시물로_좋아요를_찾는다(){
        //given
        User user = new User("1234@1234","test","123asd!@#");
        userRepository.save(user);

        Post post = new Post("test1",user);
        postRepository.save(post);

        PostLike postLike = new PostLike(user,post);
        postLikeRepository.save(postLike);

        //when
        PostLike foundPostLike = postLikeRepository.findByUserAndPost(user,post).orElse(null);

        //then
        assertNotNull(foundPostLike);
        assertEquals(postLike.getUser(),foundPostLike.getUser());
    }
}
