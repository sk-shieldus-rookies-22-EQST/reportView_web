package com.skrookies.dahaezlge.restcontroller.user.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class MyBookListDto {

    private Long book_id;
    private String title;
    private String writer;
    private String book_img_path;
}
