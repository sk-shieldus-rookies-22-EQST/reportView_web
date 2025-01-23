package com.skrookies.dahaezlge.repository.cart;

import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.cart.Cart;

import java.util.List;

public interface CartRepository {
    List<Cart> getCartList(String user_id);

    int addCart(String user_id, List<Book> book_info);
}
