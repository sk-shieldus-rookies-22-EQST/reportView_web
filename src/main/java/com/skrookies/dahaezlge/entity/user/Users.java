package com.skrookies.dahaezlge.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "user_pw", nullable = false)
    private String user_pw;

    @Column(name = "user_phone", nullable = false)
    private String user_phone;

    @Column(name = "user_email", nullable = false)
    private String user_email;

    @Column(name = "user_level")
    private Integer user_level;

    @Column(name = "user_created_at")
    private Timestamp user_created_at;

    public Users(String user_id, String user_pw, String user_phone, String user_email, Integer user_level, Timestamp user_created_at) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_level = user_level;
        this.user_created_at = user_created_at;
    }
}
