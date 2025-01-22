package com.skrookies.dahaezlge.controller.qna.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString

public class QnaDto {
    private Long qna_id;
    private String qna_title;
    private String qna_body;
    private String qna_user_id;
    private Timestamp qna_created_at;
}
