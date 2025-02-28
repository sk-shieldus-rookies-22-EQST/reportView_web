package com.skrookies.dahaezlge.entity.purchase;

import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.user.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_cart_id")
    private Long purchase_cart_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_user_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_book_id", nullable = false)
    private Book book;

    @Column(name = "purchase_date", nullable = false)
    private Timestamp purchase_date;

    public Purchase(Users users, Book book, Timestamp purchase_date) {
        this.users = users;
        this.book = book;
        this.purchase_date = purchase_date;
    }
}



