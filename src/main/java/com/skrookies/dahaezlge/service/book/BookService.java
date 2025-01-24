package com.skrookies.dahaezlge.service.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.entity.purchase.Purchase;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDto getBookInfo(Long book_id){
        return bookRepository.getBookInfo(book_id);
    }

    public List<Map<String, Object>> getBooks(int page, int pageSize) {
        return bookRepository.getBooks(page, pageSize);
    }

    public int getTotalBooks() {
        return bookRepository.getTotalBooks();
    }

    // ▼ 전체 도서 목록 (Map) 가져오기
    public List<Map<String, Object>> findAllBooks() {
        return bookRepository.findAllBooks();
    }

}
