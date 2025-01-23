package com.skrookies.dahaezlge.service.book;

import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getBookInfo(int book_id){
        return bookRepository.getBookInfo(book_id);
    }

}
