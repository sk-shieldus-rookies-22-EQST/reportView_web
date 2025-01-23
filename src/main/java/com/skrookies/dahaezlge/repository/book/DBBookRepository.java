package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                    BookDto book = new BookDto();
                    book.setBook_id(rs.getLong("book_id"));
                    book.setBook_title(rs.getString("book_title"));
                    book.setBook_auth(rs.getString("book_auth"));
                    book.setBook_path(rs.getString("book_path"));
                    book.setBook_summary(rs.getString("book_summary"));
                    book.setBook_reg_date(String.valueOf(rs.getDate("book_reg_date")));
                    book.setBook_img_path(rs.getString("book_img_path"));
                    return book;
                }
        );
    }
    @Override
    public List<BookDto> getCartBookInfo(List<Integer> bookIdList){
        String sql = "Select * from book where book_id = ?";

        // 결과를 저장할 리스트
        List<BookDto> cartBookInfoList = new ArrayList<>();

        // 각 bookId에 대해 개별적으로 쿼리 실행
        for (Integer bookId : bookIdList) {
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

}
