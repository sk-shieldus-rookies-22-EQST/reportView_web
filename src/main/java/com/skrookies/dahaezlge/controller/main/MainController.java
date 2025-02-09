package com.skrookies.dahaezlge.controller.main;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.service.book.BookService;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDate;
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
    private final SqlFilterService sqlFilterService;

    /** session에서 user_id 획득 */
    public String login_id(HttpSession session) {
        return (String) session.getAttribute("user_id");
    }

    /** 기본 페이지 전체 책 조회 */
    @GetMapping("/")
    public String main(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "") String sdate, @RequestParam(defaultValue = "") String edate, @RequestParam(defaultValue = "") String sort, @RequestParam(defaultValue = "") String direction,Model model ) {

        int totalBooks = 0;

        List<Map<String, Object>> books;
        if(keyword.isEmpty() && sdate.isEmpty() && edate.isEmpty()) {
            totalBooks = bookService.getTotalBooks(); // 전체 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooks(sort, direction);
        }
        else if (sdate.isEmpty() || edate.isEmpty()) {

            totalBooks = bookService.findBookListByKeyword(keyword).size(); // 키워드 검색 책 개수

            books = bookService.getBooksWithKeyword(keyword, sort, direction);
        }
        else if (keyword.isEmpty()) {

            totalBooks = bookService.findBookListByDate(sdate, edate).size(); // 날짜 검색 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooksWithDate(sdate, edate, sort, direction);
        }
        else{

            totalBooks = bookService.findBookListByBoth(keyword, sdate, edate).size(); // 키워드&날짜 검색 책 개수

            // 현재 페이지에 해당하는 책 목록 가져오기
            books = bookService.getBooksWithBoth(keyword, sdate, edate, sort, direction);
        }

        // JSP로 데이터 전달
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sdate", sdate);
        model.addAttribute("edate", edate);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);

        return "eBookMain"; // eBookMain.jsp 렌더링
    }


    /** 기본 페이지 검색한 책 조회
     * 검색어 기준, 날짜 기준 */
    @GetMapping("/index")
    public String eBookMain(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "") String sdate, @RequestParam(defaultValue = "") String edate, @RequestParam(defaultValue = "") String sort,@RequestParam(defaultValue = "") String direction,Model model ) {

        // XSS 필터링 적용
        keyword = xssFilterService.filter(keyword); // keyword 필터링
        keyword = sqlFilterService.filter(keyword);

        sdate = xssFilterService.filter2(sdate); // sdate 필터링 (sql 공격 허용)
        sdate = sqlFilterService.filter3(sdate); //
        edate = xssFilterService.filter2(edate); // edate 필터링
        edate = sqlFilterService.filter3(edate);

        List<Map<String, Object>> books = bookService.getBooksWithFilters(keyword, sdate, edate, sort, direction);


        // JSP로 데이터 전달
        model.addAttribute("books", books);
        //log.info(books.toString());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sdate", sdate);
        model.addAttribute("edate", edate);
        model.addAttribute("sort", sort);
        model.addAttribute("sort", direction);

        return "eBookMain"; // eBookMain.jsp 렌더링
    }


    /** 책 상세 정보 조회 */
    @GetMapping("/eBookDetail")
    public String setBookInfo(Model model, @RequestParam("book_id") Long book_id, HttpSession session){
        BookDto bookInfo = bookService.getBookInfo(book_id);

        // Timestamp를 LocalDateTime으로 변환
        LocalDateTime localDateTime = bookInfo.getBook_reg_date().toLocalDateTime();

        // 날짜 및 시간 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDateTime을 String으로 변환
        String formattedDate = localDateTime.format(formatter);

        session.setAttribute("book_id", book_id);
        model.addAttribute("bookInfo", bookInfo);
        model.addAttribute("formattedDate", formattedDate);  // 포맷된 날짜 추가
        log.info("page_move: eBookDetail.jsp");
        return "eBookDetail";
    }

    /** String 타입의 date 변수를 Timestamp 형식으로 변경 (시간 설정 안 함) */
    private Timestamp timestamp (String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }

        // 날짜 형식에 맞는 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // String을 LocalDate로 변환 (시간 설정 없음)
        LocalDate localDate = LocalDate.parse(date, formatter);

        // LocalDate를 Timestamp로 변환 (시간은 00:00:00으로 설정됨)
        return Timestamp.valueOf(localDate.atStartOfDay());  // 시간은 00:00:00으로 설정
    }


}
