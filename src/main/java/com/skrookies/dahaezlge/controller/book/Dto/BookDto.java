package com.skrookies.dahaezlge.controller.book.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Integer book_id;
    private String book_title;
    private String book_auth;
    private String book_path;
    private String book_summary;
    private String book_reg_date;
    private String book_image_path;
    private String book_price;
}
