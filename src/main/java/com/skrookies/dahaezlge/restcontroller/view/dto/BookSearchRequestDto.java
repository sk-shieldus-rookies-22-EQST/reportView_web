package com.skrookies.dahaezlge.restcontroller.view.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookSearchRequestDto {

    private String keyword;
    private LocalDateTime sdate;
    private LocalDateTime edate;
    private String theme;
}
