package com.example.neewfeed.post.entity;

import com.example.neewfeed.common.entity.BaseEntity;
import com.example.neewfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="post")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content", nullable = false, length = 200)
    private String contents;

    @Column(name = "like_count")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String contents, User user)
    {
        this.contents = contents;
        this.user = user;
        likeCount = 0;
    }

    public void updateContents(String newcontents) {
        this.contents = newcontents;
    }
}
