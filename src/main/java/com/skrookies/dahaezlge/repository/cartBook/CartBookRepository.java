package com.skrookies.dahaezlge.repository.cartBook;

import com.skrookies.dahaezlge.controller.cart.Dto.CartDto;

import java.util.List;

public interface CartBookRepository {
    Boolean addCartBook(Long cart_id, Long book_id);

    List<Long> getCartBookList(List<CartDto> CartIdList);
}
