package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.entity.book.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBBookRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BookDto getBookInfo(Long book_id){
        String sql = "Select * from book where book_id = "+ book_id +" limit 1";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    BookDto book = new BookDto(
                            rs.getLong("book_id"),
                            rs.getString("book_title"),
                            rs.getString("book_auth"),
                            rs.getString("book_path"),
                            rs.getString("book_summary"),
                            rs.getTimestamp("book_reg_date").toLocalDateTime(),
                            rs.getString("book_img_path"),
                            rs.getInt("book_price"));
                    return book;
                }
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

    @Override
    public List<Map<String, Object>> getBooks(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = """
        SELECT book_id, book_title, book_auth, book_path, book_summary, book_reg_date, book_img_path, book_price
        FROM book
        ORDER BY book_reg_date DESC
        LIMIT ? OFFSET ?
    """;
        return jdbcTemplate.queryForList(sql, pageSize, offset);
    }

    @Override
    public List<Map<String, Object>> getBooksWithKeyword(String keyword, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT book_id, book_title, book_auth, book_path, book_summary, book_reg_date, book_img_path, book_price " +
                "FROM book " +
                "WHERE book_title like '%" + keyword + "%' " +
                "ORDER BY book_reg_date DESC " +
                "LIMIT " + pageSize + " OFFSET " + offset + ";";

        return jdbcTemplate.queryForList(sql);
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

        String sql = "select * from book where book_title like '%" + keyword + "%'";

        try {
            log.info("findByKeyword try");
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e){
            log.info("findByKeyword fail");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findByDate(LocalDateTime sdate, LocalDateTime edate) {

        String sql = "select * from book where book_reg_data between sdate and edate";

        try {
            log.info("findByDate try");
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e){
            log.info("findByDate fail");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> findByBoth(String keyword, LocalDateTime sdate, LocalDateTime edate) {

        String sql = "select * from book where book_title like '%" + keyword + "%' and (book_reg_data between sdate and edate)";

        try {
            log.info("findByBoth try");
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e){
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


}
