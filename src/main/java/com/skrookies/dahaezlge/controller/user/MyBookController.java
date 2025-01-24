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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@Controller
@RequiredArgsConstructor

public class MyBookController {
    private final PurchaseService purchaseService;
    private final BookService bookService;

    @GetMapping("/myBook")
    public String myBook_form(Model model, HttpSession session){
        log.info("page_move: myBook.jsp");
        String user_id = (String) session.getAttribute("user_id");
        if (user_id != null) {
            List<Long> books_id = purchaseService.purchaseBook_list(user_id);
            log.info("books_id: "+ Arrays.toString(books_id.toArray()));
            log.info(books_id.toString());
            List<BookDto> books_info = new ArrayList<>();
            if (books_id != null){
                for(int i = 0; i< books_id.size(); i++){
                    log.info(String.valueOf("id" + books_id.get(i)));
                    BookDto book_info = bookService.getBookInfo(books_id.get(i));
                    log.info (String.valueOf(book_info));
                    books_info.add(book_info);
                }
                model.addAttribute("books_info", books_info);
                model.addAttribute("books_size", books_info.size());
                log.info(String.valueOf(books_id.size()));
                return "myBook";
            }else {
                return "myBook";
            }

        } else {
            return "redirect:/loginForm";
        }


    }

}
