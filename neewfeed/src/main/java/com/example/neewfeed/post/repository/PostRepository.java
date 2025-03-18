package com.example.neewfeed.post.repository;

import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //본인 및 팔로워로 게시물 찾기
    @Query("SELECT p FROM Post p WHERE p.user IN :followers order by p.createdAt desc ")
    Page<Post> findByFollowers(
            @Param("followers") List<User> followers,
            Pageable pageable
    );

    //수정일을 기준으로 일정 찾기
    @Query("select p from Post p where p.updatedAt >= :startDate And p.updatedAt<= :endDate order by p.updatedAt desc")
    Page<Post> findPostsBetweenDates(
            Pageable pageable,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
