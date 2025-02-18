package com.skrookies.dahaezlge.repository.notice;

import com.skrookies.dahaezlge.controller.notice.Dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NoticeRepository {

    private final JdbcTemplate jdbcTemplate;

    public int notice(NoticeDto noticeDto) {
        // 파일 관련 정보를 추가한 SQL 구문
        String sql = "INSERT INTO notice (notice_title, notice_body, notice_user_id, notice_created_at, file_name, file_path, file_size, new_file_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // 파일 정보를 포함하여 INSERT 실행
        return jdbcTemplate.update(
                sql,
                noticeDto.getNotice_title(),
                noticeDto.getNotice_body(),
                noticeDto.getNotice_user_id(),
                Timestamp.valueOf(noticeDto.getNotice_created_at()),  // LocalDateTime을 Timestamp로 변환
                noticeDto.getFile_name(),  // 파일 이름
                noticeDto.getFile_path(),  // 파일 경로
                noticeDto.getFile_size(),   // 파일 크기
                noticeDto.getNew_file_name()  // 날짜 추가된 파일 이름
        );
    }


    /** notice 전체 List 반환
     * @return List<noticeDto> </> */
    public List<NoticeDto> getNoticeList() {
        String sql = "SELECT * FROM notice ORDER BY notice_id DESC";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            NoticeDto notice = new NoticeDto();
            notice.setNotice_id(rs.getLong("notice_id"));
            notice.setNotice_title(rs.getString("notice_title"));
            notice.setNotice_body(rs.getString("notice_body"));
            notice.setNotice_user_id(rs.getString("notice_user_id"));
            Timestamp ts = rs.getTimestamp("notice_created_at");
            if (ts != null) {
                LocalDateTime createdAt = ts.toLocalDateTime();
                notice.setNotice_created_at(createdAt);
                notice.setFormattedCreatedAt(createdAt.format(formatter));
            }
            return notice;
        });
    }


    /** notice_id 기반 게시글 상세정보 모두 반환 */
    public NoticeDto NoticeById(int notice_id) {
        String sql = "SELECT * FROM notice WHERE notice_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{notice_id}, new RowMapper<NoticeDto>() {
            @Override
            public NoticeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                NoticeDto notice = new NoticeDto();
                notice.setNotice_id(rs.getLong("notice_id"));
                notice.setNotice_title(rs.getString("notice_title"));
                notice.setNotice_body(rs.getString("notice_body"));
                notice.setNotice_user_id(rs.getString("notice_user_id"));
                Timestamp ts = rs.getTimestamp("notice_created_at");
                if(ts != null) {
                    LocalDateTime createdAt = ts.toLocalDateTime(); // 변환 후
                    notice.setNotice_created_at(createdAt);             // LocalDateTime 타입으로 저장
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    notice.setFormattedCreatedAt(createdAt.format(formatter));
                }
                notice.setFile_name(rs.getString("file_name"));
                notice.setFile_path(rs.getString("file_path"));
                notice.setFile_size(rs.getLong("file_size"));
                notice.setNew_file_name(rs.getString("new_file_name"));
                return notice;
            }
        });
    }


    public int deleteNotice(int notice_id) {
        String sql = "DELETE FROM notice WHERE notice_id= ?";
        return jdbcTemplate.update(sql, notice_id);
    }


    public int noticeUpdate(NoticeDto noticeDto) {
        String sql = "UPDATE notice SET notice_title = ?, notice_body = ?, file_name = ?, file_path = ?, file_size = ?, new_file_name = ? WHERE notice_id = ?";
        return jdbcTemplate.update(sql, noticeDto.getNotice_title(), noticeDto.getNotice_body(),
                noticeDto.getFile_name(), noticeDto.getFile_path(), noticeDto.getFile_size(), noticeDto.getNew_file_name(), noticeDto.getNotice_id());
    }


    public int noticeUpdate2(NoticeDto noticeDto) {
        String sql = "UPDATE notice SET notice_title = ?, notice_body = ? WHERE notice_id = ?";
        return jdbcTemplate.update(sql, noticeDto.getNotice_title(), noticeDto.getNotice_body(), noticeDto.getNotice_id());
    }


    public int countTotalNotices() {
        String sql = "SELECT COUNT(*) FROM notice";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


    public List<NoticeDto> findnoticesByPage(int offset, int pageSize) {
        log.info("findnoticeAllList data {offset, pageSize}: {" + offset + ", " + pageSize + "}");
        String sql = "SELECT * FROM notice ORDER BY notice_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return jdbcTemplate.query(sql, new Object[]{offset, pageSize}, (rs, rowNum) -> {
            NoticeDto notice = new NoticeDto();
            notice.setNotice_id(rs.getLong("notice_id"));
            notice.setNotice_title(rs.getString("notice_title"));
            notice.setNotice_body(rs.getString("notice_body"));
            notice.setNotice_user_id(rs.getString("notice_user_id"));
            Timestamp ts = rs.getTimestamp("notice_created_at");
            if (ts != null) {
                LocalDateTime createdAt = ts.toLocalDateTime();
                notice.setNotice_created_at(createdAt);
                notice.setFormattedCreatedAt(createdAt.format(formatter));
            }
            return notice;
        });
    }


    public List<NoticeDto> findByKeyword(String keyword, int offset, int pageSize) {
        String sql = "SELECT notice_id, notice_title, notice_user_id, notice_created_at FROM notice WHERE notice_title LIKE ? ORDER BY notice_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%", offset, pageSize}, new RowMapper<NoticeDto>() {
            @Override
            public NoticeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                NoticeDto notice = new NoticeDto();
                notice.setNotice_id(rs.getLong("notice_id"));
                notice.setNotice_title(rs.getString("notice_title"));
                notice.setNotice_user_id(rs.getString("notice_user_id"));
                Timestamp ts = rs.getTimestamp("notice_created_at");
                if (ts != null) {
                    LocalDateTime createdAt = ts.toLocalDateTime();
                    notice.setNotice_created_at(createdAt);
                    notice.setFormattedCreatedAt(createdAt.format(formatter));
                }
                return notice;
            }
        });
    }

    public int countTotalNoticesByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM notice WHERE notice_title LIKE ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + keyword + "%"}, Integer.class);
    }

    public NoticeDto findByFileName(String fileName) {
        String sql = "SELECT file_name, new_file_name FROM notice WHERE new_file_name = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{fileName},
                    (rs, rowNum) -> {
                        NoticeDto noticeDto = new NoticeDto();
                        noticeDto.setFile_name(rs.getString("file_name"));
                        noticeDto.setNew_file_name(rs.getString("new_file_name"));
                        return noticeDto;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // 해당 파일이 없을 경우 null 반환
        }
    }
    
}
