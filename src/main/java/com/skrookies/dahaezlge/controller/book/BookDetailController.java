package com.skrookies.dahaezlge.controller.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.bookDetail.BookDetailService;
import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.FileStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookDetailController {
    private final BookDetailService bookDetailService;
    private final PurchaseService purchaseService;
    private final BookService bookService;

    @PostMapping("/addCart")
    @ResponseBody
    public Map<String, String> addCart(@RequestBody Map<String, Long> payload, HttpSession session) {
        Long book_id = payload.get("book_id");
        String user_id = (String) session.getAttribute("user_id");

        Map<String, String> response = new HashMap<>();
        if (user_id == null) {
            response.put("status", "login_required");
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        try {
            // 이미 장바구니에 존재하는지 확인
            boolean isAlreadyInCart = bookDetailService.isBookInCart(user_id, book_id);
            if (isAlreadyInCart) {
                response.put("status", "exists");
                response.put("message", "이미 장바구니에 담겨 있습니다.");
            } else {
                // 장바구니에 추가
                bookDetailService.addCart(user_id, book_id);
                response.put("status", "added");
                response.put("message", "장바구니에 담았습니다.");
            }
        } catch (Exception e) {
            log.error("Error while adding to cart", e);
            response.put("status", "error");
            response.put("message", "오류가 발생했습니다. 관리자에게 문의하세요.");
        }
        return response;
    }
}

