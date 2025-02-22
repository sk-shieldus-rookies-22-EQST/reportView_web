package com.skrookies.dahaezlge.entity.autoLogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auto_login")
public class AutoLogin {

    @Id
    @Column(name = "user_id", length = 255)
    private String userId;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "token_gen_date")
    private LocalDateTime tokenGenDate;


}
