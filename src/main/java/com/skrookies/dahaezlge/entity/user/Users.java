package com.skrookies.dahaezlge.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    // users

    @Id
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "user_pw")
    private String user_pw;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "user_phone")
    private String user_phone;

    @Column(name = "user_level")
    private Integer user_level;

    @Column(name = "user_created_at")
    private Timestamp user_created_at;

    public Users(String user_id, String user_pw, String user_email, String user_phone, Integer user_level, Timestamp user_created_at){
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_level = user_level;
        this.user_created_at = user_created_at;
    }
}
