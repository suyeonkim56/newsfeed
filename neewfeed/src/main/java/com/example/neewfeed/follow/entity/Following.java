package com.example.neewfeed.follow.entity;

import com.example.neewfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "following")
@NoArgsConstructor
@Getter
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
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
