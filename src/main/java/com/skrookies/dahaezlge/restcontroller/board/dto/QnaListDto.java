package com.skrookies.dahaezlge.restcontroller.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnaListDto {

    private Long qna_id;
    private String title;
    private String user_id;
    private LocalDateTime created_at;
}
