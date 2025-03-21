package com.example.neewfeed.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.neewfeed.auth.dto.UserSignUpRequestDto;
import com.example.neewfeed.auth.dto.UserSignUpResponseDto;
import com.example.neewfeed.auth.dto.UserSigninRequestDto;
import com.example.neewfeed.auth.dto.UserSigninResponseDto;
import com.example.neewfeed.common.config.JwtUtil;
import com.example.neewfeed.common.config.PasswordEncoder;
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
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Test
    void 정상적인_회원가입() {
        //given
        UserSignUpRequestDto dto = new UserSignUpRequestDto();
        dto.setName("test");
        dto.setEmail("1234@1234");
        dto.setPassword("asdf1234!");

        //when
        UserSignUpResponseDto responseDto = authService.signUp(dto);

        //then
        assertThat(responseDto.getName()).isEqualTo(dto.getName());
        assertThat(responseDto.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    void 정상적인_로그인() {
        //given
        String password = "a123456!!";
        String encodingPassword = passwordEncoder.encode(password);
        User user = new User("1234@1234", "test", encodingPassword);
        ReflectionTestUtils.setField(user, "id", 1L);

        UserSigninRequestDto requestDto = new UserSigninRequestDto();
        requestDto.setEmail(user.getEmail());
        requestDto.setPassword(password);

        System.out.println("user.email : " + user.getEmail() + " user.password : " + user.getPassword());
        System.out.println("request.email : " + requestDto.getEmail() + " request.password : " + requestDto.getPassword());

        given(jwtUtil.createToken(user.getId())).willReturn("1234");

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        //when
        UserSigninResponseDto responseDto = authService.signIn(requestDto);

        //then
        assertThat(responseDto.getBearerJwt()).isNotEmpty();
    }


}
