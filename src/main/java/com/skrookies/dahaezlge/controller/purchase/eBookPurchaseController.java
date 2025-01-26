package com.skrookies.dahaezlge.controller.purchase;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.cart.CartService;
import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import com.skrookies.dahaezlge.service.user.UserService;
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
public class eBookPurchaseController {
    private final CartService cartService;
    private final UserService userService;
    private final PurchaseService purchaseService;

    @PostMapping("/eBookPurchase")
    public String setPurchaseList(Model model, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");

        List<BookDto> purchaseList = cartService.setCartList(user_id);
        model.addAttribute("purchaseList", purchaseList);

        int userPoint = userService.userPoint(user_id);
        model.addAttribute("userPoint", userPoint);

        return "eBookPurchase";
    }

    @PostMapping("/purchaseProc")
    public String purchaseProc(RedirectAttributes redirectAttributes, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");
        log.info("purchaseProc");
        if(purchaseService.purchaseCart(user_id)){
            log.info("purchase success");
            redirectAttributes.addFlashAttribute("messageMypurchase","결제가 완료되었습니다.");
            return "redirect:/myPurchase";
        } else {
            log.info("purchase fail");
            redirectAttributes.addFlashAttribute("messageCart","결제를 실패했습니다.");
            return "redirect:/myPurchase";
        }
    }
}
