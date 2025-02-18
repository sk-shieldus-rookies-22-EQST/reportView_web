package com.skrookies.dahaezlge.entity.book;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long book_id;

    @Column(name = "book_title", nullable = false)
    private String book_title;

    @Column(name = "book_auth", nullable = false)
    private String book_auth;

    @Column(name = "book_path")
    private String book_path;

    @Column(name = "book_summary")
    private String book_summary;

    @Column(name = "book_reg_date")
    private LocalDateTime book_reg_date;

    @Column(name = "book_img_path")
    private String book_img_path;

    @Column(name = "book_price")
    private Integer book_price;

    public Book(String book_title, String book_auth, String book_path, String book_summary, LocalDateTime book_reg_date, String book_img_path, Integer book_price) {
        this.book_title = book_title;
        this.book_auth = book_auth;
        this.book_path = book_path;
        this.book_summary = book_summary;
        this.book_reg_date = book_reg_date;
        this.book_img_path = book_img_path;
        this.book_price = book_price;
    }
}

