package com.skrookies.dahaezlge.controller.cart;

import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.cartBook.CartBook;
import com.skrookies.dahaezlge.service.cart.CartService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/showList")
    public String setCartList(Model model, String user_id){
        List<Book> cartList = cartService.setCartList(user_id);
        model.addAttribute("cartList", cartList);
        return "eBookCart";
    }

}
