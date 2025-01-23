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

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookDetailController {
    private final BookDetailService bookDetailService;
    private final BookService bookService;

    @PostMapping("/addCart")
    public boolean addCart(String user_id,@RequestParam("book_id") Long book_id){
        return bookDetailService.addCart(user_id, book_id);
    }
}
