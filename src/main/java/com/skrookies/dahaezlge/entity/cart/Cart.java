package com.skrookies.dahaezlge.entity.cart;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Integer cart_id;

    @Column(name = "cart_user_id")
    private String cart_user_id;

    @Column(name = "cart_total_price")
    private Integer cart_total_price;

    public Cart(Integer cart_id, String cart_user_id, Integer cart_total_price) {
        this.cart_id = cart_id;
        this.cart_user_id = cart_user_id;
        this.cart_total_price = cart_total_price;
    }
}


