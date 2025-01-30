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
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String setPurchaseItem(Model model, RedirectAttributes redirectAttributes, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");
        log.info("detail controller addcart");
        if (user_id == null){
            log.info("detail controller user id null");
            redirectAttributes.addFlashAttribute("messageLoginForm","로그인이 필요합니다.");
            return "redirect:/loginForm";
        }
        else {
            Long book_id = (Long) session.getAttribute("book_id");

            BookDto book_info = bookService.getBookInfo(book_id);

            List<BookDto> purchaseList = new ArrayList<>();
            purchaseList.add(book_info);

            model.addAttribute("purchaseList", purchaseList);

            int userPoint = userService.userPoint(user_id);
            model.addAttribute("userPoint", userPoint);

            String purchaseUrl = "/purchaseItemProc";
            model.addAttribute("purchaseUrl", purchaseUrl);

            session.setAttribute("book_id", book_id);

            return "eBookPurchase";
        }
    }

    @PostMapping("/purchaseProc")
    @ResponseBody
    public Map<String, String> purchaseProc(Model model, RedirectAttributes redirectAttributes, HttpSession session,
                                                @RequestBody Map<String, Object> requestBody) {
        log.info("purchaseProc");
        String user_id = (String) session.getAttribute("user_id");
        int user_point = (int) session.getAttribute("point");
        int total_book_price = Integer.parseInt(requestBody.get("totalBookPrice").toString());

        Map<String, String> response = new HashMap<>();

        if (total_book_price > user_point) {
            log.info("total_book_price > user_point");
            response.put("status", "charge");
            response.put("message", "충전 포인트가 부족합니다.");
        } else {
            log.info("user_point: "+ user_point);
            String purchaseResult = purchaseService.purchaseCart(user_id, total_book_price);
            if("success".equals(purchaseResult)){
                log.info("purchaseCart success");
                int point = userService.userPoint(user_id);
                session.setAttribute("point", point);
                response.put("status", "purchase");
                response.put("message", "결제가 완료되었습니다.");
            } else if ("exists".equals(purchaseResult)) {
                response.put("status", "exists");
                response.put("message", "이미 구매한 도서가 포함되어 있습니다.");
            } else {
                log.info("purchase fail");
                response.put("status", "error");
                response.put("message", "결제 도중 오류가 발생하였습니다.");
            }
        }
        return response;
    }

    @PostMapping("/purchaseItemProc")
    @ResponseBody
    public Map<String, String> purchaseItemProc(Model model, RedirectAttributes redirectAttributes, HttpSession session,
                                   @RequestBody Map<String, Object> requestBody){
        String user_id = (String) session.getAttribute("user_id");
        int user_point = (int) session.getAttribute("point");
        int total_book_price = Integer.parseInt(requestBody.get("totalBookPrice").toString());

        Map<String, String> response = new HashMap<>();

        log.info("purchaseItemProc");
        if (total_book_price > user_point) {
            log.info("total_book_price > user_point");
            response.put("status", "charge");
            response.put("message", "충전 포인트가 부족합니다.");
        } else {
            log.info("user_point: "+ user_point);
            Long book_id = (Long) session.getAttribute("book_id");

            String purchaseItemResult = purchaseService.purchaseItem(user_id, book_id, total_book_price);
            if("success".equals(purchaseItemResult)){
                log.info("purchaseItem success");
                int point = userService.userPoint(user_id);
                session.setAttribute("point", point);
                session.removeAttribute("book_id");
                response.put("status", "purchase");
                response.put("message", "결제가 완료되었습니다.");
            } else if ("exists".equals(purchaseItemResult)) {
                response.put("status", "exists");
                response.put("message", "이미 구매한 도서가 포함되어 있습니다.");
            } else {
                log.info("purchase fail");
                response.put("status", "error");
                response.put("message", "결제 도중 오류가 발생하였습니다.");
            }
        }
        return response;
    }
}
