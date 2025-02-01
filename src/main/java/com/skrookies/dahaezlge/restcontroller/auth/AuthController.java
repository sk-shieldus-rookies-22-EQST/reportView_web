package com.skrookies.dahaezlge.restcontroller.auth;

import com.skrookies.dahaezlge.restcontroller.auth.dto.*;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<StatusDto> androidLogin(@RequestBody @Valid LoginDto loginDto) {

        StatusDto statusDto = new StatusDto(userService.login(loginDto.getUser_id(), loginDto.getUser_pw()));
        return ResponseEntity.ok()
                .body(statusDto);

    }

    @PostMapping("/login")
    public ResponseEntity<UserLevelDto> androidUserLevel(@RequestBody @Valid UserIdDto userIdDto) {

        int userLevel = userService.getUserLevel(userIdDto.getUser_id());

        UserLevelDto userLevelDto = new UserLevelDto(userLevel);

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

        StatusDto statusDto = new StatusDto(userService.updateUserpw(modifyPwDto.getUser_id(), modifyPwDto.getNew_user_pw()));

        return ResponseEntity.ok()
                .body(statusDto);

    }

}
