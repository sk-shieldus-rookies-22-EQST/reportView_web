package com.skrookies.dahaezlge.restcontroller.view;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.restcontroller.util.StatusDto;
import com.skrookies.dahaezlge.restcontroller.view.dto.BookListDto;
import com.skrookies.dahaezlge.restcontroller.view.dto.ViewerDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {


    @PostMapping("/booklist")
    public ResponseEntity<List<BookListDto>> bookList(){

        /** bookService에서 Book Entity를 모두 가져온다
         * BookListDto 형식에 맞게 데이터를 setting한다.
         * 만든 BookListDto를 List<BookListDto>에 add 한다.*/

        List<BookListDto> bookListDto = new ArrayList<>();
        for(int i = 0; i < 100; i++) {

            bookListDto.add(null);
        }

        return ResponseEntity.ok()
                .body(bookListDto);

    }



    @PostMapping("/book/viewer")
    public ResponseEntity<StatusDto> viewer(@RequestBody @Valid ViewerDto viewerDto){

//        StatusDto statusDto = new StatusDto(bookService.viewer(viewerDto.getBook_id()));
        StatusDto statusDto = new StatusDto(true);
        return ResponseEntity.ok()
                .body(statusDto);

    }
}
