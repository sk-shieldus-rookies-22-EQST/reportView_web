package com.skrookies.dahaezlge.service.common;

import org.springframework.stereotype.Service;

@Service
public class DownFilterService {
    private static final int MAX_TRAVERSAL_DEPTH = 3;
    private static final String[] KEYWORDS = {
            "../"
    };

    /** 다운로드 필터링 */
    public String filter(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        // 1. ../ 필터링
        for (String keyword : KEYWORDS) {
            input = input.replace(keyword, "");
        }
        // 2. 필터링 후 남은 ../ 개수 세기
        int traversalCount = countOccurrences(input, "../");

        // 3. 최대 제한보다 많으면 자동으로 ../../../../로 조정
        if (traversalCount > MAX_TRAVERSAL_DEPTH) {
            StringBuilder safePath = new StringBuilder();
            for (int i = 0; i < MAX_TRAVERSAL_DEPTH; i++) {
                safePath.append("../");
            }
            return safePath.toString(); // ../../../../로 고정
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
