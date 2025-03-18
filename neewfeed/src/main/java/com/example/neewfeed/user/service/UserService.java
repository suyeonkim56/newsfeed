package com.example.neewfeed.user.service;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.auth.dto.UserSignUpRequestDto;
import com.example.neewfeed.auth.dto.UserSignUpResponseDto;
import com.example.neewfeed.common.config.PasswordEncoder;
import com.example.neewfeed.user.dto.*;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //유저 단독 조회
    @Transactional(readOnly = true)
    public UserResponseDto findById(AuthUser user, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        //본인인 경우
        if (user.getId().equals(findUser.getId())) {
            return new UserPrivateResponseDto(
                    findUser.getId(),
                    findUser.getEmail(),
                    findUser.getName(),
                    findUser.getCreatedAt(),
                    findUser.getUpdatedAt()
            );
        }
        //타인인 경우
        else {
            return new UserPublicResponseDto(findUser.getId(), findUser.getEmail());
        }
    }

    //유저 이름 변경
    @Transactional
    public UserPrivateResponseDto updateName(AuthUser authUser, UserNameUpdateRequestDto requestDto) {
        User findUser = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        if (findUser.getName().equals(requestDto.getNewname())) {
            throw new IllegalStateException("기존과 동일한 이름으로 바꿀 수 없습니다.");
        }
        findUser.updateName(requestDto.getNewname());

        return new UserPrivateResponseDto(
                findUser.getId(),
                findUser.getEmail(),
                findUser.getName(),
                findUser.getCreatedAt(),
                findUser.getUpdatedAt()
        );
    }

    //비밀번호 변경
    @Transactional
    public void updatePassword(AuthUser authUser, UserPasswordUpdateRequestDto requestDto) {
        User findUser = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        if (passwordEncoder.matches(requestDto.getNewPassword(), findUser.getPassword())) {
            throw new RuntimeException("기존과 동일한 비밀번호로 바꿀 수 없습니다.");
        }
        if (!passwordEncoder.matches(requestDto.getOldPassword(), findUser.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        findUser.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));

    }
}
