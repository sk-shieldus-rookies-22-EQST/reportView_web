package com.skrookies.dahaezlge.controller.book;

import ch.qos.logback.core.model.Model;
import com.skrookies.dahaezlge.service.bookDetail.BookDetailService;
import org.springframework.web.bind.annotation.PostMapping;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookDetailController {
    private final BookDetailService bookDetailService;

    public boolean addCart(String user_id, int book_id){
        int cart_id = bookDetailService.addCart(user_id, book_id);


        return true;
    }
}
