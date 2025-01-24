package com.skrookies.dahaezlge.controller.cart;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.cart.CartService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/eBookCart")
    public String setCartList(Model model, RedirectAttributes redirectAttributes, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");
        if (user_id == null){
            log.info("cart controller user_id null");
            redirectAttributes.addFlashAttribute("messageLoginForm","로그인이 필요합니다.");
            return "redirect:/loginForm";
        }
        else {
            log.info("cart controller ");
            List<BookDto> cartList = cartService.setCartList(user_id);
            model.addAttribute("cartList", cartList);
            return "eBookCart";
        }
    }

    @PostMapping("/deleteCart")
    public Boolean delCartList(Model model, String user_id, Long book_id){
        return true;
    }

    @PostMapping("/Purchase")
    public String purchase(Model model, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");
        List<BookDto> cartList = cartService.setCartList(user_id);
        model.addAttribute("cartList", cartList);
        return "PurchaseList";
    }

}
