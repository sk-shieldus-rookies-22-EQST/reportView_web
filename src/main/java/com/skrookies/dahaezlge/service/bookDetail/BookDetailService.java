package com.skrookies.dahaezlge.service.bookDetail;

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

    public int addCart(String user_id, int book_price){
        return cartRepository.addCart(user_id, book_price);
    }

    public boolean addCartBook(int cart_id, int book_id){
        return cartBookRepository.addCartBook(cart_id, book_id);
    }

}
