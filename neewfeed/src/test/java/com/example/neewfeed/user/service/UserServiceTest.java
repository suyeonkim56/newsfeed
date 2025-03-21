package com.example.neewfeed.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.common.config.PasswordEncoder;
import com.example.neewfeed.user.dto.*;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder encoder;

    @Test
    void 정상적인_본인_단독_조회() {
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        UserPrivateResponseDto responseDto = new UserPrivateResponseDto(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt(),
                user.getUpdatedAt());
        //when
        UserResponseDto result = userService.findById(authUser, user.getId());

        //then
        assertThat(result.getId()).isEqualTo(responseDto.getId());
        assertThat(result.getName()).isEqualTo(responseDto.getName());
    }

    @Test
    void 정상적인_타인_단독_조회() {
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        User user2 = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 2L);

        AuthUser authUser = new AuthUser(1L);

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        UserPublicResponseDto responseDto = new UserPublicResponseDto(user.getId(),
                user.getEmail());
        //when
        UserResponseDto result = userService.findById(authUser, user.getId());

        //then
        assertThat(result.getId()).isEqualTo(responseDto.getId());
    }

    @Test
    void 정상적인_유저_이름_변경() {
        //given
        User user = new User("1234@1234", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        UserNameUpdateRequestDto requestDto = new UserNameUpdateRequestDto();
        requestDto.setNewname("newtest");

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        //when
        UserPrivateResponseDto result = userService.updateName(authUser,requestDto);

        //then
        assertThat(result.getName()).isEqualTo("newtest");
    }

    @Test
    void 정상적인_유저_비밀번호_변경() {
        //given
        String rawOldPassword = "a123456!!";
        String rawNewPassword = "as123456!!";
        String encodedPassword = encoder.encode(rawOldPassword);

        User user = new User("1234@1234", "test", encodedPassword);
        ReflectionTestUtils.setField(user, "id", 1L);

        AuthUser authUser = new AuthUser(1L);

        UserPasswordUpdateRequestDto requestDto = new UserPasswordUpdateRequestDto();
        requestDto.setOldPassword(rawOldPassword);
        requestDto.setNewPassword(rawNewPassword);

        given(userRepository.findById(authUser.getId())).willReturn(Optional.of(user));

        // when
        userService.updatePassword(authUser, requestDto);

        // then
        assertThat(encoder.matches(rawNewPassword, user.getPassword())).isTrue();
    }
}
