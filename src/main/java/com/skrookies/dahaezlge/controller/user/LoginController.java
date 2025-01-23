package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.controller.user.Dto.LoginDto;
import com.skrookies.dahaezlge.controller.user.Dto.SessionDto;
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
public class LoginController {

    private final UserService userService;

    @GetMapping("/loginForm")
    public String loginForm_form(){

        log.info("loginForm");
        return "loginForm";
    }

    @PostMapping("/loginProc")
    public String loginProc_form(Model model, @ModelAttribute LoginDto loginDto, HttpSession session){
        String user_id = loginDto.getUser_id();
        log.info("Login Id: " + user_id);
        log.info("Login Password: " + loginDto.getUser_pw());

        if(userService.login(loginDto.getUser_id(), loginDto.getUser_pw())){
            log.info("user_id = " + user_id);

            session.setAttribute("user_id", user_id);
            log.info("sessionId = " + session.getAttribute("user_id"));

            /** point select 메소드 */
            int point = userService.userPoint(user_id);
            session.setAttribute("point", point);
            log.info("point = " + point);

            log.info("user_id = " + user_id);
            return "redirect:/index";
        }
        else{
            log.info("없는 아이디");

            model.addAttribute("warn","1");
            return "loginForm";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.removeAttribute("user_id");
        return "redirect:/index";
    }

}
