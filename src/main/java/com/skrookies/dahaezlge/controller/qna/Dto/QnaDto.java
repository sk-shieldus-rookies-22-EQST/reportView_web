package com.skrookies.dahaezlge.controller.qna.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString

public class QnaDto {
    private Integer qna_id;
    private String qna_title;
    private String qna_body;
    private String qna_user_id;
    private LocalDate qna_created_at;
}
