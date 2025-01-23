package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookDetailDto {

    private Long book_id;
    private String book_title;
    private Integer book_price;
    private String book_auth;
    private String book_summary;
    private String book_img_path;

}
