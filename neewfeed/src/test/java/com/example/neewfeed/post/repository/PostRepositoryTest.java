package com.example.neewfeed.post.repository;

import com.example.neewfeed.post.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    void 본인_및_팔로워의_게시물만_가져온다(){

    }

    @Test
    void 원하는_기간의_게시물만_가져온다(){

    }

    @Test
    void 수정일을_기준으로_정렬한다(){

    }
}
