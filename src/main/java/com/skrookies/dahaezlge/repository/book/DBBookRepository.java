package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.entity.book.BookInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBBookRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<BookInfo> getBookInfo(int book_id){
        String sql = "Select book_id, book_img_path, book_title, book_auth, book_reg_date from book " +
                "where book_id = '" + book_id + "';";

        return jdbcTemplate.queryForObject(sql,List.class);
    }
}
