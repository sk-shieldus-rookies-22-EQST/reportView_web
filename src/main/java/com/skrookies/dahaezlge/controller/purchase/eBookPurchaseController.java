package com.skrookies.dahaezlge.controller.purchase;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.cart.CartService;
import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.text.AttributedString;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class eBookPurchaseController {
    private final CartService cartService;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final BookService bookService;

    @RequestMapping("/eBookPurchase")
    public String setPurchaseList(Model model, HttpSession session) {
        String user_id = (String) session.getAttribute("user_id");

        List<BookDto> purchaseList = cartService.setCartList(user_id);
        model.addAttribute("purchaseList", purchaseList);

        int userPoint = userService.userPoint(user_id);
        session.setAttribute("point", userPoint);

        String purchaseUrl = "/purchaseProc";
        model.addAttribute("purchaseUrl", purchaseUrl);

        return "eBookPurchase";
    }

    @PostMapping("/eBookPurchaseItem")
    public String setPurchaseItem(Model model, @RequestParam("book_id") Long book_id, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");

        BookDto book_info = bookService.getBookInfo(book_id);

        List<BookDto> purchaseList = new ArrayList<>();
        purchaseList.add(book_info);

        model.addAttribute("purchaseList", purchaseList);

        int userPoint = userService.userPoint(user_id);
        model.addAttribute("userPoint", userPoint);

        String purchaseUrl = "/purchaseItemProc";
        model.addAttribute("purchaseUrl", purchaseUrl);

        return "eBookPurchase";
    }

    @PostMapping("/purchaseProc")
    public String purchaseProc(Model model, RedirectAttributes redirectAttributes,
                               @RequestParam("total_book_price") int total_book_price, HttpSession session){
        log.info("purchaseProc");
        log.info("total_book_price");
        String user_id = (String) session.getAttribute("user_id");
        int user_point = (int) session.getAttribute("point");

        if (total_book_price > user_point) {
            log.info("total_book_price > user_point");
            // 조건에 맞으면 모달을 띄우기 위한 정보를 JSP로 전달
            model.addAttribute("showPointChargerModal", true);
            model.addAttribute("totalBookPrice", total_book_price);  // 모달에서 필요한 데이터
            return "eBookPurchase";
        } else {
            log.info("user_point: "+ user_point);
            if (purchaseService.purchaseCart(user_id, total_book_price)) {
                log.info("purchase success");
                int point = userService.userPoint(user_id);
                session.setAttribute("point", point);
                redirectAttributes.addFlashAttribute("messageMypurchase", "결제가 완료되었습니다.");
                return "redirect:/myPurchase";
            } else {
                log.info("purchase fail");
                redirectAttributes.addFlashAttribute("messageCart", "결제를 실패했습니다.");
                return "redirect:/eBookCart";
            }
        }
    }

    @PostMapping("/purchaseItemProc")
    public String purchaseItemProc(RedirectAttributes redirectAttributes, HttpSession session,
                                   @RequestParam("book_id") Long book_id, @RequestParam("total_book_price") int total_book_price){
        String user_id = (String) session.getAttribute("user_id");
        int user_point = (int) session.getAttribute("point");

        log.info("purchaseProc");
        if (total_book_price > user_point) {
            log.info("total_book_price > user_point");
            return "forward:/pointCharger";
        } else {
            log.info("user_point: "+ user_point);
            if(purchaseService.purchaseItem(user_id, book_id, total_book_price)){
                log.info("purchaseItem success");
                int point = userService.userPoint(user_id);
                session.setAttribute("point", point);
                redirectAttributes.addFlashAttribute("messageMypurchase","결제가 완료되었습니다.");
                return "redirect:/myPurchase";
            } else {
                log.info("purchase fail");
                redirectAttributes.addFlashAttribute("messageCart","결제를 실패했습니다.");
                return "redirect:/eBookCart";
            }
        }
    }
}
