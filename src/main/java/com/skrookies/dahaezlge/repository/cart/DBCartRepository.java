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
        try {
            // 동일한 책이 이미 장바구니에 있는지 확인
            String checkSql = "SELECT COUNT(*) FROM cart_book cb " +
                    "JOIN cart c ON cb.cart_book_id = c.cart_id " +
                    "WHERE c.cart_user_id = ? AND cb.cart_book_book_id = ?";

            Integer count = jdbcTemplate.queryForObject(
                    checkSql,
                    new Object[]{user_id, book_info.getBook_id()},
                    Integer.class
            );

            // 이미 책이 장바구니에 있다면 에러 메시지 출력 및 null 반환
            if (count != null && count > 0) {
                throw new IllegalStateException("이미 장바구니에 담겨있습니다.");
            }

            // 새로운 장바구니 생성
            String cartSql = "INSERT INTO cart (cart_user_id, cart_total_price) VALUES (?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(cartSql, new String[]{"cart_id"});
                ps.setString(1, user_id);
                ps.setInt(2, book_info.getBook_price());
                return ps;
            }, keyHolder);

            // 생성된 cart_id 반환
            return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;

        } catch (IllegalStateException e) {
            // 이미 장바구니에 존재하는 경우 사용자에게 메시지를 보여줌
            System.out.println(e.getMessage()); // "이미 장바구니에 담겨있습니다."
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to add cart", e);
        }
    }

    @Override
    public Boolean delCartItem(List<Long> deletedCartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";

        for (Long cartId : deletedCartId) {
            int updatedRows = jdbcTemplate.update(sql, cartId);
            if (updatedRows <= 0) {
                return false;
            }
        }
        // 모든 삭제가 성공했을 경우 true 반환
        return true;
    }
}
