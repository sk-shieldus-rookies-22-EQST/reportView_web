package com.skrookies.dahaezlge.restcontroller.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyBookListDto {

    private Long book_id;
    private String title;
    private String writer;
    private String book_img_path;
}
