package com.skrookies.dahaezlge.entity.autoLogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auto_login")
public class AutoLogin {

    @Id
    @Column(name = "auto_login_user_id", length = 255)
    private String autoLoginUserId;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "uuid", length = 255)
    private String uuid;

    @Column(name = "token_gen_date")
    private Timestamp tokenGenDate;
}
