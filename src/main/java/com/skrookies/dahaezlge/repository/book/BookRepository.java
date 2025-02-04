package com.skrookies.dahaezlge.repository.book;

import com.skrookies.dahaezlge.controller.book.Dto.BookDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookRepository {

    BookDto getBookInfo(Long book_id);

    List<BookDto> getCartBookInfo(List<Long> bookIdList);

    /** 페이지별로 책 리스트 가져오기 */
    List<Map<String, Object>> getBooks();

    /** 검색어 별 책 리스트 가져오기 */
    List<Map<String, Object>> getBooksWithKeyword(String keyword);

    /** 게시 날짜 별 책 리스트 가져오기 */
    List<Map<String, Object>> getBooksWithDate(Timestamp sdate, Timestamp edate);

    /** 검색어 및 게시 날짜 별 책 리스트 가져오기 */
    List<Map<String, Object>> getBooksWithBoth(String keyword, Timestamp sdate, Timestamp edate);


    // 전체 책 개수
    int getTotalBooks();

    // 전체 책 정보를 가져오는 메서드 (BookInfo 대신 Map 사용)
    List<Map<String, Object>> findAllBooks();

    /** keyword 기반 book list 출력
     * @return List<Map<String, Object>> */
    List<Map<String, Object>> findByKeyword(String keyword);


    /** Date 기반 book list 출력
     * @return List<Map<String, Object>> */
    List<Map<String, Object>> findByDate(Timestamp sdate, Timestamp edate);

    /** keyword & Date 기반 book list 출력
     * @return List<Map<String, Object>> */
    List<Map<String, Object>> findByBoth(String keyword, Timestamp sdate, Timestamp edate);

    List<Map<String, Object>> getMyBooks(Long bookId);
}
