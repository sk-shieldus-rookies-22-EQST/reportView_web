package com.skrookies.dahaezlge.repository.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import com.skrookies.dahaezlge.entity.qnaRe.QnaRe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        // 파일 관련 정보를 추가한 SQL 구문
        String sql = "INSERT INTO qna (qna_title, qna_body, qna_user_id, qna_created_at, file_name, file_path, file_size, new_file_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // 파일 정보를 포함하여 INSERT 실행
        return jdbcTemplate.update(
                sql,
                QnaDto.getQna_title(),
                QnaDto.getQna_body(),
                QnaDto.getQna_user_id(),
                Timestamp.valueOf(QnaDto.getQna_created_at()),  // LocalDateTime을 Timestamp로 변환
                QnaDto.getFile_name(),  // 파일 이름
                QnaDto.getFile_path(),  // 파일 경로
                QnaDto.getFile_size(),   // 파일 크기
                QnaDto.getNew_file_name()  // 날짜 추가된 파일 이름
        );
    }

    /** Qna 전체 List 반환
     * @return List<QnaDto> </> */
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

    /** qna_id 기반 게시글 상세정보 모두 반환 */
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
                qna.setFile_name(rs.getString("file_name"));
                qna.setFile_path(rs.getString("file_path"));
                qna.setFile_size(rs.getLong("file_size"));
                qna.setNew_file_name(rs.getString("new_file_name"));
                return qna;
            }
        });
    }

    public int deleteQna(int qna_id) {
        String sql = "DELETE FROM qna WHERE qna_id= ?";
        return jdbcTemplate.update(sql, qna_id);
    }

    public int qnaUpdate(QnaDto QnaDto) {
        String sql = "UPDATE qna SET qna_title = ?, qna_body = ?, file_name = ?, file_path = ?, file_size = ?, new_file_name = ? WHERE qna_id = ?";
        return jdbcTemplate.update(sql, QnaDto.getQna_title(), QnaDto.getQna_body(),
                QnaDto.getFile_name(), QnaDto.getFile_path(), QnaDto.getFile_size(), QnaDto.getNew_file_name(), QnaDto.getQna_id());
    }

    public int qnaUpdate2(QnaDto QnaDto) {
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

    public List<QnaRe> findRepliesByQnaId(Long qna_id) {
        String sql = "SELECT * FROM qna_re WHERE qna_id = ?";
        return jdbcTemplate.query(sql, new Object[]{qna_id}, new BeanPropertyRowMapper<>(QnaRe.class));
    }

    public void deleteByQnaID(Long qna_re_id) {
        String sql = "DELETE FROM qna_re WHERE qna_re_id= ?";
        jdbcTemplate.update(sql, qna_re_id);
    }

    public QnaDto findByFileName(String fileName) {
        String sql = "SELECT file_name, new_file_name FROM qna WHERE new_file_name = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{fileName},
                    (rs, rowNum) -> {
                        QnaDto qnaDto = new QnaDto();
                        qnaDto.setFile_name(rs.getString("file_name"));
                        qnaDto.setNew_file_name(rs.getString("new_file_name"));
                        return qnaDto;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // 해당 파일이 없을 경우 null 반환
        }
    }
}
