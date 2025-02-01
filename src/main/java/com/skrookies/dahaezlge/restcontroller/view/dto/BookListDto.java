package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookListDto {

    private Long book_id;
    private String title;
    private Integer price;
    private String writer;
    private LocalDateTime write_date;
    private String book_img_path;
}
