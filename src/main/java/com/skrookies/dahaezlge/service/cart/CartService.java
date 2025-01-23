package com.skrookies.dahaezlge.service.cart;

import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.cart.Cart;
import com.skrookies.dahaezlge.entity.cartBook.CartBook;
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


public class CartService {
    private final CartRepository cartRepository;
    private final CartBookRepository cartBookRepository;
    private final BookRepository bookRepository;

    public List<Book> setCartList(String user_id){
        List<Cart> cartIdList = cartRepository.getCartList(user_id);

        List<Integer> cartBookIdList = cartBookRepository.getCartBookList(cartIdList);

        return bookRepository.getCartBookInfo(cartBookIdList);
    }
}
