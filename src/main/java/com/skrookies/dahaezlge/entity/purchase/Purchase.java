package com.skrookies.dahaezlge.entity.purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {

    @Id
    @GeneratedValue
    @Column(name = "purchase_cart_id")
    private Integer purchase_cart_id;

    @Column(name = "purchase_user_id")
    private String purchase_user_id;

    @Column(name = "purchase_book_id")
    private Integer purchase_book_id;

    @Column(name = "purchase_date")
    private LocalDateTime purchase_date;

    public Purchase(Integer purchase_cart_id, String purchase_user_id, Integer purchase_book_id, LocalDateTime purchase_date) {
        this.purchase_cart_id = purchase_cart_id;
        this.purchase_user_id = purchase_user_id;
        this.purchase_book_id = purchase_book_id;
        this.purchase_date = purchase_date;
    }
}


