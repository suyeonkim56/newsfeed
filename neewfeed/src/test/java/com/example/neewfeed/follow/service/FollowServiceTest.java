package com.example.neewfeed.follow.service;


import com.example.neewfeed.auth.dto.AuthUser;
import com.example.neewfeed.follow.entity.Following;
import com.example.neewfeed.follow.repository.FollowingRepository;
import com.example.neewfeed.user.entity.User;
import com.example.neewfeed.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTest {

    @InjectMocks
    private FollowingService followingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowingRepository followingRepository;

    @Test
    void 팔로우_추가() {
        // given
        User user = new User("1234@1234", "test", "123456");
        ReflectionTestUtils.setField(user, "id", 1L);
        AuthUser authUser = new AuthUser(user.getId());

        User user2 = new User("5678@5678", "test2", "1234567");
        ReflectionTestUtils.setField(user2, "id", 2L);

        given(userRepository.findById(user2.getId())).willReturn(Optional.of(user2));

        Following following = new Following(user.getId(), user2);

        given(followingRepository.findAll()).willReturn(List.of(following));

        // when
        followingService.addFollow(authUser, user2.getId());

        // then
        List<Following> followings = followingRepository.findAll();
        assertThat(followings).hasSize(1);
        assertThat(followings.get(0).getFromId()).isEqualTo(user.getId());
        assertThat(followings.get(0).getToUser().getId()).isEqualTo(user2.getId());
    }
}
