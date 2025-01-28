package com.skrookies.dahaezlge.repository.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j

public class QnaRepository {
    private final JdbcTemplate jdbcTemplate;
    public int qna(QnaDto QnaDto) {

        
        //DB에 작성
        String sql = "insert into qna(qna_id, qna_title, qna_body, qna_user_id, qna_created_at) values(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, QnaDto.getQna_id(), QnaDto.getQna_title(), QnaDto.getQna_body(), QnaDto.getQna_user_id(), QnaDto.getQna_created_at());
    }

    public List<QnaDto> getQnaList() {
        String sql = "SELECT * FROM qna ORDER BY qna_id DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            QnaDto qna = new QnaDto();
            qna.setQna_id(rs.getLong("qna_id"));
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
                qna.setQna_id(rs.getLong("qna_id"));
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
            String sql = "UPDATE qna SET qna_title = ?, qna_body = ? WHERE qna_id = ?";
            return jdbcTemplate.update(sql, QnaDto.getQna_title(), QnaDto.getQna_body(), QnaDto.getQna_id());
    }

    public int countTotalQnas() {
        String sql = "SELECT COUNT(*) FROM qna";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<QnaDto> findQnasByPage(int offset, int pageSize) {
        String sql = "SELECT * FROM qna ORDER BY qna_created_at DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{pageSize, offset}, (rs, rowNum) -> {
            QnaDto qna = new QnaDto();
            qna.setQna_id(rs.getLong("qna_id"));
            qna.setQna_title(rs.getString("qna_title"));
            qna.setQna_body(rs.getString("qna_body"));
            qna.setQna_user_id(rs.getString("qna_user_id"));
            qna.setQna_created_at(rs.getTimestamp("qna_created_at").toLocalDateTime());
            return qna;
        });
    }

    public List<QnaDto> findByKeyword(String keyword, int offset, int pageSize) {
        String sql = "SELECT qna_id, qna_title, qna_user_id, qna_created_at FROM qna WHERE qna_title LIKE ? LIMIT ? OFFSET ? ";
        return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%", pageSize, offset},
                new BeanPropertyRowMapper<>(QnaDto.class));
    }

    public int qnaReply(QnaReDto qnaReDto) {
        String sql = "INSERT INTO qna_re (qna_re_user_id, qna_re_body, qna_re_created_at, qna_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, qnaReDto.getQna_re_user_id(), qnaReDto.getQna_re_body(), qnaReDto.getQna_re_created_at(), qnaReDto.getQna_id());
    }
}
