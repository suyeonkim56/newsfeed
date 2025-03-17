package com.example.neewfeed.follow.repository;

import com.example.neewfeed.follow.entity.Following;
import com.example.neewfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    List<User> findbyfromId(Long id);
}
