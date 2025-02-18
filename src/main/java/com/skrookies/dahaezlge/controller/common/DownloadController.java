package com.skrookies.dahaezlge.controller.common;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            // 파일 경로 필터링
            String filePath = "src/main/webapp/drm/BookiesDRM_Setup.exe";
            // 파일이 저장된 절대 경로 (예: /uploads/dog_20250203_205142.jpg)
            File file = new File(filePath);

            if (!file.exists()) {
                log.info("drm 다운 실패");
                session.setAttribute("errorMessage", "파일 다운 실패.");
                // 파일이 존재하지 않으면 리다이렉트
                return "redirect:/qnaList";
            }

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

