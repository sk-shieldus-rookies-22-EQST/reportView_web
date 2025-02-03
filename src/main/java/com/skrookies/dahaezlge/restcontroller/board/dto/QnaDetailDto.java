package com.skrookies.dahaezlge.restcontroller.board.dto;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QnaDetailDto {

    private Long qna_id;
    private String title;
    private String writer;
    private Boolean secret;
    private LocalDateTime created_at;

    private String content;
    private String file_name;
    private String file_path;
    private List<CommentDto> comment;
}
