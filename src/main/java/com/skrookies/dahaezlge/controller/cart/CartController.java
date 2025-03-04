package com.skrookies.dahaezlge.controller.cart;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.cart.CartService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;


    /** 장바구니 요소 조회 */
    @RequestMapping("/eBookCart")
    public String setCartList(Model model, RedirectAttributes redirectAttributes, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");
        if (user_id == null){
            log.info("cart controller user_id null");
            return "redirect:/loginForm";
        }
        else {
            log.info("cart controller ");
            List<BookDto> cartList = cartService.setCartList(user_id);
            model.addAttribute("cartList", cartList);
            return "eBookCart";
        }
    }


    /** 장바구니 물품 삭제 */
    @PostMapping("/deleteCart")
    public String delCartItem(Model model, RedirectAttributes redirectAttributes,
                              @RequestParam("book_id") Long book_id, HttpSession session){
        log.info("cart Controller delCart");
        log.info(String.valueOf(book_id));
        String user_id = (String) session.getAttribute("user_id");
        if (user_id == null){
            return "redirect:/loginForm";
        }
        else {
            log.info("cart Controller delCartItem");
            if(cartService.delCartItem(user_id, book_id)){
                session.setAttribute("messageCartDeleted", "장바구니에서 삭제되었습니다.");
            } else {
                session.setAttribute("messageCartDeletedFailed", "삭제에 실패했습니다.");
            }
            return "redirect:/eBookCart";
        }
    }
}
