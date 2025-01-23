package com.skrookies.dahaezlge.controller.cart;

import com.skrookies.dahaezlge.entity.cartBook.CartBookId;
import com.skrookies.dahaezlge.service.cart.CartService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    public List<CartBookId> setCartList(String user_id){
        return cartService.setCartList(user_id);
    }

}
