package com.skrookies.dahaezlge.controller.user;

import ch.qos.logback.core.model.Model;
import com.skrookies.dahaezlge.controller.user.Dto.LoginDto;
import com.skrookies.dahaezlge.controller.user.Dto.SessionDto;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login-form")
    public String login_form(){

        log.info("login-form");
        return "loginFormBoot";
    }

    @PostMapping("/loginProcess")
    public String loginProcess(Model model, LoginDto loginDto, HttpSession session){

        log.info("Login Id: " + loginDto.getLoginId());
        log.info("Login Password: " + loginDto.getPassword());

        if(userService.login(loginDto.getLoginId(), loginDto.getPassword())){

            SessionDto sessionDto = new SessionDto(loginDto.getLoginId());

            session.setAttribute("id", sessionDto);

            return "redirect:/index";
        }
        else{
            return "loginFormBoot";
        }
    }


}
