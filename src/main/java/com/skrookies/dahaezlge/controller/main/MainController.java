package com.skrookies.dahaezlge.controller.main;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final BookService bookService;
    private final XssFilterService xssFilterService;

    /** session에서 user_id 획득 */
    public String login_id(HttpSession session) {
        return (String) session.getAttribute("user_id");
    }

    /** 기본 페이지 전체 책 조회 */
    @GetMapping("/")
    public String main(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "") String sdate, @RequestParam(defaultValue = "") String edate, Model model ) {

        int totalBooks = 0;

        List<Map<String, Object>> books;
        if(keyword.isEmpty() && sdate.isEmpty() && edate.isEmpty()) {
            totalBooks = bookService.getTotalBooks(); // 전체 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooks();
        }
        else if (sdate.isEmpty() || edate.isEmpty()) {

            totalBooks = bookService.findBookListByKeyword(keyword).size(); // 키워드 검색 책 개수

            books = bookService.getBooksWithKeyword(keyword);
        }
        else if (keyword.isEmpty()) {

            totalBooks = bookService.findBookListByDate(dateFormatter(sdate), dateFormatter(edate)).size(); // 날짜 검색 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooksWithDate(dateFormatter(sdate), dateFormatter(edate));
        }
        else{

            totalBooks = bookService.findBookListByBoth(keyword, dateFormatter(sdate), dateFormatter(edate)).size(); // 키워드&날짜 검색 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooksWithBoth(keyword, dateFormatter(sdate), dateFormatter(edate));
        }

        // JSP로 데이터 전달
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sdate", sdate);
        model.addAttribute("edate", edate);

        return "eBookMain"; // eBookMain.jsp 렌더링
    }


    /** 기본 페이지 검색한 책 조회
     * 검색어 기준, 날짜 기준 */
    @GetMapping("/index")
    public String eBookMain(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "") String sdate, @RequestParam(defaultValue = "") String edate, Model model ) {

        // XSS 필터링 적용
        keyword = xssFilterService.filter(keyword); // keyword 필터링
        sdate = xssFilterService.filter(sdate); // sdate 필터링
        edate = xssFilterService.filter(edate); // edate 필터링

        int totalBooks = 0;

        List<Map<String, Object>> books;
        if(keyword.isEmpty() && sdate.isEmpty() && edate.isEmpty()) {
            totalBooks = bookService.getTotalBooks(); // 전체 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooks();
        }
        else if (sdate.isEmpty() || edate.isEmpty()) {

            totalBooks = bookService.findBookListByKeyword(keyword).size(); // 키워드 검색 책 개수

            books = bookService.getBooksWithKeyword(keyword);
        }
        else if (keyword.isEmpty()) {

            totalBooks = bookService.findBookListByDate(dateFormatter(sdate), dateFormatter(edate)).size(); // 날짜 검색 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooksWithDate(dateFormatter(sdate), dateFormatter(edate));
        }
        else{

            totalBooks = bookService.findBookListByBoth(keyword, dateFormatter(sdate), dateFormatter(edate)).size(); // 키워드&날짜 검색 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooksWithBoth(keyword, dateFormatter(sdate), dateFormatter(edate));
        }

        // JSP로 데이터 전달
        model.addAttribute("books", books);
        log.info(books.toString());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sdate", sdate);
        model.addAttribute("edate", edate);

        return "eBookMain"; // eBookMain.jsp 렌더링
    }


    /** 책 상세 정보 조회 */
    @GetMapping("/eBookDetail")
    public String setBookInfo(Model model, @RequestParam("book_id") Long book_id, HttpSession session){
        BookDto bookInfo = bookService.getBookInfo(book_id);
        session.setAttribute("book_id", book_id);
        model.addAttribute("bookInfo", bookInfo);
        log.info("page_move: eBookDetail.jsp");
        return "eBookDetail";
    }

    /** String 타입의 date 변수를 LocalDateTime 형식으로 변경 */
    private LocalDateTime dateFormatter(String date){

        String formattedDate = date + " 00:00:00";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(formattedDate, formatter);

        return localDateTime;
    }

}
