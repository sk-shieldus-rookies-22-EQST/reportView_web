package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private String price;
    private String writer;
    private Timestamp write_date;
}
