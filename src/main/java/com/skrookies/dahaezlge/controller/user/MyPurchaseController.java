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

import java.sql.Timestamp;
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
            List<List> books_list = purchaseService.purchaseBook_list(user_id);
            log.info(books_list.toString());
            // purchase_date를 기준으로 오름차순 정렬
            books_list.sort((book1, book2) -> {
                String date1 = (String) book1.get(1); // book1의 purchase_date (두 번째 요소)
                String date2 = (String) book2.get(1); // book2의 purchase_date (두 번째 요소)
                return date1.compareTo(date2); // 오름차순 정렬
            });
            log.info(books_list.toString());

            List<Map<String, Object>> books_info = new ArrayList<>();
            if (!books_list.isEmpty()){
                for(int i = 0; i< books_list.size(); i++){
                    log.info(String.valueOf("id" + books_list.get(i).get(0)));
                    log.info(String.valueOf("date" + books_list.get(i).get(1)));
                    List<Map<String, Object>> book_info = bookService.getMyBooks(Long.parseLong(books_list.get(i).get(0).toString()));
                    log.info (String.valueOf(book_info));
                    books_info.add(book_info.getFirst());
                }
                log.info (String.valueOf(books_info));
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
