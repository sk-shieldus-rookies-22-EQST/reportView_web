package com.skrookies.dahaezlge.controller.notice.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class NoticeDto {
    private Long notice_id;
    private String notice_title;
    private String notice_body;
    private String notice_user_id;
    private LocalDateTime notice_created_at;

    private MultipartFile notice_file;  // MultipartFile은 파일 업로드에 사용되는 객체
    private String file_name;        // 파일 이름
    private String file_path;        // 파일 경로
    private Long file_size;          // 파일 크기
    private String new_file_name;        // 날짜 추가된 파일 이름

    private String formattedCreatedAt;
}