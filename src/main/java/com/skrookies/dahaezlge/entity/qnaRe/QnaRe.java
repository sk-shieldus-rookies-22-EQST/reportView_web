package com.skrookies.dahaezlge.entity.qnaRe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaRe {

    @Id
    @GeneratedValue
    @Column(name = "qna_re_id")
    private Integer qna_re_id;

    @Column(name = "qna_re_user_id")
    private String qna_re_user_id;

    @Column(name = "qna_re_body")
    private String qna_re_body;

    @Column(name = "qna_re_created_at")
    private LocalDate qna_re_created_at;

    public QnaRe(Integer qna_re_id, String qna_re_user_id, String qna_re_body, LocalDate qna_re_created_at) {
        this.qna_re_id = qna_re_id;
        this.qna_re_user_id = qna_re_user_id;
        this.qna_re_body = qna_re_body;
        this.qna_re_created_at = qna_re_created_at;
    }
}


