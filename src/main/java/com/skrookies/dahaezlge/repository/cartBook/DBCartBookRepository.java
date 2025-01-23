package com.skrookies.dahaezlge.repository.cartBook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor

public class DBCartBookRepository implements CartBookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean addCartBook(int cart_id, int book_id){
        String sql = "INSERT INTO cart_book (cart_book_id, cart_book_book_id) VALUES (?, ?)";

        // update 메서드는 영향을 받은 행의 개수를 반환
        int rowsAffected = jdbcTemplate.update(sql, cart_id, book_id);

        // 삽입이 성공했는지 여부를 boolean으로 반환
        return rowsAffected > 0;
    }
}
