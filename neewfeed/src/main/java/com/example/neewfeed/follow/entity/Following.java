package com.example.neewfeed.follow.entity;

import com.example.neewfeed.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "following")
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "from_id",nullable = false)
    private Long fromId;

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    private User toUser;

    public Following(Long fromId, User toUser)
    {
        this.createdAt = LocalDateTime.now();
        this.fromId = fromId;
        this.toUser = toUser;
    }


}
