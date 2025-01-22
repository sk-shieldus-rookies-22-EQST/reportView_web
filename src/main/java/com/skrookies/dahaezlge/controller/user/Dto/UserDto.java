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

    public UserDto(String user_id, String user_pw) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_level = user_level;
        this.user_created_at = user_created_at;
    }
}
