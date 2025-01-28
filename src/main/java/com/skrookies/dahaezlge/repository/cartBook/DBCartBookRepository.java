package com.skrookies.dahaezlge.repository.cartBook;

import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor

public class DBCartBookRepository implements CartBookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean addCartBook(Long cart_id, Long book_id){
        String sql = "INSERT INTO cart_book (cart_book_id, cart_book_book_id) VALUES (?, ?)";

        // update 메서드는 영향을 받은 행의 개수를 반환
        int rowsAffected = jdbcTemplate.update(sql, cart_id, book_id);

        // 삽입이 성공했는지 여부를 boolean으로 반환
        return rowsAffected > 0;
    }

    @Override
    public List<Long> getCartBookList(List<CartDto> CartList){
        List<Long> bookIds = new ArrayList<>();

        String sql = "SELECT cart_book_book_id FROM cart_book WHERE cart_book_id = ?";
        for (CartDto cart : CartList) {
            List<Long> ids = jdbcTemplate.query(sql, new Object[]{cart.getCart_id()},
                    (rs, rowNum) -> rs.getLong("cart_book_book_id"));

            bookIds.addAll(ids);
        }
        log.info(String.valueOf(bookIds.size()));

        return bookIds;
    }

    @Override
    public List<Long> delCartBookItem(List<CartDto> cartIdList, Long book_id){
        String sql = "DELETE FROM cart_book WHERE cart_book_id = ? AND cart_book_book_id = ?";
        List<Long> deletedCartIds = new ArrayList<>(); // 삭제가 성공한 cart_id를 저장할 리스트

        for (CartDto cart : cartIdList) {
            int updatedRows = jdbcTemplate.update(sql, cart.getCart_id(), book_id);
            if (updatedRows > 0) {
                deletedCartIds.add(cart.getCart_id()); // 삭제가 성공하면 cart_id를 리스트에 추가
            }
        }
        log.info("삭제된 cart_id들: " + deletedCartIds);
        // 삭제가 성공한 cart_id들의 리스트를 반환
        return deletedCartIds;
    }

    @Override
    public List<Long> delCartBookItems(List<CartDto> cartIdList){
        String sql = "DELETE FROM cart_book WHERE cart_book_id = ?";
        List<Long> deletedCartIds = new ArrayList<>(); // 삭제가 성공한 cart_id를 저장할 리스트

        for (CartDto cart : cartIdList) {
            int updatedRows = jdbcTemplate.update(sql, cart.getCart_id());
            if (updatedRows > 0) {
                deletedCartIds.add(cart.getCart_id()); // 삭제가 성공하면 cart_id를 리스트에 추가
            }
        }
        log.info("삭제된 cart_id들: " + deletedCartIds);
        // 삭제가 성공한 cart_id들의 리스트를 반환
        return deletedCartIds;
    }
}
