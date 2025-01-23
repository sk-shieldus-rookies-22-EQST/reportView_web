package com.skrookies.dahaezlge.restcontroller.signup.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignDto {

    private String user_id;
    private String user_pw;
    private String user_phone;
    private String user_email;

}
