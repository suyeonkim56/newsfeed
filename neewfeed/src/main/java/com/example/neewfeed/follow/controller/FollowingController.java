package com.example.neewfeed.follow.controller;

import com.example.neewfeed.auth.annotation.Auth;
import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.follow.service.FollowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FollowingController {
    private final FollowingService followingService;

    //상대방 팔로우 하기
    @PostMapping("/addfollow/{userId}")
    public ResponseEntity<Map<String, String>> addFollow(
            @Auth AuthUser authUser,
            @PathVariable Long toUserId
            ){
        followingService.addFollow(authUser, toUserId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "팔로잉을 성공했습니다.");
        return new ResponseEntity<> (response, HttpStatus.OK);
    }
}
