package com.skrookies.dahaezlge.repository.cart;

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
public class DBCartRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CartId> getCartList(String user_id){
        String sql = "Select cart_id from cart where cart_user_id = '" + user_id + "';";

        return jdbcTemplate.queryForObject(sql,List.class);
    }

}
