package com.skrookies.dahaezlge.repository.purchase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;


@Slf4j
@Repository
@RequiredArgsConstructor

public class DBPurchaseRepository implements PurchaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Long> getDuplicateBooks(String user_id, List<Long> cartBookIdList) {
        String sql = "SELECT purchase_book_id FROM purchase WHERE purchase_user_id = ? AND purchase_book_id = ?";

        List<Long> duplicateBooks = new ArrayList<>();

        for (Long bookId : cartBookIdList) {
            List<Long> result = jdbcTemplate.queryForList(sql, Long.class, user_id, bookId);
            if (!result.isEmpty()) {
                duplicateBooks.add(bookId);
            }
        }

        log.info("Duplicate books found: " + duplicateBooks);

        return duplicateBooks;
    }


    @Override
    public boolean purchaseCart(String user_id, List<Long> cartBookIdList){

        String sql = "INSERT INTO purchase (purchase_user_id, purchase_book_id, purchase_date) VALUES (?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();
        int rowsAffected = 0;

        for (Long cartbookid : cartBookIdList) {
            rowsAffected += jdbcTemplate.update(sql, user_id, cartbookid, now);
        }

        log.info("Total rows affected: " + rowsAffected);

        return rowsAffected > 0;

    }

    @Override
    public List<Long> purchaseBook_list(String user_id) {
        String sql = "SELECT purchase_book_id FROM purchase WHERE purchase_user_id = ?";

        // queryForList로 데이터를 가져옴
        try {
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, user_id);
            log.info("PurchaseRepository: Got purchase_book_id List");

            // 반환할 book_id 리스트 초기화
            List<Long> purchase_books_id = new ArrayList<>();

            // Map에서 book_id 값을 추출하여 리스트에 추가
            for (Map<String, Object> row : results) {
                Long book_id = (Long) row.get("purchase_book_id");
                purchase_books_id.add(book_id);
            }

            // 변환된 book_id 리스트 반환
            log.info("purchase_books_id.size() : " + purchase_books_id.size());
            return purchase_books_id;
        } catch (Exception e) {
            // 예외 발생 시 빈 리스트 반환
            log.error("Error retrieving purchase_book_ids", e);
            return new ArrayList<>();
        }
    }



}
