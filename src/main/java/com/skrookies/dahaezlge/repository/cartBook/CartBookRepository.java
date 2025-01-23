package com.skrookies.dahaezlge.repository.cartBook;

import com.skrookies.dahaezlge.entity.cart.CartId;
import com.skrookies.dahaezlge.entity.cartBook.CartBookId;

import java.util.List;

public interface CartBookRepository {
    Boolean addCartBook(int cart_id, int book_id);

    List<CartBookId> getCartBookList(List<CartId> CartIdList);
}
