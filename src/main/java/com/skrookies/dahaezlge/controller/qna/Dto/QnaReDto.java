package com.skrookies.dahaezlge.controller.qna.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class QnaReDto {
    private Long qna_re_id;
    private Long qna_id;
    private String qna_re_user_id;
    private String qna_re_body;
    private LocalDateTime qna_re_created_at;

    private String formattedCreatedAt;
}
