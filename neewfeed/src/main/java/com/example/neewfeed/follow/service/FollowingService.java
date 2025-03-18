package com.example.neewfeed.follow.service;

import com.example.neewfeed.follow.entity.Following;
import com.example.neewfeed.follow.repository.FollowingRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowingService {
    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFollow(AuthUser authUser, Long toUserId) {
        User findToUser = userRepository.findById(toUserId).orElseThrow(()->new IllegalStateException("존재하지 않는 유저입니다."));

        Following newFollower = new Following(fromId,findToUser);
        followingRepository.save(newFollower);
    }
}
