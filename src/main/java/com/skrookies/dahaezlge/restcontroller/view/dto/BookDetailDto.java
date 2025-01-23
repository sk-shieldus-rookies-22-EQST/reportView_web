package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookDetailDto {

    private Long id;
    private String title;
    private String price;
    private String write;
    private String detail;
    private String img;

}
