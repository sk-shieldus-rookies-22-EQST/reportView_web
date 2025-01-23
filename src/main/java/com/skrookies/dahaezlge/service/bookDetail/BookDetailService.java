package com.skrookies.dahaezlge.service.bookDetail;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.repository.cart.CartRepository;
import com.skrookies.dahaezlge.repository.cartBook.CartBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookDetailService {

    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookRepository bookRepository;

    public Boolean addCart(String user_id, Long book_id){
        BookDto book_info = bookRepository.getBookInfo(book_id);
        int cart_id = cartRepository.addCart(user_id, book_info);
        return cartBookRepository.addCartBook(cart_id, book_id);
    }
}
