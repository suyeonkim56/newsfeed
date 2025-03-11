package com.example.neewfeed.user.entity;

import com.example.neewfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    String email;

    @Column(name = "name", nullable = false, length = 30)
    String name;

    @Column(name = "password", nullable = false, length = 16)
    String password;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void updateName(String newname) {
        this.name = newname;
    }

    public void updatePassword(String password)
    {
        this.password = password;
    }
}
