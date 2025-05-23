package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.entity.book.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.net.resolver.TimeUnitSuffixUtility;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBBookRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BookDto getBookInfo(Long book_id) {
        String sql = "SELECT * FROM book WHERE book_id = ? FETCH FIRST 1 ROWS ONLY";

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{book_id},
                (rs, rowNum) -> new BookDto(
                        rs.getLong("book_id"),
                        rs.getString("book_title"),
                        rs.getString("book_auth"),
                        rs.getString("book_path"),
                        rs.getString("book_summary"),
                        rs.getTimestamp("book_reg_date"),
                        rs.getString("book_img_path"),
                        rs.getInt("book_price")
                )
        );
    }
    @Override
    public List<BookDto> getCartBookInfo(List<Long> bookIdList){
        String sql = "Select * from book where book_id = ?";

        // 결과를 저장할 리스트
        List<BookDto> cartBookInfoList = new ArrayList<>();

        // 각 bookId에 대해 개별적으로 쿼리 실행
        for (Long bookId : bookIdList) {
            List<BookDto> books = jdbcTemplate.query(
                    sql,
                    new Object[]{bookId},
                    new BeanPropertyRowMapper<>(BookDto.class)
            );
            cartBookInfoList.addAll(books); // 결과를 리스트에 추가
        }

        return cartBookInfoList; // 결과 반환
    }
/** order by 버전 prepare statement로 봐도 될듯 */
    private String getOrderByClause(String sort) {
        if (sort == null || sort.isEmpty()) {
            return "book_title"; // 기본값
        }
        return switch (sort) {
            case "bt" -> "book_title";
            case "price_asc", "price_desc" -> "book_price";
            case "date" -> "book_reg_date";
            default -> "book_title";
        };
    }

    @Override
    public List<Map<String, Object>> getBooks(String sort, String direction) {
        String orderByClause = getOrderByClause(sort);
        if(direction == null || direction.isEmpty()){
            direction = "ASC";
        }
        String sql = "SELECT book_id, book_title, book_auth, book_path, book_summary, " +
                "book_reg_date, book_img_path, book_price " +
                "FROM book ORDER BY " + orderByClause + " " + direction;
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getBooksWithKeyword(String keyword,
                                                         String sort, String direction) {
        String orderByClause = getOrderByClause(sort);
        if (direction == null || direction.isEmpty()) {
            direction = "ASC";
        }

        // SQL Query (keyword를 PreparedStatement로 바인딩)
        String sql = "SELECT book_id, book_title, book_auth, book_path, book_summary, " +
                "book_reg_date, book_img_path, book_price " +
                "FROM book WHERE book_title LIKE ? " +
                "ORDER BY " + orderByClause + " " + direction;

        // '%'를 포함한 검색어를 바인딩
        return jdbcTemplate.queryForList(sql, "%" + keyword + "%");
    }

    @Override
    public List<Map<String, Object>> getBooksWithDate(String sdate, String edate, String sort, String direction) {
        String orderByClause = getOrderByClause(sort);
        if (direction == null || direction.isEmpty()) {
            direction = "ASC";
        }

        // SQL Query (sdate, edate를 PreparedStatement로 바인딩)
        String sql = "SELECT book_id, book_title, book_auth, book_path, book_summary, " +
                "book_reg_date, book_img_path, book_price " +
                "FROM book WHERE book_reg_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') " +
                "AND TO_DATE(?, 'YYYY-MM-DD') " +
                "ORDER BY " + orderByClause + " " + direction;

        return jdbcTemplate.queryForList(sql, sdate, edate);
    }

    @Override
    public List<Map<String, Object>> getBooksWithBoth(String keyword, String sdate, String edate, String sort, String direction) {
        String orderByClause = getOrderByClause(sort);
        if (direction == null || direction.isEmpty()) {
            direction = "ASC";
        }

        // SQL Query (keyword, sdate, edate를 PreparedStatement로 바인딩)
        String sql = "SELECT book_id, book_title, book_auth, book_path, book_summary, " +
                "book_reg_date, book_img_path, book_price " +
                "FROM book WHERE book_title LIKE ? " +
                "AND book_reg_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') " +
                "AND TO_DATE(?, 'YYYY-MM-DD') " +
                "ORDER BY " + orderByClause + " " + direction;

        return jdbcTemplate.queryForList(sql, "%" + keyword + "%", sdate, edate);
    }

    @Override
    public int getTotalBooks() {
        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 4) 전체 책 목록 (Map 리스트)
    @Override
    public List<Map<String, Object>> findAllBooks() {
        String sql = """
            SELECT book_id, book_title, book_auth, book_path,
                   book_summary, book_reg_date, book_img_path, book_price 
            FROM book
        """;
        // queryForList: 컬럼명=Key, 값=Value 형태로 Map을 만듦
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        log.info("[findAllBooks] Fetched {} books from DB", result.size());
        return result;
    }

    @Override
    public List<Map<String, Object>> findByKeyword(String keyword) {
        String sql = "SELECT * FROM book WHERE book_title LIKE ?";

        try {
            log.info("findByKeyword try");
            return jdbcTemplate.queryForList(sql, "%" + keyword + "%");
        } catch (Exception e) {
            log.info("findByKeyword fail");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findByDate(String sdate, String edate) {
        String sql = "SELECT * FROM book WHERE book_reg_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";

        try {
            log.info("findByDate try");
            return jdbcTemplate.queryForList(sql, sdate, edate);
        } catch (Exception e) {
            log.info("findByDate fail");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findByBoth(String keyword, String sdate, String edate) {
        String sql = "SELECT * FROM book WHERE book_title LIKE ? " +
                "AND book_reg_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";

        try {
            log.info("findByBoth try");
            return jdbcTemplate.queryForList(sql, "%" + keyword + "%", sdate, edate);
        } catch (Exception e) {
            log.info("findByBoth fail");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getMyBooks(Long bookId) {
        String sql = """
        SELECT book_id, book_title, book_auth, book_path, book_summary, book_reg_date, book_img_path, book_price
        FROM book WHERE book_id = ?
        ORDER BY book_reg_date DESC
    """;
        return jdbcTemplate.queryForList(sql, bookId);
    }

    /** Timestamp를 String 형식으로 변환 (나노초 제거) */
    private String dateFormatter(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        // Timestamp를 LocalDateTime으로 변환
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        // 날짜 및 시간 형식 지정 (나노초는 제거)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDateTime을 String으로 변환
        return localDateTime.format(formatter);
    }


}
