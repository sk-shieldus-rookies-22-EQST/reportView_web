package com.skrookies.dahaezlge.restcontroller.view;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.entity.book.Book;
//import com.skrookies.dahaezlge.entity.book.BookInfo;
import com.skrookies.dahaezlge.restcontroller.util.StatusDto;
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
    public ResponseEntity<List<BookListDto>> bookList(){

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

        return ResponseEntity.ok()
                .body(bookListDto);

    }


    @PostMapping("/search")
    public ResponseEntity<List<BookSearchDto>> bookSearch(@RequestBody @Valid BookSearchRequestDto bookSearchRequestDto){

        // bookService.searchBook(bookSearchRequestDto);

        List<BookSearchDto> bookSearchDto = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            // 수령한 데이터를 List<Book> 개수 만큼 반복
            // 수령한 데이터를 BookListDto bookListDto = new BookListDto(); 에 할당
            // bookSearchDto.add(bookListDto);
        }


        return ResponseEntity.ok()
                .body(bookSearchDto);

    }

    @GetMapping("/bookdetail/{book_id}")
    public ResponseEntity<BookDetailDto> bookDetail(@PathVariable("book_id") String book_id){

        //List<Book> book_data = bookService.getBookInfo(Integer.parseInt(book_id));

        //BookDetailDto bookDetailDto = new BookDetailDto();

//        return ResponseEntity.ok()
//                .body(bookDetailDto);
        return ResponseEntity.ok()
                .body(null);
    }

    @PostMapping("/book/viewer")
    public ResponseEntity<StatusDto> viewer(@RequestBody @Valid ViewerDto viewerDto){

//        StatusDto statusDto = new StatusDto(bookService.viewer(viewerDto.getBook_id()));
        StatusDto statusDto = new StatusDto(true);
        return ResponseEntity.ok()
                .body(statusDto);

    }
}
