package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.entity.book.BookInfo;

import java.util.List;

public interface BookRepository {
    public List<BookInfo> getBookInfo(int book_id);
}
