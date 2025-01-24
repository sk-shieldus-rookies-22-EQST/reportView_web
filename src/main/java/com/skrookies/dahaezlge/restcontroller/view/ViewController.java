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
        for(int i = 0; i < bookList.size(); i++){

            BookListDto bookListDto = new BookListDto(
                    (Long) bookList.get(i).get("book_id"),
                    (String) bookList.get(i).get("book_title"),
                    (Integer) bookList.get(i).get("book_price"),
                    (String) bookList.get(i).get("book_auth")
            );

            bookListDtoList.add(bookListDto);
        }

        BookListCapDto bookListCapDto = new BookListCapDto(bookListDtoList);

        return ResponseEntity.ok()
                .body(bookListCapDto);

    }


    /** book 검색 데이터 */
    @PostMapping("/search")
    public ResponseEntity<BookSearchCapDto> bookSearch(@RequestBody @Valid BookSearchRequestDto bookSearchRequestDto){

        List<BookDto> returnBookList = new ArrayList<>();

        if(bookSearchRequestDto.getKeyword() == null || bookSearchRequestDto.getSdate() == null || bookSearchRequestDto.getEdate() == null){

            List<Map<String, Object>> bookList = bookService.findAllBooks();

            for(int i = 0; i < bookList.size(); i++){

                BookDto bookDto = new BookDto(
                        (Long) bookList.get(i).get("book_id"),
                        (String) bookList.get(i).get("book_title"),
                        (String) bookList.get(i).get("book_auth"),
                        (String) bookList.get(i).get("book_path"),
                        (String) bookList.get(i).get("book_summary"),
                        (LocalDateTime) bookList.get(i).get("book_reg_date"),
                        (String) bookList.get(i).get("book_img_path"),
                        (Integer) bookList.get(i).get("book_price")
                );

                returnBookList.add(bookDto);
            }
        }
        else if(bookSearchRequestDto.getSdate() == null || bookSearchRequestDto.getEdate() == null){

            returnBookList = bookService.findBookListByKeyword(bookSearchRequestDto.getKeyword());
        }
        else if(bookSearchRequestDto.getKeyword() == null){

            returnBookList = bookService.findBookListByDate(bookSearchRequestDto.getSdate(), bookSearchRequestDto.getEdate());
        }
        else{
            returnBookList = bookService.findBookListByBoth(bookSearchRequestDto);
        }

        List<BookSearchDto> bookSearchDtoList = new ArrayList<>();
        for(int i = 0; i < returnBookList.size(); i++){
            BookSearchDto bookSearchDto = new BookSearchDto(
                    (Long) returnBookList.get(i).getBook_id(),
                    (String) returnBookList.get(i).getBook_title(),
                    (Integer) returnBookList.get(i).getBook_price(),
                    (String) returnBookList.get(i).getBook_auth(),
                    (LocalDateTime) returnBookList.get(i).getBook_reg_date(),
                    (String) returnBookList.get(i).getBook_img_path()
            );
            bookSearchDtoList.add(bookSearchDto);
        }

        BookSearchCapDto bookSearchCapDto = new BookSearchCapDto(bookSearchDtoList);

        return ResponseEntity.ok()
                .body(bookSearchCapDto);

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
