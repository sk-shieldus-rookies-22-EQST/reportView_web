package com.skrookies.dahaezlge.repository.cartBook;

import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;

import java.util.List;

public interface CartBookRepository {
    Boolean addCartBook(int cart_id, int book_id);

    List<Integer> getCartBookList(List<CartDto> CartIdList);
}
