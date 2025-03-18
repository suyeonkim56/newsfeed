package com.example.neewfeed.like.repository;

import com.example.neewfeed.comment.entity.Comment;
import com.example.neewfeed.like.entity.CommentLike;
import com.example.neewfeed.user.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
