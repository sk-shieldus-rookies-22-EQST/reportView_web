package com.skrookies.dahaezlge.controller.user.Dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDto {
    private String user_id;
    private String user_pw;
    private String user_phone;
    private String user_email;
    private String user_level;
    private String user_created_at;
    private String user_agree;
}
