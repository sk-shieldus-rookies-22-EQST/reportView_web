package com.skrookies.dahaezlge.restcontroller.purchase.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseCartDto {

    private Long cart_id;
    private Long book_id;
    private String title;
    private Integer price;
    private String book_img_path;
}
