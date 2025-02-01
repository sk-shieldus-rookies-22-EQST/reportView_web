package com.skrookies.dahaezlge.restcontroller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private String user_id;
    private String user_pw;
    private String user_phone;
    private String user_email;
    private Integer user_level;
    private LocalDateTime user_created_at;
}
