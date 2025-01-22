package com.skrookies.dahaezlge.controller.user.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FinduseridDto {
    private String user_phone;
    private String user_email;
}
