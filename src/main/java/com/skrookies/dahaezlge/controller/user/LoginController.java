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
        log.info("Login Id: " + loginDto.getUser_id());
        log.info("Login Password: " + loginDto.getUser_pw());

        if(userService.login(loginDto.getUser_id(), loginDto.getUser_pw())){

            SessionDto sessionDto = new SessionDto(loginDto.getUser_id());

            session.setAttribute("id", sessionDto);

            /** point select 메소드 */
            int point = userService.userPoint("1");
            log.info("point = " + point);

            /** Point model로 전달 */
            model.addAttribute("point", point);

            return "redirect:/index";
        }
        else{
            log.info("test");

            model.addAttribute("warn","1");
            return "loginForm";
        }
    }


}
