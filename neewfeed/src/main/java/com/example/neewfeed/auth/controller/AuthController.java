package com.example.neewfeed.auth.controller;

import com.example.neewfeed.auth.dto.UserSignUpRequestDto;
import com.example.neewfeed.auth.dto.UserSignUpResponseDto;
import com.example.neewfeed.auth.dto.UserSigninRequestDto;
import com.example.neewfeed.auth.dto.UserSigninResponseDto;
import com.example.neewfeed.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponseDto> signUp(
            @Valid @RequestBody UserSignUpRequestDto requestDto
    ){
        UserSignUpResponseDto dto = authService.signUp(requestDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/signin")
    public ResponseEntity<UserSigninResponseDto> signIn(
            @RequestBody UserSigninRequestDto requestDto
    ){
        UserSigninResponseDto dto = authService.signIn(requestDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
