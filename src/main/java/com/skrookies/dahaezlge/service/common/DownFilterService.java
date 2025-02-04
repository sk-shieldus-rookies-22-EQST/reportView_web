package com.skrookies.dahaezlge.service.common;

import org.springframework.stereotype.Service;

@Service
public class DownFilterService {
    private static final String[] KEYWORDS = {
            "../"
    };

    /** 다운로드 필터링 */
    public String filter(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        for (String keyword : KEYWORDS) {
            input = input.replace(keyword, "");
        }
        return input;
    }

}
