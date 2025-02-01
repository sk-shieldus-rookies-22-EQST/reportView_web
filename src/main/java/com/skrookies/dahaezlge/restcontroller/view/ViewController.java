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

    @PostMapping("/booklist")
    public ResponseEntity<BookListCapDto> bookList(){

        List<Map<String, Object>> bookList = bookService.findAllBooks();

        List<BookListDto> bookListDtoList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : bookList) {

            BookListDto bookListDto = new BookListDto();
            bookListDto.setBook_id((Long) stringObjectMap.get("book_id"));
            bookListDto.setTitle((String) stringObjectMap.get("book_title"));
            bookListDto.setPrice((Integer) stringObjectMap.get("book_price"));
            bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
            bookListDto.setWrite_date((LocalDateTime) stringObjectMap.get("book_reg_date"));
            bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

            bookListDtoList.add(bookListDto);
        }

        BookListCapDto bookListCapDto = new BookListCapDto(bookListDtoList);

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
                bookListDto.setBook_id((Long) stringObjectMap.get("book_id"));
                bookListDto.setTitle((String) stringObjectMap.get("book_title"));
                bookListDto.setPrice((Integer) stringObjectMap.get("book_price"));
                bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
                bookListDto.setWrite_date((LocalDateTime) stringObjectMap.get("book_reg_date"));
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                bookList.add(bookListDto);
            }
        }
        else if(bookSearchRequestDto.getSdate() == null || bookSearchRequestDto.getEdate() == null){

            bookList = bookService.findBookListByKeyword(bookSearchRequestDto.getKeyword());
        }
        else if(bookSearchRequestDto.getKeyword() == null){

            bookList = bookService.findBookListByDate(bookSearchRequestDto.getSdate(), bookSearchRequestDto.getEdate());
        }
        else{
            bookList = bookService.findBookListByBoth(bookSearchRequestDto);
        }

        BookListCapDto BookListCapDto = new BookListCapDto(bookList);

        return ResponseEntity.ok()
                .body(BookListCapDto);

    }

    /** book 상세 페이지 데이터 */
    @GetMapping("/bookdetail/{book_id}")
    public ResponseEntity<BookDetailDto> bookDetail(@PathVariable("book_id") String book_id){

        BookDto book_data = bookService.getBookInfo(Long.parseLong(book_id));

        BookDetailDto bookDetailDto = new BookDetailDto(book_data.getBook_id(), book_data.getBook_title(), book_data.getBook_price(), book_data.getBook_auth(), book_data.getBook_summary(), book_data.getBook_img_path());

        return ResponseEntity.ok()
                .body(bookDetailDto);
    }

    /** viewer page */
    @PostMapping("/book/viewer")
    public ResponseEntity<StatusDto> viewer(@RequestBody @Valid ViewerDto viewerDto){

//        StatusDto statusDto = new StatusDto(bookService.viewer(viewerDto.getBook_id()));
        StatusDto statusDto = new StatusDto(true);
        return ResponseEntity.ok()
                .body(statusDto);

    }
}
