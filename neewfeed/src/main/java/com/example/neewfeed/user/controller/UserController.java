package com.example.neewfeed.user.controller;


import com.example.neewfeed.common.config.JwtUtil;
import com.example.neewfeed.user.dto.UserNameUpdateRequestDto;
import com.example.neewfeed.user.dto.UserPasswordUpdateRequestDto;
import com.example.neewfeed.user.dto.UserPrivateResponseDto;
import com.example.neewfeed.user.dto.UserResponseDto;
import com.example.neewfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //프로필 조회
    @GetMapping("/check/{userId}")
    public ResponseEntity<UserResponseDto> findById(
            @RequestHeader(name = "Authorization") String authorization,
            @PathVariable Long userId) {
        // JWT에서 userId 추출
        Long loginUserId = JwtUtil.extractUserId(authorization);

        UserResponseDto dto = userService.findById(loginUserId, userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    //프로필 이름 수정
    @PutMapping("/updatename")
    public ResponseEntity<UserPrivateResponseDto> updateName(
            @RequestHeader(name = "Authorization") String authorization,
            @RequestBody UserNameUpdateRequestDto requestDto
    ) {
        // JWT에서 userId 추출
        Long loginUserId = JwtUtil.extractUserId(authorization);

        UserPrivateResponseDto dto = userService.updateName(loginUserId, requestDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //프로필 비밀번호 수정
    @PutMapping("/updatepassword")
    public ResponseEntity<Map<String, String>> updatePassword(
            @RequestHeader(name = "Authorization") String authorization,
            @Valid @RequestBody UserPasswordUpdateRequestDto requestDto
    ) {
        // JWT에서 userId 추출
        Long loginUserId = JwtUtil.extractUserId(authorization);

        userService.updatePassword(loginUserId, requestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "비밀번호가 성공적으로 변경되었습니다.");

        return ResponseEntity.ok(response);
    }

}
