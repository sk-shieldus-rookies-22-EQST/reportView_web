package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBBookRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BookDto getBookInfo(int book_id){
        String sql = "Select * from book where book_id = '" + book_id + "';";

        return jdbcTemplate.queryForObject(sql,BookDto.class);
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

}
