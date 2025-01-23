package com.skrookies.dahaezlge.controller.cart.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private Integer cart_id;
    private String cart_user_id;
    private Integer cart_total_price;
}
