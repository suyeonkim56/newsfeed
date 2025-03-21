package com.example.neewfeed.user.repository;

import com.example.neewfeed.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_사용자를_조회할_수_있다(){
        //given
        String email = "1234@1234.com";
        User user = new User(email, "test","a123456!!");
        userRepository.save(user);

        //when
        User foundUser = userRepository.findByEmail(email).orElse(null);

        //then
        assertNotNull(foundUser);
        assertEquals(email,foundUser.getEmail());
    }

    @Test
    void 이메일로_사용자가_존재하는지_확인한다(){
        //given
        String email = "1234@1234.com";
        User user = new User(email, "test","a123456!!");
        userRepository.save(user);

        //when
        boolean isExist = userRepository.existsByEmail(email);

        //then
        assertTrue(isExist);
    }
}
