package com.skrookies.dahaezlge.service.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;
import com.skrookies.dahaezlge.repository.book.BookRepository;
import com.skrookies.dahaezlge.restcontroller.view.dto.BookListDto;
import javax.transaction.Transactional;
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

    public List<Map<String, Object>> getBooks(String sort, String direction) {
        return bookRepository.getBooks(sort, direction);
    }
    public List<Map<String, Object>> getMyBooks(Long book_id) {
        return bookRepository.getMyBooks(book_id);
    }

    public List<Map<String, Object>> getBooksWithKeyword(String keyword, String sort, String direction) {
        return bookRepository.getBooksWithKeyword(keyword, sort, direction);
    }

    public List<Map<String, Object>> getBooksWithDate(String sdate, String edate, String sort, String direction) {
        return bookRepository.getBooksWithDate(sdate, edate, sort, direction);
    }

    public List<Map<String, Object>> getBooksWithBoth(String keyword, String sdate, String edate, String sort, String direction) {
        return bookRepository.getBooksWithBoth(keyword, sdate, edate, sort, direction);
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
                bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")));
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                returnBookList.add(bookListDto);
            }
        }
        System.out.println("bookListFindByKeyword count: " + returnBookList.size());

        return returnBookList;
    }

    /** Date 기반 BookList 반환
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByDate(String sdate, String edate){

        List<Map<String, Object>> bookList = bookRepository.findByDate(sdate, edate);
        List<BookListDto> returnBookList = new ArrayList<>();

        if(bookList != null) {
            for (Map<String, Object> stringObjectMap : bookList) {

                BookListDto bookListDto = new BookListDto();
                bookListDto.setBook_id(((BigDecimal) stringObjectMap.get("book_id")).longValue());
                bookListDto.setTitle((String) stringObjectMap.get("book_title"));
                bookListDto.setPrice(((BigDecimal) stringObjectMap.get("book_price")).intValue());
                bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
                bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")));
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                returnBookList.add(bookListDto);
            }
        }

        System.out.println("bookListFindByDate count: " + returnBookList.size());

        return returnBookList;
    }

    /** Keyword & Date 기반 BookList 반환
     * @return List<BookListDto> */
    public List<BookListDto> findBookListByBoth(String keyword, String sdate, String edate){

        List<Map<String, Object>> bookList = bookRepository.findByBoth(keyword, sdate, edate);
        List<BookListDto> returnBookList = new ArrayList<>();

        if(bookList != null) {
            for (Map<String, Object> stringObjectMap : bookList) {

                BookListDto bookListDto = new BookListDto();
                bookListDto.setBook_id(((BigDecimal) stringObjectMap.get("book_id")).longValue());
                bookListDto.setTitle((String) stringObjectMap.get("book_title"));
                bookListDto.setPrice(((BigDecimal) stringObjectMap.get("book_price")).intValue());
                bookListDto.setWriter((String) stringObjectMap.get("book_auth"));
                bookListDto.setWrite_date(((Timestamp) stringObjectMap.get("book_reg_date")));
                bookListDto.setBook_img_path((String) stringObjectMap.get("book_img_path"));

                returnBookList.add(bookListDto);
            }
        }
        System.out.println("bookListFindByBoth count: " + returnBookList.size());

        return returnBookList;
    }

    public List<Map<String, Object>> getBooksWithFilters(String keyword, String sdate, String edate, String sort, String direction) {
        if (keyword.isEmpty() && sdate.isEmpty() && edate.isEmpty()) {
            return bookRepository.getBooks(sort, direction);
        } else if (sdate.isEmpty() || edate.isEmpty()) {
            return bookRepository.getBooksWithKeyword(keyword, sort, direction);
        } else if (keyword.isEmpty()) {
            return bookRepository.getBooksWithDate(sdate, edate, sort, direction);
        } else {
            return bookRepository.getBooksWithBoth(keyword, sdate, edate, sort, direction);
        }
    }
}
