package com.example.neewfeed.comment.repository;

import com.example.neewfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM comment c ORDER BY c.id DESC")
    List<Comment> findAllDesc();
}
