package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookListDto {

    private Long book_id;
    private String book_title;
    private String book_price;
    private String book_auth;
}
