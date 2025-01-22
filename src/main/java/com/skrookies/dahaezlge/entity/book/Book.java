package com.skrookies.dahaezlge.entity.book;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer book_id;

    @Column(name = "book_title", length = 255)
    private String book_title;

    @Column(name = "book_auth", length = 255)
    private String book_auth;

    @Column(name = "book_path", length = 255)
    private String book_path;

    @Column(name = "book_summary", columnDefinition = "TEXT")
    private String book_summary;

    @Column(name = "book_reg_date")
    private LocalDateTime book_reg_date;

    @Column(name = "book_img_path", length = 255)
    private String book_img_path;

    public Book(Integer book_id, String book_title, String book_auth, String book_path,
                String book_summary, LocalDateTime book_reg_date, String book_img_path) {
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_auth = book_auth;
        this.book_path = book_path;
        this.book_summary = book_summary;
        this.book_reg_date = book_reg_date;
        this.book_img_path = book_img_path;
    }

}
