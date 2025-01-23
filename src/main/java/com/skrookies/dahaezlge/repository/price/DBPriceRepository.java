package com.skrookies.dahaezlge.repository.price;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.relational.core.query.Query.query;


@Slf4j
@Repository
@RequiredArgsConstructor
public class DBPriceRepository implements PriceRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public String getBookPrice(String book_id){
        String sql = "Select book_price from price where book_id = '" + book_id + "';";

        return jdbcTemplate.queryForObject(sql,String.class);
    }
}
