package com.skrookies.dahaezlge.entity.qna;

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
@Table(name = "qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long qna_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_user_id", nullable = false)
    private Users users;

    @Column(name = "qna_title", nullable = false)
    private String qna_title;

    @Column(name = "qna_body", nullable = false)
    private String qna_body;

    @Column(name = "qna_created_at")
    private Timestamp qna_created_at;

    public Qna(Users users, String qna_title, String qna_body, Timestamp qna_created_at) {
        this.users = users;
        this.qna_title = qna_title;
        this.qna_body = qna_body;
        this.qna_created_at = qna_created_at;
    }
}



