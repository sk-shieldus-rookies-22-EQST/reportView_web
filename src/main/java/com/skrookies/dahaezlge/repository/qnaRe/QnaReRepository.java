package com.skrookies.dahaezlge.repository.qnaRe;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QnaReRepository {
    private final JdbcTemplate jdbcTemplate;

    public int saveReply(QnaReDto qnaReDto) {
        String sql = "INSERT INTO qna_re (qna_re_user_id, qna_re_body, qna_re_created_at) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, qnaReDto.getQna_re_user_id(), qnaReDto.getQna_re_body(),qnaReDto.getQna_re_created_at());
    }

    public List<QnaReDto> findRepliesByQnaId(Long qnaId) {
        String sql = "SELECT * FROM qna_re WHERE qna_id = ? ORDER BY qna_re_created_at ASC";
        return jdbcTemplate.query(sql, new Object[]{qnaId}, (rs, rowNum) -> {
            QnaReDto reply = new QnaReDto();
            reply.setQna_re_id(rs.getLong("qna_re_id"));
            reply.setQna_re_user_id(rs.getString("qna_re_user_id"));
            reply.setQna_re_body(rs.getString("qna_re_body"));
            reply.setQna_re_created_at(rs.getTimestamp("qna_re_created_at").toLocalDateTime());
            return reply;
        });
    }
}
