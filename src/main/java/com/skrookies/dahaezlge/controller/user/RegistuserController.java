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

    /** 회원가입 페이지 */
    @GetMapping("/registerForm")
    public String registerForm_form(){

        log.info("registerForm");
        return "registerForm";
    }

    /** 회원가입 프로세스 */
    @PostMapping("/registerProc")
    public String registerProc_form(Model model, @ModelAttribute UserDto userDto, HttpSession session){
        String user_id = userDto.getUser_id();
        String user_pw = userDto.getUser_pw();
        String user_phone = userDto.getUser_phone();
        String user_email = userDto.getUser_email();
        String user_agree = userDto.getUser_agree();
        log.info("agree: " + user_agree);
        if(userService.checkId(user_id)) {
            log.info("이미 있는 아이디");
            session.setAttribute("status", "1");
            return "redirect:/registerForm";
        } else {
            if (user_agree != null) {
                if (userService.registerUser(user_id, user_pw, user_phone, user_email)) {
                    // 회원가입 직후 자동 로그인
                    int point = userService.userPoint(user_id);
                    session.setAttribute("user_id", user_id);
                    session.setAttribute("point", point);
                    session.removeAttribute("status");
                    return "redirect:/index";
                } else {
                    session.setAttribute("status","2");
                    log.info("모든 input 채우기");
                    return "redirect:/registerForm";
                }

            } else {
                session.setAttribute("status","3");
                log.info("활용 동의");
                return "redirect:/registerForm";
            }

        }


    }

}
