package com.skrookies.dahaezlge.service.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.entity.book.Book;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.restcontroller.view.dto.BookListDto;
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
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByKeyword(String keyword){

        List<Map<String, Object>> bookList = bookRepository.findByKeyword(keyword);
        List<BookListDto> returnBookList = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : bookList) {

            BookListDto bookListDto = new BookListDto();
            bookListDto.setBook_id((Long) stringObjectMap.get("book_id"));
            bookListDto.setTitle((String) stringObjectMap.get("book_title"));
            bookListDto.setPrice((Integer) stringObjectMap.get("book_price"));
            bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
            bookListDto.setWrite_date((LocalDateTime) stringObjectMap.get("book_reg_date"));
            bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

            returnBookList.add(bookListDto);
        }

        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

    /** Date 기반 BookList 반환
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByDate(LocalDateTime sdate, LocalDateTime edate){

        List<Map<String, Object>> bookList = bookRepository.findByDate(sdate, edate);
        List<BookListDto> returnBookList = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : bookList) {

            BookListDto bookListDto = new BookListDto();
            bookListDto.setBook_id((Long) stringObjectMap.get("book_id"));
            bookListDto.setTitle((String) stringObjectMap.get("book_title"));
            bookListDto.setPrice((Integer) stringObjectMap.get("book_price"));
            bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
            bookListDto.setWrite_date((LocalDateTime) stringObjectMap.get("book_reg_date"));
            bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

            returnBookList.add(bookListDto);
        }

        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

    /** Keyword & Date 기반 BookList 반환
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByBoth(BookSearchRequestDto bookSearchRequestDto){

        List<Map<String, Object>> bookList = bookRepository.findByBoth(bookSearchRequestDto.getKeyword(), bookSearchRequestDto.getSdate(), bookSearchRequestDto.getEdate());
        List<BookListDto> returnBookList = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : bookList) {

            BookListDto bookListDto = new BookListDto();
            bookListDto.setBook_id((Long) stringObjectMap.get("book_id"));
            bookListDto.setTitle((String) stringObjectMap.get("book_title"));
            bookListDto.setPrice((Integer) stringObjectMap.get("book_price"));
            bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
            bookListDto.setWrite_date((LocalDateTime) stringObjectMap.get("book_reg_date"));
            bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

            returnBookList.add(bookListDto);
        }

        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

}
