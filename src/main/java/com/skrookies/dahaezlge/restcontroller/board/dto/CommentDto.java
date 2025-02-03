package com.skrookies.dahaezlge.restcontroller.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long qna_re_id;
    private Long qna_id;
    private String qna_re_content;
    private LocalDateTime qna_re_created_at;
}
