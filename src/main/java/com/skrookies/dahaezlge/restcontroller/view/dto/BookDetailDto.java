package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.*;

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
    private LocalDateTime write_date;
    private String book_img_path;
    private String book_summary;

}
