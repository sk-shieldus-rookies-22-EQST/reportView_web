package com.skrookies.dahaezlge.controller.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.bookDetail.BookDetailService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.FileStore;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookDetailController {
    private final BookDetailService bookDetailService;
    private final BookService bookService;

    @PostMapping("/addCart")
    public String addCart(Model model, RedirectAttributes redirectAttributes, @RequestParam("book_id") Long book_id, HttpSession session){
        String user_id = (String) session.getAttribute("user_id");
        log.info("detail controller addcart");
        if (user_id == null){
            log.info("detail controller user id null");
            redirectAttributes.addFlashAttribute("message","로그인이 필요합니다.");
            return "loginForm";
        }
        else {
            log.info("detail controller do addcart");
            bookDetailService.addCart(user_id, book_id);
            return "forward:/eBookCart";
        }
    }
}
