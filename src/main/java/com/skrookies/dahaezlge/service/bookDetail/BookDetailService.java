package com.skrookies.dahaezlge.service.bookDetail;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.repository.cart.CartRepository;
import com.skrookies.dahaezlge.repository.cartBook.CartBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookDetailService {

    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookRepository bookRepository;
    private final JdbcTemplate jdbcTemplate;

    /** 장바구니에 책 담기 */
    public Boolean addCart(String user_id, Long book_id, int book_price){
        BookDto book_info = bookRepository.getBookInfo(book_id);
        Long cart_id = cartRepository.addCart(user_id, book_id, book_info.getBook_price());
        if (cart_id == null){
            return false;
        }
        return cartBookRepository.addCartBook(cart_id, book_id);
    }

    public boolean isBookInCart(String userId, Long bookId) {
        // 동일한 책이 이미 장바구니에 있는지 확인
        String checkSql = "SELECT COUNT(*) FROM cart_book cb " +
                "JOIN cart c ON cb.cart_book_id = c.cart_id " +
                "WHERE c.cart_user_id = ? AND cb.cart_book_book_id = ?";

        // 쿼리 실행
        Integer count = jdbcTemplate.queryForObject(
                checkSql,
                new Object[]{userId, bookId},
                Integer.class
        );

        // 결과 반환: count > 0이면 true, 아니면 false
        return count != null && count > 0;
    }
}
