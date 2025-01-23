package com.skrookies.dahaezlge.repository.cart;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;
import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.cart.Cart;

import java.util.List;

public interface CartRepository {
    List<CartDto> getCartList(String user_id);

    int addCart(String user_id, BookDto book_info);
}
