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
        
        //qna_id 자동 증가
        String sql = "SELECT COALESCE(MAX(qna_id), 0) FROM qna";
        Integer maxQnaId =  jdbcTemplate.queryForObject(sql, Integer.class);
        QnaDto.setQna_id(maxQnaId + 1);
        
        //DB에 작성
        String sql2 = "insert into qna(qna_id, qna_title, qna_body, qna_user_id, qna_created_at) values(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql2, QnaDto.getQna_id(), QnaDto.getQna_title(), QnaDto.getQna_body(), QnaDto.getQna_user_id(), QnaDto.getQna_created_at());
    }
}
