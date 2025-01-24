package com.skrookies.dahaezlge.service.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.restcontroller.view.dto.BookSearchRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<Map<String, Object>> getMyBooks(Long book_id) {
        return bookRepository.getMyBooks(book_id);
    }

    public List<Map<String, Object>> getBooksWithKeyword(String keyword, int page, int pageSize) {
        return bookRepository.getBooksWithKeyword(keyword, page, pageSize);
    }

    public int getTotalBooks() {
        return bookRepository.getTotalBooks();
    }

    // ▼ 전체 도서 목록 (Map) 가져오기
    public List<Map<String, Object>> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    /** Keyword 기반 BookList 반환
     * @return List<Book> */
    public List<BookDto> findBookListByKeyword(String keyword){

        List<Map<String, Object>> bookList = bookRepository.findByKeyword(keyword);
        List<BookDto> returnBookList = new ArrayList<>();

        for(int i = 0; i < bookList.size(); i++){
            BookDto bookDto = new BookDto(
                    (Long) bookList.get(i).get("book_id"),
                    (String) bookList.get(i).get("book_title"),
                    (String) bookList.get(i).get("book_auth"),
                    (String) bookList.get(i).get("book_path"),
                    (String) bookList.get(i).get("book_summary"),
                    ((Timestamp) bookList.get(i).get("book_reg_date")).toLocalDateTime(),
                    (String) bookList.get(i).get("book_img_path"),
                    (Integer) bookList.get(i).get("book_price")
            );

            returnBookList.add(bookDto);
        }

        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

    /** Date 기반 BookList 반환
     * @return List<Book> */
    public List<BookDto> findBookListByDate(LocalDateTime sdate, LocalDateTime edate){

        List<Map<String, Object>> bookList = bookRepository.findByDate(sdate, edate);
        List<BookDto> returnBookList = new ArrayList<>();

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

        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

    /** Keyword & Date 기반 BookList 반환
     * @return List<Book> */
    public List<BookDto> findBookListByBoth(BookSearchRequestDto bookSearchRequestDto){

        List<Map<String, Object>> bookList = bookRepository.findByBoth(bookSearchRequestDto.getKeyword(), bookSearchRequestDto.getSdate(), bookSearchRequestDto.getEdate());
        List<BookDto> returnBookList = new ArrayList<>();

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

        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

}
