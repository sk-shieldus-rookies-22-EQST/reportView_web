package com.skrookies.dahaezlge.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.RedirectStrategy;
import java.io.IOException;

public class CustomRedirectStrategy implements RedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        // 리디렉트 횟수 제한 없이 무조건 수행
        response.sendRedirect(url);
    }
}

