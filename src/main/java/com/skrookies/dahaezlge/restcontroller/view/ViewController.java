package com.skrookies.dahaezlge.restcontroller.view;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
import com.skrookies.dahaezlge.restcontroller.view.dto.*;
import com.skrookies.dahaezlge.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    private final BookService bookService;

    @PostMapping(value = "/booklist")
    public ResponseEntity<BookListCapDto> bookList(){

        List<Map<String, Object>> bookList = bookService.findAllBooks();

        log.info("Android api Book DB에 저장된 bookList size: {}", bookList.size());

        List<BookListDto> bookListDtoList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : bookList) {

            BookListDto bookListDto = new BookListDto();
            bookListDto.setBook_id(((BigDecimal) stringObjectMap.get("book_id")).longValue());
            bookListDto.setTitle((String) stringObjectMap.get("book_title"));
            bookListDto.setPrice(((BigDecimal) stringObjectMap.get("book_price")).intValue());
            bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
            bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")));
            bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

            bookListDtoList.add(bookListDto);
        }

        bookListDtoList = del_bookies_data(bookListDtoList);

        log.info("Android api 반환값으로 변환된 bookList size: {}", bookListDtoList.size());

        BookListCapDto bookListCapDto = new BookListCapDto();
        bookListCapDto.setBook_list(bookListDtoList);

        log.info("List Cap 할당 여부: {}", !bookListCapDto.getBook_list().isEmpty());

        return ResponseEntity.ok()
                .body(bookListCapDto);

    }


    /** book 검색 데이터 */
    @PostMapping("/search")
    public ResponseEntity<BookListCapDto> bookSearch(@RequestBody @Valid BookSearchRequestDto bookSearchRequestDto){

        List<BookListDto> bookList = new ArrayList<>();

        /** service의 findAllBooks() 메소드 중복 사용을 방지하고자
         * 그대로 가져와 Controller에서 BookListDto로 변환 후 return
         * 그 외 검색어 기반 데이터 출력은 Service에서 처리 후 return */
        if(bookSearchRequestDto.getKeyword() == null && bookSearchRequestDto.getSdate() == null && bookSearchRequestDto.getEdate() == null){

            List<Map<String, Object>> bookData = bookService.findAllBooks();

            for (Map<String, Object> stringObjectMap : bookData) {

                BookListDto bookListDto = new BookListDto();
                bookListDto.setBook_id(((BigDecimal) stringObjectMap.get("book_id")).longValue());
                bookListDto.setTitle((String) stringObjectMap.get("book_title"));
                bookListDto.setPrice(((BigDecimal) stringObjectMap.get("book_price")).intValue());
                bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
                bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")));
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                bookList.add(bookListDto);
            }
        }
        else if(bookSearchRequestDto.getSdate() == null || bookSearchRequestDto.getEdate() == null){

            bookList = bookService.findBookListByKeyword(bookSearchRequestDto.getKeyword());
        }
        else if(bookSearchRequestDto.getKeyword() == null){

            bookList = bookService.findBookListByDate(bookSearchRequestDto.getSdate().toString(), bookSearchRequestDto.getEdate().toString());
        }
        else{
            bookList = bookService.findBookListByBoth(bookSearchRequestDto.getKeyword(), bookSearchRequestDto.getSdate().toString(), bookSearchRequestDto.getEdate().toString());
        }

        bookList = del_bookies_data(bookList);

        BookListCapDto bookListCapDto = new BookListCapDto();
        bookListCapDto.setBook_list(bookList);

        return ResponseEntity.ok()
                .body(bookListCapDto);

    }

    /** book 상세 페이지 데이터
     * @return BookDetailDto */
    @GetMapping("/bookdetail/{book_id}")
    public ResponseEntity<BookDetailDto> bookDetail(@PathVariable("book_id") String book_id){

        BookDto book_data = bookService.getBookInfo(Long.parseLong(book_id));

        BookDetailDto bookDetailDto = new BookDetailDto();

        bookDetailDto.setBook_id(book_data.getBook_id());
        bookDetailDto.setTitle(book_data.getBook_title());
        bookDetailDto.setWriter(book_data.getBook_auth());
        bookDetailDto.setPrice(book_data.getBook_price());
        bookDetailDto.setWrite_date(book_data.getBook_reg_date());
        bookDetailDto.setBook_img_path(book_data.getBook_img_path());
        bookDetailDto.setBook_summary(book_data.getBook_summary());

        return ResponseEntity.ok()
                .body(bookDetailDto);
    }

    /** viewer page */
    @PostMapping("/book/viewer")
    public ResponseEntity<StatusDto> viewer(@RequestBody @Valid ViewerDto viewerDto){

//        StatusDto statusDto = new StatusDto(bookService.viewer(viewerDto.getBook_id()));
        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(true);

        return ResponseEntity.ok()
                .body(statusDto);

    }

    private List<BookListDto> del_bookies_data(List<BookListDto> books){

        List<String> webtoon_titles = List.of(
                "지극히 평범한 생활", "불같은 청춘", "고스트 피처", "자작 보드게임 동아리",
                "로로의 일본생활 다이어리", "170km", "루저들의 세계여행", "의미불명 개그만화",
                "좀비신드롬", "킥오프");

        List<BookListDto> webtoon_datas = new ArrayList<>();
        for(int i = 0; i < books.size(); i++){
            for(String webtoon_title : webtoon_titles){
                if(books.get(i).getTitle().equals(webtoon_title)){
                    log.info("find webtoon {}", webtoon_title);

                    BookListDto webtoon_data = books.get(i);
                    webtoon_datas.add(webtoon_data);

                    break;
                }
            }
        }

        return webtoon_datas;
    }


}
