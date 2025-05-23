package com.skrookies.dahaezlge.restcontroller.auth;

import com.skrookies.dahaezlge.restcontroller.auth.dto.*;
import com.skrookies.dahaezlge.restcontroller.util.Bcrypt;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
import com.skrookies.dahaezlge.service.security.AESService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AESService aesService;


    @PostMapping("/login")
    public ResponseEntity<StatusDto> androidLogin(@RequestBody @Valid E2EDto e2eDto) {

        StatusDto statusDto = new StatusDto();

        log.info("Android login 시도");

        try {
            String e2e_data = e2eDto.getE2e_data();
            log.info("Android login e2e data:", e2e_data);
            String decrypted_data = aesService.decrypt(e2e_data);

            String[] passwordParts = decrypted_data.split("&&&&");

            // 예시: 분리된 값들로 추가 작업
            log.info("Android ID: " + passwordParts[0]);
            log.info("Android PW: " + passwordParts[1]);
            log.info("Android UUID: " + passwordParts[2]);

            String user_id = passwordParts[0];
            String user_pw = passwordParts[1];
            String uuid = passwordParts[2];

            String result = userService.login(user_id, user_pw);
            String token = userService.autoLoginTokenGen(user_id, uuid);

            log.info("Android token: " + token);

            statusDto.setStatus(Boolean.valueOf(result));
            statusDto.setToken(token);

        } catch (Exception e) {
            e.printStackTrace();
            statusDto.setStatus(false);
        }

        log.info("Android login 결과: {}", statusDto.getStatus());

        return ResponseEntity.ok()
                .body(statusDto);

    }


    /** 자동 로그인 */
    @PostMapping("/autologin")
    public ResponseEntity<StatusDto> autoLogin(@RequestBody @Valid AutoLoginDto autoLoginDto){

        log.info("Requset data user_id: {}", autoLoginDto.getUser_id());
        log.info("Request data token: {}", autoLoginDto.getToken());
        log.info("Request data uuid: {}", autoLoginDto.getUuid());

        Boolean result = userService.auto_login(autoLoginDto.getUser_id(), autoLoginDto.getToken(), autoLoginDto.getUuid());

        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(result);

        return ResponseEntity.ok()
                .body(statusDto);
    }


    @PostMapping("/user/level")
    public ResponseEntity<UserLevelDto> androidUserLevel(@RequestBody @Valid UserIdDto userIdDto) {

        int userLevel = userService.getUserLevel(userIdDto.getUser_id());

        UserLevelDto userLevelDto = new UserLevelDto(userLevel);

        log.info(userIdDto.getUser_id() + "의 user level = {}", userLevelDto);

        return ResponseEntity.ok()
                .body(userLevelDto);

    }

    @PostMapping("/find/id")
    public ResponseEntity<UserIdDto> androidFindID(@RequestBody @Valid FindIdDto findIdDto){

        UserIdDto userIdDto = new UserIdDto(userService.findUserId(findIdDto.getUser_phone(), findIdDto.getUser_email()));

        return ResponseEntity.ok()
                .body(userIdDto);

    }

    @PostMapping("/modify/pw")
    public ResponseEntity<StatusDto> androidModifyPW(@RequestBody @Valid ModifyPwDto modifyPwDto){

        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(userService.updateUserpw(modifyPwDto.getUser_id(), modifyPwDto.getNew_user_pw()));

        return ResponseEntity.ok()
                .body(statusDto);

    }

}
