package com.skrookies.dahaezlge.repository.cart;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;

import java.util.List;

public interface CartRepository {
    List<CartDto> getCartList(String user_id);

    Long addCart(String user_id, BookDto book_info);

    Boolean delCartItem(List<Long> deletedCardId);
}
