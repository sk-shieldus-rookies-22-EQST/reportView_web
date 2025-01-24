package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookListDto {

    private Long book_id;
    private String book_title;
    private Integer book_price;
    private String book_auth;
}
