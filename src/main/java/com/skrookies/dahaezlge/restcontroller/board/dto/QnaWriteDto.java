package com.skrookies.dahaezlge.restcontroller.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnaWriteDto {

    private String title;
    private String writer;
    private String content;

    private MultipartFile qna_file;
}
