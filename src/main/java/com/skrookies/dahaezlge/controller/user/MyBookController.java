package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.purchase.Purchase;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.repository.book.DBBookRepository;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@Transactional
@Controller
@RequiredArgsConstructor
public class MyBookController {
    private final PurchaseService purchaseService;
    private final BookService bookService;

    /** 내 서재 페이지 */
    @GetMapping("/myBook")
    public String myBook_form(Model model, HttpSession session){
        log.info("page_move: myBook.jsp");
        String user_id = (String) session.getAttribute("user_id");
        if (user_id != null) {
            List<List> books_id = purchaseService.purchaseBook_list(user_id);
            log.info("books_id: "+ Arrays.toString(books_id.toArray()));
            log.info(books_id.toString());
            List<Map<String, Object>> books_info = new ArrayList<>();
            if (!books_id.isEmpty()) {

                for (int i = 0; i < books_id.size(); i++) {
                    // 로그 출력: 책 ID 확인
                    log.info("id" + books_id.get(i));  // String.valueOf는 필요 없음

                    List<Map<String, Object>> book_info = bookService.getMyBooks(Long.parseLong(books_id.get(i).get(0).toString()));

                    // 첫 번째 책 정보만 가져와서 books_info에 추가
                    if (!book_info.isEmpty()) {
                        books_info.add(book_info.get(0));  // 첫 번째 책 정보만 추가
                    }

                    log.info("books_info: " + books_info.toString());
                }

                // book_title를 기준으로 오름차순 정렬
                Collections.sort(books_info, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> book1, Map<String, Object> book2) {
                        String bookTitle1 = (String) book1.get("BOOK_TITLE");
                        String bookTitle2 = (String) book2.get("BOOK_TITLE");
                        return bookTitle1.compareTo(bookTitle2);  // 오름차순 정렬
                    }
                });

                // 정렬된 books_info 출력
                log.info("Sorted books_info: " + books_info.toString());
                model.addAttribute("books_info", books_info);
                model.addAttribute("books_size", books_info.size());
                log.info(String.valueOf(books_id.size()));
                log.info(String.valueOf(books_info.toString()));
                return "myBook";
            }else {
                return "myBook";
            }

        } else {
            return "redirect:/loginForm";
        }


    }

}
