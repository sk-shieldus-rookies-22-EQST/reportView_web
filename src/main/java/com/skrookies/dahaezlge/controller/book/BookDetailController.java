package com.skrookies.dahaezlge.controller.book;

import ch.qos.logback.core.model.Model;
import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.book.BookInfo;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.service.book.BookService;
import org.springframework.web.bind.annotation.PostMapping;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookDetailController {
    private final BookService bookService;

    @PostMapping("/eBookCart")
    public Boolean addCart(Model model, String user_id, int book_id){

        return true;
    }
}
