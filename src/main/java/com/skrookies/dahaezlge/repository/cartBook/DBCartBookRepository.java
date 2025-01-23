package com.skrookies.dahaezlge.repository.cartBook;

import com.skrookies.dahaezlge.entity.cart.CartId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.relational.core.query.Query.query;


@Slf4j
@Repository
@RequiredArgsConstructor

public class DBCartBookRepository implements CartBookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean addPrice(int cart_id, String price){
        String sql = "Insert into cart_book (book_id, book_price) values ("+ cart_id + ", '" + price + "';";

        return true;
    }
}
