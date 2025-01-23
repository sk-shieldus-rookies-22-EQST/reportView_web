package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookSearchDto {

    private Long book_id;
    private String book_title;
    private String book_price;
    private String book_auth;
    private LocalDateTime book_reg_date;
    private String book_img_path;
}
