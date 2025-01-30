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

    @Column(name = "file_name")
    private String file_name;  // 파일 이름

    @Column(name = "file_path")
    private String file_path;  // 파일 경로

    @Column(name = "file_size")
    private Long file_size;    // 파일 크기

    @Column(name = "new_file_name")
    private String new_file_name;  // 파일 이름

    public Qna(Users users, String qna_title, String qna_body, Timestamp qna_created_at, String file_name, String file_path, Long file_size, String new_file_name) {
        this.users = users;
        this.qna_title = qna_title;
        this.qna_body = qna_body;
        this.qna_created_at = qna_created_at;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_size = file_size;
        this.new_file_name = new_file_name;
    }
}



