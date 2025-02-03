package com.skrookies.dahaezlge.controller.qna.Dto;

import com.skrookies.dahaezlge.entity.qnaRe.QnaRe;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class QnaReDto {
    private Long qna_re_id;
    private Long qna_id;
    private String qna_re_user_id;
    private String qna_re_body;
    private LocalDateTime qna_re_created_at;

    private String formattedCreatedAt;

    public QnaReDto() {
        // 기본 생성자
    }

    public QnaReDto(QnaRe qnaRe) {
        this.qna_re_id = qnaRe.getQna_re_id();
        // Qna 객체가 null이 아닐 경우에만 qna_id를 설정
        this.qna_id = (qnaRe.getQna() != null) ? qnaRe.getQna().getQna_id() : null;
        // Users 객체에 대해서도 null 체크 (필요하다면)
        this.qna_re_user_id = (qnaRe.getUsers() != null) ? qnaRe.getUsers().getUser_id() : "";
        this.qna_re_body = qnaRe.getQna_re_body();
        this.qna_re_created_at = qnaRe.getQna_re_created_at();

        // 날짜 포맷 (T 제거)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.formattedCreatedAt = (qna_re_created_at != null) ? qna_re_created_at.format(formatter) : "";
    }
}
