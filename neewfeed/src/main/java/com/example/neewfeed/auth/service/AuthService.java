package com.example.neewfeed.auth.service;


import com.example.neewfeed.auth.dto.UserSignUpRequestDto;
import com.example.neewfeed.auth.dto.UserSignUpResponseDto;
import com.example.neewfeed.auth.dto.UserSigninRequestDto;
import com.example.neewfeed.auth.dto.UserSigninResponseDto;
import com.example.neewfeed.common.config.JwtUtil;
import com.example.neewfeed.common.config.PasswordEncoder;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //회원가입
    @Transactional
    public UserSignUpResponseDto signUp(@Valid UserSignUpRequestDto requestDto){
        User user = new User(requestDto.getEmail(), requestDto.getName(), passwordEncoder.encode(requestDto.getPassword()));
        if(userRepository.existsByEmail(requestDto.getEmail()))
        {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
        User saveuser = userRepository.save(user);

        return UserSignUpResponseDto.buildDto(user);
    }

    //로그인
    @Transactional(readOnly = true)
    public UserSigninResponseDto signIn(UserSigninRequestDto requestDto){
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );

        String password = requestDto.getPassword();
        if (!passwordEncoder.matches(password,user.getPassword())) {
            throw new IllegalStateException("비밀번호가 잘못되었습니다.");
        }

        // 비밀번호가 일치한 경우
        String bearerJwt = jwtUtil.createToken(user.getId());
        return new UserSigninResponseDto(bearerJwt);
    }
}
