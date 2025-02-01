package com.skrookies.dahaezlge.restcontroller.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QnaModifyDto {

    private Long qna_id;
    private String title;
    private String writer;
    private String content;

    private MultipartFile qna_file;  // MultipartFile은 파일 업로드에 사용되는 객체
}
