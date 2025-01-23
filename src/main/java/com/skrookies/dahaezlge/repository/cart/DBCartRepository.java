package com.skrookies.dahaezlge.repository.cart;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class DBCartRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CartDto> getCartList(String user_id){
        String sql = "Select * from cart where cart_user_id = '" + user_id + "';";

        return jdbcTemplate.queryForObject(sql,List.class);
    }


    @Override
    public int addCart(String user_id, List<BookDto> book_info) {
        String sql = "INSERT INTO cart (cart_user_id, cart_total_price) VALUES (?, ?)";

        String book_price = book_info.getFirst().getBook_price();

        // KeyHolder 객체 생성
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "cart_id" });
            ps.setString(1, user_id);
            ps.setString(2, book_price);
            return ps;
        }, keyHolder);

        // 키를 정수로 반환
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            throw new RuntimeException("cart_id 값을 생성하지 못했습니다.");
        }
    }
}
