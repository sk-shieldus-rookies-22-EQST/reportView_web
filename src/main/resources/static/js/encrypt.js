async function encryptAES(key, iv, data) {
    // Base64로 디코딩하고 16바이트로 패딩하는 함수
    function padTo16Bytes(inputBytes) {
        let padded = new Uint8Array(16);
        padded.set(inputBytes.slice(0, 16));  // 최대 16바이트까지 복사
        return padded;
    }

    // Base64로 디코딩된 키와 IV를 16바이트로 패딩
    let keyBytes = Uint8Array.from(atob(key), c => c.charCodeAt(0));
    let ivBytes = Uint8Array.from(atob(iv), c => c.charCodeAt(0));

    // 16바이트로 패딩
    keyBytes = padTo16Bytes(keyBytes);  // 키 패딩
    ivBytes = padTo16Bytes(ivBytes);    // IV 패딩

    // AES 키 가져오기
    const cryptoKey = await crypto.subtle.importKey(
        "raw",
        keyBytes,
        { name: "AES-CBC" },
        false,
        ["encrypt", "decrypt"]
    );

    // UTF-8로 인코딩 후 암호화 수행
    let encodedData = new TextEncoder().encode(data);

    // 암호화 실행 (PKCS7 padding 자동 처리)
    const encryptedData = await crypto.subtle.encrypt(
        { name: "AES-CBC", iv: ivBytes },
        cryptoKey,
        encodedData
    );

    // 암호화된 데이터를 Base64로 인코딩하여 반환
    return btoa(String.fromCharCode(...new Uint8Array(encryptedData)));
}
