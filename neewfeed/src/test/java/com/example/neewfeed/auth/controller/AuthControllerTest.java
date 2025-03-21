package com.example.neewfeed.auth.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.neewfeed.auth.dto.UserSignUpRequestDto;
import com.example.neewfeed.auth.dto.UserSignUpResponseDto;
import com.example.neewfeed.auth.dto.UserSigninRequestDto;
import com.example.neewfeed.auth.dto.UserSigninResponseDto;
import com.example.neewfeed.auth.service.AuthService;
import com.example.neewfeed.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 정상적인_회원가입() throws Exception {
        //given
        UserSignUpRequestDto requestDto = new UserSignUpRequestDto();
        requestDto.setName("test");
        requestDto.setPassword("a123456!!");
        requestDto.setEmail("test@email.com");
        UserSignUpResponseDto responseDto = new UserSignUpResponseDto(1L,"test","email@test", LocalDateTime.now(),LocalDateTime.now());
        given(authService.signUp(requestDto)).willReturn(responseDto);

        //when&then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void 정상적인_로그인() throws Exception{
        //given
        User user = new User("test@email.com", "test", "a123456!!");
        ReflectionTestUtils.setField(user, "id", 1L);

        UserSigninRequestDto requestDto = new UserSigninRequestDto();
        requestDto.setEmail("test@email.com");
        requestDto.setPassword("a123456!!");

        UserSigninResponseDto responseDto = new UserSigninResponseDto("asdfewqfefasdfefqwfewfdasfefq");
        given(authService.signIn(requestDto)).willReturn(responseDto);

        //when&then
        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }
}
