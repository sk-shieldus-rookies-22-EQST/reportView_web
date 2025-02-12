package com.skrookies.dahaezlge.restcontroller.sign;

import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.restcontroller.sign.dto.SignOutDto;
import com.skrookies.dahaezlge.restcontroller.sign.dto.SignupDto;
import com.skrookies.dahaezlge.restcontroller.util.Bcrypt;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = "application/json")
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;
    Bcrypt bcrypt = new Bcrypt();

    /** 회원가입 api */
    @PostMapping("/signup")
    public ResponseEntity<StatusDto> androidSignup(@RequestBody @Valid SignupDto signupDto) {


        StatusDto statusDto = new StatusDto(userService.registerUser(signupDto.getUser_id(), bcrypt.hashPassword(signupDto.getUser_pw()), signupDto.getUser_phone(), signupDto.getUser_email()));

        return ResponseEntity.ok()
                .body(statusDto);

    }

    /** 회원탈퇴 api */
    @PostMapping("/signout")
    public ResponseEntity<StatusDto> androidSignOut(@RequestBody @Valid SignOutDto signOutDto) {

        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(false);

        String user_check = userService.login(signOutDto.getUser_id(), bcrypt.hashPassword(signOutDto.getUser_pw()));
        if (user_check.equals("true")) {
            Boolean result = userService.deleteUser(signOutDto.getUser_id());
            statusDto.setStatus(result);
        }

        return ResponseEntity.ok()
                .body(statusDto);
    }
}
