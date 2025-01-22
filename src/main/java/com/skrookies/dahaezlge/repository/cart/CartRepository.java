package com.skrookies.dahaezlge.repository.cart;

import com.skrookies.dahaezlge.entity.cart.CartId;

import java.util.List;

public interface CartRepository {
    List<CartId> getCartList(String user_id);
}
