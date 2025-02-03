package com.skrookies.dahaezlge.security;

import com.skrookies.dahaezlge.service.security.AESService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/security")
public class SecurityController {

    private final AESService aesService;

    @Autowired
    public SecurityController(AESService aesService) {
        this.aesService = aesService;
    }

    @GetMapping("/getKey")
    public Map<String, String> getKey(@Value("${aes.key}") String key, @Value("${aes.iv}") String iv) {
        Map<String, String> keys = new HashMap<>();

        // 16바이트(128비트)로 변환 & \0 패딩 추가
        byte[] ivBytes = new byte[16];
        byte[] keyBytes = new byte[16];

        Arrays.fill(ivBytes, (byte) 0);  // \0으로 초기화
        Arrays.fill(keyBytes, (byte) 0);  // \0으로 초기화

        // 키와 IV를 배열에 복사
        System.arraycopy(iv.getBytes(StandardCharsets.UTF_8), 0, ivBytes, 0, iv.length());
        System.arraycopy(key.getBytes(StandardCharsets.UTF_8), 0, keyBytes, 0, key.length());

        // Base64로 인코딩
        String base64Iv = Base64.getEncoder().encodeToString(ivBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        // JSON 응답에 Base64로 인코딩된 키와 IV 추가
        keys.put("aesKey", base64Key);
        keys.put("aesIv", base64Iv);

        return keys;
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String encryptedData) throws Exception {
        return aesService.decrypt(encryptedData);
    }
}

