package com.skrookies.dahaezlge.restcontroller.auth;

import com.skrookies.dahaezlge.restcontroller.auth.dto.FindIdDto;
import com.skrookies.dahaezlge.restcontroller.auth.dto.FindPwDto;
import com.skrookies.dahaezlge.restcontroller.auth.dto.LoginDto;
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
    public ResponseEntity<Boolean> androidLogin(@RequestBody @Valid LoginDto loginDto) {

        return ResponseEntity.ok()
                .body(userService.login(loginDto.getUser_id(), loginDto.getUser_pw()));

    }

    @PostMapping("/find/id")
    public ResponseEntity<String> androidFindID(@RequestBody @Valid FindIdDto findIdDto){

        return ResponseEntity.ok()
                .body(userService.findUserId(findIdDto.getUser_phone(), findIdDto.getUser_email()));

    }

    @PostMapping("/find/pw")
    public ResponseEntity<String> androidFindPW(@RequestBody @Valid FindPwDto findPwDto){

//        return ResponseEntity.ok()
//                .body(userService.findUserPw(findPwDto.getUser_id(), findPwDto.getUser_phone()), findPwDto.getUser_email());

        return ResponseEntity.ok().body("null");
    }

}
