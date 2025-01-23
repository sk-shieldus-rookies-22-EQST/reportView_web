package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.entity.book.Book;

import java.util.List;

public interface BookRepository {
    public List<Book> getBookInfo(int book_id);

    List<Book> getCartBookInfo(List<Integer> bookIdList);
}
