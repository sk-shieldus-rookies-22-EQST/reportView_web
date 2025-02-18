package com.skrookies.dahaezlge.entity.notice;

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
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long notice_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_user_id", nullable = false)
    private Users users;

    @Column(name = "notice_title", nullable = false)
    private String notice_title;

    @Column(name = "notice_body", nullable = false)
    private String notice_body;

    @Column(name = "notice_created_at")
    private Timestamp notice_created_at;

    @Column(name = "file_name")
    private String file_name;  // 파일 이름

    @Column(name = "file_path")
    private String file_path;  // 파일 경로

    @Column(name = "file_size")
    private Long file_size;    // 파일 크기

    @Column(name = "new_file_name")
    private String new_file_name;  // 파일 이름

    public Notice(Users users, String notice_title, String notice_body, Timestamp notice_created_at, String file_name, String file_path, Long file_size, String new_file_name) {
        this.users = users;
        this.notice_title = notice_title;
        this.notice_body = notice_body;
        this.notice_created_at = notice_created_at;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_size = file_size;
        this.new_file_name = new_file_name;
    }
}