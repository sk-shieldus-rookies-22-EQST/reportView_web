package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.entity.book.Book;

import java.util.List;
import java.util.Map;

public interface BookRepository {

    BookDto getBookInfo(Long book_id);

    List<BookDto> getCartBookInfo(List<Integer> bookIdList);

    // 페이지별로 책 리스트 가져오기
    List<Map<String, Object>> getBooks(int page, int pageSize);

    // 전체 책 개수
    int getTotalBooks();

    // 전체 책 정보를 가져오는 메서드 (BookInfo 대신 Map 사용)
    List<Map<String, Object>> findAllBooks();

}
