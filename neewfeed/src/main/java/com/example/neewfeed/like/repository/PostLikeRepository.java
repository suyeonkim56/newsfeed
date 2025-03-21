package com.example.neewfeed.like.repository;

import com.example.neewfeed.like.entity.PostLike;
import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.user.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
