package com.example.neewfeed.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    String email;

    @Column(name = "name", nullable = false, length = 30)
    String name;

    @Column(name = "password", nullable = false)
    String password;

    @CreatedDate
    @Column(name = "createdAt", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateName(String newname) {
        this.name = newname;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
    }
}
