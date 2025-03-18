package com.example.neewfeed.post.entity;

import com.example.neewfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {
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

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    public Post(String contents, User user) {
        this.contents = contents;
        this.user = user;
        likeCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContents(String newContents) {
        this.contents = newContents;
        this.updatedAt = LocalDateTime.now();
    }

    public void addLike() {
        likeCount++;
        updatedAt = LocalDateTime.now();
    }

    public void minusLike() {
        if (likeCount > 0) {
            likeCount--;
        }
        updatedAt = LocalDateTime.now();
    }
}
