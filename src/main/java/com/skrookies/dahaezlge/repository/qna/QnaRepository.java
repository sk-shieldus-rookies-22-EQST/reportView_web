package com.skrookies.dahaezlge.repository.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

public class QnaRepository {
    private final JdbcTemplate jdbcTemplate;
    public int qna(QnaDto QnaDto) {
        String sql = "insert into qna(qna_id, qna_title, qna_body, qna_user_id, qna_created_at) values(#{qna_id}, #{qna_title}, #{qna_body}, #{qna_user_id}, #{qna_created_at})";
        return jdbcTemplate.update(sql,QnaDto);
    }
}
