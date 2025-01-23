package com.skrookies.dahaezlge.restcontroller.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindPwDto {

    private String user_id;
    private String user_phone;
    private String user_email;

}
