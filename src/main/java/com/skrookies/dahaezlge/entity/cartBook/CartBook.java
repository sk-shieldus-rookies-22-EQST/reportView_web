package com.skrookies.dahaezlge.entity.cartBook;

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
public class CartBook {

    @Id
    @GeneratedValue
    @Column(name = "cart_book_id")
    private Integer cart_book_id;

    @Column(name = "cart_book_book_id")
    private Integer cart_book_book_id;

    public CartBook(Integer cart_book_id, Integer cart_book_book_id) {
        this.cart_book_id = cart_book_id;
        this.cart_book_book_id = cart_book_book_id;
    }
}

