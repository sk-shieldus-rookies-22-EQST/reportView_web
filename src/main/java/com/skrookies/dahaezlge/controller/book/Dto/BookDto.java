package com.skrookies.dahaezlge.controller.book.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long book_id;
    private String book_title;
    private String book_auth;
    private String book_path;
    private String book_summary;
    private Timestamp book_reg_date;
    private String book_img_path;
    private Integer book_price;
}
