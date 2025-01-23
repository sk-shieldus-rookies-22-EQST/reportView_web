package com.skrookies.dahaezlge.repository.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j

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

    public List<QnaDto> getQnaList() {
        String sql = "SELECT * FROM qna ORDER BY qna_id DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            QnaDto qna = new QnaDto();
            qna.setQna_id(rs.getInt("qna_id"));
            qna.setQna_title(rs.getString("qna_title"));
            qna.setQna_body(rs.getString("qna_body"));
            qna.setQna_user_id(rs.getString("qna_user_id"));
            qna.setQna_created_at(rs.getTimestamp("qna_created_at").toLocalDateTime());
            return qna;
        });
    }

    public QnaDto QnaById(int qna_id) {
        String sql = "SELECT * from qna where qna_id= ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{qna_id}, new RowMapper<QnaDto>() {
            @Override
            public QnaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                QnaDto qna = new QnaDto();
                qna.setQna_id(rs.getInt("qna_id"));
                qna.setQna_title(rs.getString("qna_title"));
                qna.setQna_body(rs.getString("qna_body"));
                qna.setQna_user_id(rs.getString("qna_user_id"));
                qna.setQna_created_at(rs.getTimestamp("qna_created_at").toLocalDateTime());
                return qna;
            }
        });
    }

    public int deleteQna(int qna_id) {
        String sql = "DELETE FROM qna WHERE qna_id= ?";
        return jdbcTemplate.update(sql, qna_id);
    }

    public int qnaUpdate(QnaDto QnaDto) {
            //DB에 작성
            String sql = "UPDATE qna SET qna_title = ?, qna_body = ? WHERE qna_id = ? ";
            return jdbcTemplate.update(sql, QnaDto.getQna_title(), QnaDto.getQna_body(), QnaDto.getQna_id().intValue());
    }
}
