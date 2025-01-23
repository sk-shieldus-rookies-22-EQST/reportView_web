package com.skrookies.dahaezlge.controller.user.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterDto {
    private String user_id;
    private String user_pw;
    private String user_phone;
    private String user_email;
    private String user_level;
    private String user_created_at;
}
