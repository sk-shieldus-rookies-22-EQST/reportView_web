package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.userPoint.UserPoint;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistuserController {

    private final UserService userService;

    @GetMapping("/registerForm")
    public String registerForm_form(){

        log.info("registerForm");
        return "registerForm";
    }

    @PostMapping("/registerProc")
    public String registerProc_form(Model model, @ModelAttribute UserDto userDto, HttpSession session){
        String user_id = userDto.getUser_id();
        String user_pw = userDto.getUser_pw();
        String user_phone = userDto.getUser_phone();
        String user_email = userDto.getUser_email();
        if (userService.registerUser(user_id, user_pw,user_phone,user_email)){
            // 회원가입 직후 자동 로그인
            int point = userService.userPoint(user_id);
            session.setAttribute("user_id", user_id);
            session.setAttribute("point", point);

            return "redirect:/index";
        } else {
            return "redirect:/registerForm";
        }

    }

}
