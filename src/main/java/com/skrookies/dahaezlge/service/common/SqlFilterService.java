package com.skrookies.dahaezlge.service.common;

import org.springframework.stereotype.Service;

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
            {"/", "&#47;"}
    };

    public String filter(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        // SQL 키워드 차단
        for (String keyword : SQL_KEYWORDS) {
            input = input.replaceAll("(?i)\\b" + keyword + "\\b", ""); // 대소문자 구분 없이 제거
        }

        // 특수문자 이스케이프 처리
        for (String[] replacement : SPECIAL_CHARACTERS) {
            input = input.replace(replacement[0], replacement[1]);
        }

        return input;
    }

}
