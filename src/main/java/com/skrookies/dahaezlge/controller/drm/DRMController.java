package com.skrookies.dahaezlge.controller.drm;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@Controller
public class DRMController {
    private final RestTemplate restTemplate;

    public DRMController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private String padTo16Bytes(String input) {
        if (input == null) {
            return "0000000000000000"; // 기본값 설정
        }

        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        byte[] paddedBytes = new byte[16];

        // 기존 값 복사 (초과 부분 잘림, 부족하면 0으로 채움)
        System.arraycopy(inputBytes, 0, paddedBytes, 0, Math.min(inputBytes.length, 16));

        return new String(paddedBytes, StandardCharsets.UTF_8);
    }

    private final String FILE_GET_URL = "http://3.35.84.46:8080/generate-presigned-url";

    @PostMapping("/getPreURL")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getPresignedUrl(@RequestParam("book_id") String bookId, HttpSession session) {
        try {
            log.info("getPresignedUrl Process");
            // 세션에서 user_id 가져오기
            String userId = (String) session.getAttribute("user_id");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "로그인이 필요합니다."));
            }

            try {
                // 요청 헤더 설정
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // 요청 본문 생성
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("user_id", userId);
                requestBody.put("book_id", bookId);

                // HTTP 요청 객체 생성
                HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
                log.info("entity: " + entity);

                // distributor API 호출
                ResponseEntity<Map<String, String>> fileResponse = restTemplate.exchange(
                        FILE_GET_URL, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, String>>() {});
                log.info("fileResponse: " + fileResponse);

                // Presigned URL 추출
                String presignedUrl = fileResponse.getBody().get("presigned_url");
                log.info("presignedUrl: " + presignedUrl);

                String drmUrl = "BookiesDRM://run?presigned_url=" + presignedUrl;

                log.info("drmUrl: " + drmUrl);

                Map<String, String> response = new HashMap<>();
                response.put("drm_url", drmUrl);

                return ResponseEntity.ok(response);

            } catch (Exception e) {
                log.error("Error occurred during processing: ", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("error", "요청 정보가 올바르지 않습니다."));
            }

        } catch (Exception e) {
            log.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
