package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailDto {

    private Long book_id;
    private String title;
    private Integer price;
    private String writer;
    private Timestamp write_date;
    private String book_img_path;
    private String book_summary;

}
