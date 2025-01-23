package com.skrookies.dahaezlge.restcontroller.signup;

import com.skrookies.dahaezlge.restcontroller.auth.dto.LoginDto;
import com.skrookies.dahaezlge.restcontroller.signup.dto.SignDto;
import com.skrookies.dahaezlge.restcontroller.util.StatusDto;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<StatusDto> androidLogin(@RequestBody @Valid SignDto signDto) {

//        StatusDto statusDto = new StatusDto(userService.signup(signDto.getUser_id(), signDto.getPw(), signDto.getMail(), signDto.getPhone()));
        StatusDto statusDto = new StatusDto(true);
        return ResponseEntity.ok()
                .body(statusDto);

    }
}
