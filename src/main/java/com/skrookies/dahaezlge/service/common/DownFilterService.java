package com.skrookies.dahaezlge.service.common;

import org.springframework.stereotype.Service;

@Service
public class DownFilterService {
    private static final int MAX_TRAVERSAL_DEPTH = 100;
    private static final String[] KEYWORDS = {
            ".", "/", "\\", "%"
    };

    /** 다운로드 필터링 */
    public String filter(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        //  ../ 필터링
        for (String keyword : KEYWORDS) {
            /** 되는지 테스트 */
            input = input.replace(keyword, "112");
        }

        return input;
    }

    /** 다운로드 필터링 해제 */
    public String filter1(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        String[] limitedSpecialCharacters = {
                "../"
        };
        // 1. ../ 필터링
        for (String keyword : KEYWORDS) {
            input = input.replace(keyword, "");
        }

        return input;
    }

    /** 문자열에서 특정 패턴의 등장 횟수 계산 */
    private int countOccurrences(String str, String subStr) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }
        return count;
    }

}
