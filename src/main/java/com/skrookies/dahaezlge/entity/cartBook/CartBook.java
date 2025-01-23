package com.skrookies.dahaezlge.entity.cartBook;

import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.cart.Cart;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "cart_book")
public class CartBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_book_id")
    private Long cart_book_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_book_book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_book_cart_id", nullable = false)
    private Cart cart;

    public CartBook(Book book, Cart cart) {
        this.book = book;
        this.cart = cart;
    }
}


