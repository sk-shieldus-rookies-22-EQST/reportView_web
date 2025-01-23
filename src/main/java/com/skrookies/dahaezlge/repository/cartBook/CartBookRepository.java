package com.skrookies.dahaezlge.repository.cartBook;

import com.skrookies.dahaezlge.entity.cart.Cart;

import java.util.List;

public interface CartBookRepository {
    Boolean addCartBook(int cart_id, int book_id);

    List<Integer> getCartBookList(List<Cart> CartIdList);
}
