package com.skrookies.dahaezlge.restcontroller.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPurchaseDto {

    private Long book_id;
    private String title;
    private String writer;
    private int price;
}
