package com.skrookies.dahaezlge.controller.user.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FinduseridDto {
    private String user_id;
    private String user_phone;
    private String user_phone_pw;
    private String user_email;
    private String new_user_pw;
    private String re_new_user_pw;
}
