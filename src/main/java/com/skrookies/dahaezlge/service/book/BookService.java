package com.skrookies.dahaezlge.service.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.restcontroller.view.dto.BookListDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public List<Map<String, Object>> getBooks() {
        return bookRepository.getBooks();
    }
    public List<Map<String, Object>> getMyBooks(Long book_id) {
        return bookRepository.getMyBooks(book_id);
    }

    public List<Map<String, Object>> getBooksWithKeyword(String keyword) {
        return bookRepository.getBooksWithKeyword(keyword);
    }

    public List<Map<String, Object>> getBooksWithDate(LocalDateTime sdate, LocalDateTime edate) {
        return bookRepository.getBooksWithDate(sdate, edate);
    }

    public List<Map<String, Object>> getBooksWithBoth(String keyword, LocalDateTime sdate, LocalDateTime edate) {
        return bookRepository.getBooksWithBoth(keyword, sdate, edate);
    }

    public int getTotalBooks() {

        int size = bookRepository.getTotalBooks();

        System.out.println("bookListAll count: " + size);

        return size;
    }

    // ▼ 전체 도서 목록 (Map) 가져오기
    public List<Map<String, Object>> findAllBooks() {

        List<Map<String, Object>> books = bookRepository.findAllBooks();

        System.out.println("bookListAll count: " + books.size());

        return books;
    }

    /** Keyword 기반 BookList 반환
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByKeyword(String keyword){

        List<Map<String, Object>> bookList = bookRepository.findByKeyword(keyword);
        List<BookListDto> returnBookList = new ArrayList<>();

        if(bookList != null) {
            for (Map<String, Object> stringObjectMap : bookList) {

                BookListDto bookListDto = new BookListDto();
                bookListDto.setBook_id(((BigDecimal) stringObjectMap.get("book_id")).longValue());
                bookListDto.setTitle((String) stringObjectMap.get("book_title"));
                bookListDto.setPrice(((BigDecimal) stringObjectMap.get("book_price")).intValue());
                bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
                bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")).toLocalDateTime());
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                returnBookList.add(bookListDto);
            }
        }
        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

    /** Date 기반 BookList 반환
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByDate(LocalDateTime sdate, LocalDateTime edate){

        List<Map<String, Object>> bookList = bookRepository.findByDate(sdate, edate);
        List<BookListDto> returnBookList = new ArrayList<>();

        if(bookList != null) {
            for (Map<String, Object> stringObjectMap : bookList) {

                BookListDto bookListDto = new BookListDto();
                bookListDto.setBook_id(((BigDecimal) stringObjectMap.get("book_id")).longValue());
                bookListDto.setTitle((String) stringObjectMap.get("book_title"));
                bookListDto.setPrice(((BigDecimal) stringObjectMap.get("book_price")).intValue());
                bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
                bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")).toLocalDateTime());
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                returnBookList.add(bookListDto);
            }
        }

        System.out.println("bookListFindByDate count: " + returnBookList.size());

        return returnBookList;
    }

    /** Keyword & Date 기반 BookList 반환
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByBoth(String keyword, LocalDateTime sdate, LocalDateTime edate){

        List<Map<String, Object>> bookList = bookRepository.findByBoth(keyword, sdate, edate);
        List<BookListDto> returnBookList = new ArrayList<>();

        if(bookList != null) {
            for (Map<String, Object> stringObjectMap : bookList) {

                BookListDto bookListDto = new BookListDto();
                bookListDto.setBook_id(((BigDecimal) stringObjectMap.get("book_id")).longValue());
                bookListDto.setTitle((String) stringObjectMap.get("book_title"));
                bookListDto.setPrice(((BigDecimal) stringObjectMap.get("book_price")).intValue());
                bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
                bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")).toLocalDateTime());
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                returnBookList.add(bookListDto);
            }
        }
        System.out.println("bookListFindByBoth count: " + returnBookList.size());

        return returnBookList;
    }

}
