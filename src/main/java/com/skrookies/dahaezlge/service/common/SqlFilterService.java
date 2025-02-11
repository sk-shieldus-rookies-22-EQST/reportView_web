package com.skrookies.dahaezlge.service.common;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SqlFilterService {
    // 차단할 SQL 키워드 목록
    private static final String[] SQL_KEYWORDS = {
            "AND", "OR", "SELECT", "FROM", "WHERE", "UPDATE", "SET", "INSERT INTO",
            "DELETE", "DROP", "JOIN", "ALL_TABLES", "TABLE_NAME", "ALL_TAB_COLUMNS",
            "COLUMN_NAME", "UNION", "ORDER BY", "NULL",
            "UTL_INADDR.GET_HOST_NAME", "UTL_INADDR.GET_HOST_ADDRESS",
            "ORDSYS.ORD_DICOM.GETMAPPINGXPATH", "CTXSYS.DRITHSX.SN",
            "SUBSTR", "ASCII"
    };

    // 차단할 특수문자
    private static final String[][] SPECIAL_CHARACTERS = {
            {"\"", "&quot;"},
            {"'", "&apos;"},
            {"=", "&#61;"},
            {"(", "&#40;"},
            {")", "&#41;"},
            {"--", "&#45;&#45;"},
            {"/*", "&#47;&#42;"},
            {"*/", "&#42;&#47;"},
            {"%", "&#37;"},
            {"+", "&#43;"},
            {"-", "&#45;"},
            {"/", "&#47;"},
            {"&", "&amp;"}
    };

    /** sql injection 필터링 */
    public String filter(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        // SQL 키워드 차단
        for (String keyword : SQL_KEYWORDS) {
            input = input.replaceAll("(?i)\\b" + keyword + "\\b", " "); // 대소문자 구분 없이 제거
        }

        // 특수문자 이스케이프 처리
        for (String[] replacement : SPECIAL_CHARACTERS) {
            input = input.replace(replacement[0], replacement[1]);
        }

        return input;
    }

    /**전화번호에 사용하기 위해 - 필터링 해제*/
    public String filter1(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        // SQL 키워드 차단
        for (String keyword : SQL_KEYWORDS) {
            input = input.replaceAll("(?i)\\b" + keyword + "\\b", " "); // 대소문자 구분 없이 제거
        }

        String[][] limitedSpecialCharacters = {
                {"\"", "&quot;"},
                {"'", "&apos;"},
                {"=", "&#61;"},
                {"(", "&#40;"},
                {")", "&#41;"},
                {"--", "&#45;&#45;"},
                {"/*", "&#47;&#42;"},
                {"*/", "&#42;&#47;"},
                {"%", "&#37;"},
                {"+", "&#43;"},
                {"/", "&#47;"},
                {"&", "&amp;"}
        };

        // 특수문자 이스케이프 처리
        for (String[] replacement : limitedSpecialCharacters) {
            input = input.replace(replacement[0], replacement[1]);
        }

        return input;
    }

    /** sql 필터링 (xss 허용) */
    public String filter2(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        String[][] limitedSpecialCharacters = {
                {"--", "&#45;&#45;"},
                {"/*", "&#47;&#42;"},
                {"*/", "&#42;&#47;"},
                {"+", "&#43;"},
                {"&", "&amp;"}
        };

        // 특수문자 이스케이프 처리
        for (String[] replacement : limitedSpecialCharacters) {
            input = input.replace(replacement[0], replacement[1]);
        }

        // SQL 키워드 차단
        for (String keyword : SQL_KEYWORDS) {
            input = input.replaceAll("(?i)\\b" + keyword + "\\b", " "); // 대소문자 구분 없이 제거
        }

        return input;
    }

    /** sql 공격 허용 (xss 필터링) */
    public String filter3(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        String[][] limitedSpecialCharacters = {
                {"/*", "&#47;&#42;"},
                {"*/", "&#42;&#47;"},
                {"/", "&#47;"},
                {"&", "&amp;"}
        };

        // 특수문자 이스케이프 처리
        for (String[] replacement : limitedSpecialCharacters) {
            input = input.replace(replacement[0], replacement[1]);
        }

        // 지정된 토큰 치환 (대소문자 무시)
        List<String> modifiedTokens = new ArrayList<>(Arrays.asList(SQL_KEYWORDS));
        modifiedTokens.remove("AND");
        modifiedTokens.remove("OR");
        modifiedTokens.remove("SELECT");
        modifiedTokens.remove("FROM");
        modifiedTokens.remove("WHERE");
        modifiedTokens.remove("ALL_TABLES");
        modifiedTokens.remove("TABLE_NAME");
        modifiedTokens.remove("ALL_TAB_COLUMNS");
        modifiedTokens.remove("COLUMN_NAME");
        modifiedTokens.remove("SUBSTR");
        modifiedTokens.remove("ASCII");

        // SQL 키워드 차단
        for (String keyword : modifiedTokens) {
            input = input.replaceAll("(?i)\\b" + keyword + "\\b", " "); // 대소문자 구분 없이 제거
        }

        return input;
    }

}
