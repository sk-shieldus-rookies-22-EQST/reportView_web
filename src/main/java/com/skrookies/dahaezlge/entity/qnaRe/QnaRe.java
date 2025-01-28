package com.skrookies.dahaezlge.entity.qnaRe;

import com.skrookies.dahaezlge.entity.qna.Qna;
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
@Table(name = "qna_re")
public class QnaRe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_re_id")
    private Long qna_re_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_re_user_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id", nullable = false)
    private Qna qna;

    @Column(name = "qna_re_body", nullable = false)
    private String qna_re_body;

    @Column(name = "qna_re_created_at")
    private Timestamp qna_re_created_at;

    public QnaRe(Users users, Qna qna, String qna_re_body, Timestamp qna_re_created_at) {
        this.users = users;
        this.qna = qna;
        this.qna_re_body = qna_re_body;
        this.qna_re_created_at = qna_re_created_at;
    }
}



