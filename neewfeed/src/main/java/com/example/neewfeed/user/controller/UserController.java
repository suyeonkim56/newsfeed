package com.example.neewfeed.user.controller;


import com.example.neewfeed.auth.annotation.Auth;
import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.user.dto.UserNameUpdateRequestDto;
import com.example.neewfeed.user.dto.UserPasswordUpdateRequestDto;
import com.example.neewfeed.user.dto.UserPrivateResponseDto;
import com.example.neewfeed.user.dto.UserResponseDto;
import com.example.neewfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findById(
            @Auth AuthUser authUser,
            @PathVariable Long userId){
        UserResponseDto dto = userService.findById(authUser, userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    //프로필 이름 수정
    @PutMapping("/updatename")
    public ResponseEntity<UserPrivateResponseDto> updateName(
            @Auth AuthUser authUser,
            @RequestBody UserNameUpdateRequestDto requestDto
    ){
        UserPrivateResponseDto dto = userService.updateName(authUser, requestDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //프로필 비밀번호 수정
    @PutMapping("/updatepassword")
    public ResponseEntity<Map<String, String>> updatePassword(
            @Auth AuthUser authUser,
            @Valid @RequestBody UserPasswordUpdateRequestDto requestDto
            ){
        userService.updatePassword(authUser, requestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "비밀번호가 성공적으로 변경되었습니다.");

        return ResponseEntity.ok(response);
    }

}
