package com.skrookies.dahaezlge.controller.user.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginDto {

    private String user_id;
    private String user_pw;
}
