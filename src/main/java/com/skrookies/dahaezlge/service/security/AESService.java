package com.skrookies.dahaezlge.service.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Service
public class AESService {

    private final SecretKey secretKey;
    private final IvParameterSpec iv;

    public AESService(@Value("${aes.key}") String key, @Value("${aes.iv}") String iv) {
        // 1. 키와 IV에 대해 패딩 처리
        byte[] keyBytes = padTo16Bytes(key.getBytes(StandardCharsets.UTF_8));  // 키 패딩
        byte[] ivBytes = padTo16Bytes(iv.getBytes(StandardCharsets.UTF_8));   // IV 패딩

        // 2. 패딩된 키와 IV를 Base64로 인코딩
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);  // Base64 인코딩
        String base64Iv = Base64.getEncoder().encodeToString(ivBytes);    // Base64 인코딩

        // 3. Base64로 인코딩된 값을 사용하여 SecretKeySpec과 IvParameterSpec 생성
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
        this.iv = new IvParameterSpec(ivBytes);

        // 디버깅용으로 출력
        System.out.println("Base64 Encoded Key: " + base64Key);
        System.out.println("Base64 Encoded IV: " + base64Iv);
    }

    // 16바이트로 패딩하는 메소드
    private byte[] padTo16Bytes(byte[] inputBytes) {
        if (inputBytes.length < 16) {
            byte[] padded = new byte[16];
            System.arraycopy(inputBytes, 0, padded, 0, inputBytes.length);  // 원본 데이터를 16바이트 배열로 복사
            return padded;  // 패딩된 배열 반환
        } else if (inputBytes.length > 16) {
            return Arrays.copyOf(inputBytes, 16);  // 16바이트보다 길면 앞의 16바이트만 사용
        }
        return inputBytes;  // 이미 16바이트라면 그대로 반환
    }

    // AES 암호화 (CBC 모드 + PKCS5Padding)
    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // 평문을 바이트 배열로 변환
        byte[] plainBytes = plainText.getBytes("UTF-8");

        // 암호화 수행
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] encryptedBytes = cipher.doFinal(plainBytes);

        // Base64로 암호화된 결과 반환
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // AES 복호화 (CBC 모드 + PKCS5Padding)
    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Base64로 인코딩된 문자열을 바이트 배열로 디코딩
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

        // 복호화 수행
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // 복호화된 데이터를 문자열로 변환
        return new String(decryptedBytes, "UTF-8");
    }
}
