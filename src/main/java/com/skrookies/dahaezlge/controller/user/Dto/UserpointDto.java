package com.skrookies.dahaezlge.controller.user.Dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserpointDto {
    private String point_user_id;
    private Integer point;
}

