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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    private final BookService bookService;

    @PostMapping("/booklist")
    public ResponseEntity<BookListCapDto> bookList(){

        /** bookService에서 Book Entity를 모두 가져온다
         * BookListDto 형식에 맞게 데이터를 setting한다.
         * 만든 BookListDto를 List<BookListDto>에 add 한다.*/

        // bookService.findAll을 통해 List<Book> 형식으로 데이터 수령할 부분
        List<BookListDto> bookListDto = new ArrayList<>();
        for(int i = 0; i < 100; i++) {

            // 수령한 데이터를 List<Book> 개수 만큼 반복
            // 수령한 데이터를 BookListDto bookListDto = new BookListDto(); 에 할당
            // bookListDto.add(bookListDto);
        }

        BookListCapDto bookListCapDto = new BookListCapDto(bookListDto);

        return ResponseEntity.ok()
                .body(bookListCapDto);

    }


    @PostMapping("/search")
    public ResponseEntity<BookSearchCapDto> bookSearch(@RequestBody @Valid BookSearchRequestDto bookSearchRequestDto){

        // bookService.searchBook(bookSearchRequestDto);

        List<BookSearchDto> bookSearchDto = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            // 수령한 데이터를 List<Book> 개수 만큼 반복
            // 수령한 데이터를 BookListDto bookListDto = new BookListDto(); 에 할당
            // bookSearchDto.add(bookListDto);
        }

        BookSearchCapDto bookSearchCapDto = new BookSearchCapDto(bookSearchDto);

        return ResponseEntity.ok()
                .body(bookSearchCapDto);

    }

    @GetMapping("/bookdetail/{book_id}")
    public ResponseEntity<BookDetailDto> bookDetail(@PathVariable("book_id") String book_id){

        BookDto book_data = bookService.getBookInfo(Long.parseLong(book_id));


        BookDetailDto bookDetailDto = new BookDetailDto(book_data.getBook_id(), book_data.getBook_title(), book_data.getBook_price(), book_data.getBook_auth(), book_data.getBook_summary(), book_data.getBook_img_path());

        return ResponseEntity.ok()
                .body(bookDetailDto);
    }

    @PostMapping("/book/viewer")
    public ResponseEntity<StatusDto> viewer(@RequestBody @Valid ViewerDto viewerDto){

//        StatusDto statusDto = new StatusDto(bookService.viewer(viewerDto.getBook_id()));
        StatusDto statusDto = new StatusDto(true);
        return ResponseEntity.ok()
                .body(statusDto);

    }
}
