package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;

import java.util.List;

public interface BookRepository {
    public List<BookDto> getBookInfo(int book_id);

    List<BookDto> getCartBookInfo(List<Integer> bookIdList);
}
