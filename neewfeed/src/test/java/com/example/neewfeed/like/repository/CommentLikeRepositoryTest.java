package com.example.neewfeed.like.repository;

import com.example.neewfeed.comment.entity.Comment;
import com.example.neewfeed.comment.repository.CommentRepository;
import com.example.neewfeed.like.entity.CommentLike;
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
public class CommentLikeRepositoryTest {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 유저와_댓글로_댓글좋아요를_찾는다() {
        //given
        User user = new User("1234@1234", "test", "123asd!@#");
        userRepository.save(user);

        Post post = new Post("test1", user);
        postRepository.save(post);

        Comment comment = new Comment("test", user, post);
        commentRepository.save(comment);

        CommentLike commentLike = new CommentLike(user,comment);
        commentLikeRepository.save(commentLike);

        //when
        CommentLike foundCommentLike = commentLikeRepository.findByUserAndComment(user,comment).orElse(null);

        //then
        assert foundCommentLike != null;
        assertEquals(commentLike.getId(),foundCommentLike.getId());

    }
}
