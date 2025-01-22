package com.skrookies.dahaezlge.entity.price;

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
public class Price {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Integer book_id;

    @Column(name = "book_price")
    private String book_price;

    public Price(Integer book_id, String book_price) {
        this.book_id = book_id;
        this.book_price = book_price;
    }
}


