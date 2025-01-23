package com.skrookies.dahaezlge.controller.main;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.controller.user.Dto.SessionDto;
import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final BookService bookService;

    public String login_id(HttpSession session) {
        return (String) session.getAttribute("user_id");
    }

    @GetMapping("/index")
    public String eBookMain(
            @RequestParam(defaultValue = "1") int page, // 현재 페이지 (기본값: 1)
            Model model
    ) {
        int pageSize = 10; // 한 페이지에 출력할 책 개수
        int totalBooks = bookService.getTotalBooks(); // 전체 책 개수
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        // 현재 페이지에 해당하는 책 목록 가져오기
        List<Map<String, Object>> books = bookService.getBooks(page, pageSize);

        // JSP로 데이터 전달
        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "eBookMain"; // eBookMain.jsp 렌더링
    }


    @GetMapping("/banner")
    public String banner_form(){

        log.info("page_move: banner.jsp");
        return "banner";
    }





//

    @GetMapping("/myInfoModify")
    public String myInfoModify_form(){

        log.info("page_move: myInfoModify.jsp");
        return "myInfoModify";
    }

    @GetMapping("/myInfoProc")
    public String myInfoProc_form(){

        log.info("page_move: myInfoProc.jsp");
        return "myInfoProc";
    }

    @GetMapping("/eBookMain")
    public String eBookDetail_form(){

         log.info("page_move: eBookMain.jsp");
        return "eBookMain";
    }

    @GetMapping("/eBookDetail")
    public String setBookInfo(Model model, @RequestParam("book_id") Long book_id){
        BookDto bookInfo = bookService.getBookInfo(book_id);
        model.addAttribute("bookInfo", bookInfo);
        log.info("page_move: eBookDetail.jsp");
        return "eBookDetail";
    }
    //@GetMapping("/eBookDetail")
    //public String eBookDetail_form(){
    //
    //     log.info("page_move: eBookDetail.jsp");
    //    return "eBookDetail";
    //}

    @GetMapping("/eBookCart")
    public String eBookCart_form(){

        log.info("page_move: eBookCart.jsp");
        return "eBookCart";
    }

}
