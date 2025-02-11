package com.skrookies.dahaezlge.controller.user.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String user_id;
    private String user_pw;
    private String re_user_pw;
    private String user_phone;
    private String user_email;
    private String user_level;
    private String user_created_at;
    private String user_agree;
}
