package com.skrookies.dahaezlge.repository.cart;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        String sql = "SELECT * FROM cart WHERE cart_user_id = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{user_id}, // SQL의 '?'에 매핑되는 파라미터
                (rs, rowNum) -> new CartDto(
                        rs.getLong("cart_id"),
                        rs.getString("cart_user_id"),
                        rs.getInt("cart_total_price")
                )
        );
    }


    @Override
    public Long addCart(String user_id, BookDto book_info) {
        String sql = "INSERT INTO cart (cart_user_id, cart_total_price) VALUES (?, ?)";

        Integer book_price = book_info.getBook_price();

        // KeyHolder 객체 생성
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "cart_id" });
            ps.setString(1, user_id);
            ps.setInt(2, book_price);
            return ps;
        }, keyHolder);

        // 키를 정수로 반환
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("cart_id 값을 생성하지 못했습니다.");
        }
    }
}
