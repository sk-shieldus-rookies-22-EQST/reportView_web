package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPurchaseController {
    private final BookService bookService;
    private final PurchaseService purchaseService;

    /** 구매 내역 페이지 */
    @GetMapping("/myPurchase")
    public String myPurchaseList(Model model, HttpSession session){
        log.info("page_move: myPurchase.jsp");
        String user_id = (String) session.getAttribute("user_id");
        if (user_id != null) {
            List<Long> books_id = purchaseService.purchaseBook_list(user_id);
            log.info("books_id: "+ Arrays.toString(books_id.toArray()));
            log.info(books_id.toString());
            List<List<Map<String, Object>>> books_info = new ArrayList<>();
            if (!books_id.isEmpty()){
                for(int i = 0; i< books_id.size(); i++){
                    log.info(String.valueOf("id" + books_id.get(i)));
                    List<Map<String, Object>> book_info = bookService.getMyBooks((Long)books_id.get(i));
                    log.info (String.valueOf(book_info));
                    books_info.add(book_info);
                }
                model.addAttribute("books", books_info);
                return "myPurchase";
            } else {
                return "myPurchase";
            }
        } else {
            return "redirect:/loginForm";
        }
    }
}
