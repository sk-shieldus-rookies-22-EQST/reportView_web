package com.skrookies.dahaezlge.controller.common;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;

@Controller
@Slf4j
public class DownloadController {
    @GetMapping("/download/drm")
    public String downloadFile(HttpServletResponse response, HttpSession session) {
        try {
            // 고정 파일 경로 설정 (BookiesDRM_Setup.exe 파일만 다운로드)
            String filePathStr = Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "drm").toString();
            File file = new File(filePathStr);

            if (!file.exists()) {
                session.setAttribute("errorMessage", "파일 다운로드 실패.");
                return "redirect:/myBook";
            }

            // 고정 파일명
            String fileName = "BookiesDRM_Setup.exe";

            // 다운로드 응답 헤더 설정
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20") + "\"");
            response.setContentLengthLong(file.length());

            // 파일 스트림 전송
            try (InputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
            // 다운로드가 완료되었으므로 추가 뷰 반환은 필요 없음
            return null;
        } catch (Exception e) {
            log.error("파일 다운로드 실패: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }
}

