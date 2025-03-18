package com.example.neewfeed.comment.entity;

import com.example.neewfeed.post.entity.Post;
import com.example.neewfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "content")
    String content;

    @Column(name = "like_count")
    int likeCount;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    Post post;

    public Comment(String content, User user, Post post)
    {
        this.content = content;
        likeCount = 0;
        this.user = user;
        this.post = post;
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    public void updateContent(String newContent) {
        this.content = newContent;
        updatedAt = LocalDateTime.now();
    }

    public void addLike() {
        updatedAt = LocalDateTime.now();
        likeCount++;
    }

    public void minusLike() {
        updatedAt = LocalDateTime.now();
        if(likeCount > 0)
        {
            likeCount--;
        }
    }
}
