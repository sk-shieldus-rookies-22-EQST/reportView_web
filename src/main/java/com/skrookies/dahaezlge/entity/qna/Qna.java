package com.skrookies.dahaezlge.entity.qna;

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
public class Qna {

    @Id
    @GeneratedValue
    @Column(name = "qna_id")
    private Integer qna_id;

    @Column(name = "qna_title")
    private String qna_title;

    @Column(name = "qna_body")
    private String qna_body;

    @Column(name = "qna_user_id")
    private String qna_user_id;

    @Column(name = "qna_created_at")
    private LocalDateTime qna_created_at;

    public Qna(Integer qna_id, String qna_title, String qna_body, String qna_user_id, LocalDateTime qna_created_at) {
        this.qna_id = qna_id;
        this.qna_title = qna_title;
        this.qna_body = qna_body;
        this.qna_user_id = qna_user_id;
        this.qna_created_at = qna_created_at;
    }
}


