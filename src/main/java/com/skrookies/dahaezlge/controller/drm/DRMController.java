package com.skrookies.dahaezlge.controller.drm;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class DRMController {
    private final RestTemplate restTemplate;

    public DRMController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private final String KEY_GET_URL = "http://3.35.84.46:8080/get-key";
    private final String FILE_GET_URL = "http://3.35.84.46:8080/generate-presigned-url";

    @PostMapping("/getPreURL")
    public ResponseEntity<Map<String, Object>> getPresignedUrl(@RequestParam("book_id") String bookId, HttpSession session) {
        try {
            // 세션에서 user_id 가져오기
            String userId = (String) session.getAttribute("user_id");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not authenticated"));
            }

            // Presigned URL 요청
            Map<String, String> requestData = Map.of("user_id", userId, "book_id", bookId);
            ResponseEntity<Map> fileResponse = restTemplate.postForEntity(FILE_GET_URL, requestData, Map.class);
            String presignedUrl = (String) fileResponse.getBody().get("presigned_url");

            // KMS에서 AES 키 및 IV 가져오기
            ResponseEntity<Map> kmsResponse = restTemplate.getForEntity(KEY_GET_URL, Map.class);
            String aesKey = (String) kmsResponse.getBody().get("aes_key");
            String aesIv = (String) kmsResponse.getBody().get("aes_iv");

            // Base64 인코딩
            String base64AesKey = Base64.getEncoder().encodeToString(aesKey.getBytes());
            String base64AesIv = Base64.getEncoder().encodeToString(aesIv.getBytes());

            // JSON 형태로 반환
            Map<String, Object> response = new HashMap<>();
            response.put("presigned_url", presignedUrl);
            response.put("aes_key", base64AesKey);
            response.put("aes_iv", base64AesIv);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
