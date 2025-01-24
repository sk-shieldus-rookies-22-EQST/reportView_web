package com.skrookies.dahaezlge.controller.cart.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
public class CartDto {
    private Long cart_id;
    private String cart_user_id;
    private Integer cart_total_price;
}
