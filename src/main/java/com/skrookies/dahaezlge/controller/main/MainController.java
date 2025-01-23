package com.skrookies.dahaezlge.controller.main;

import com.skrookies.dahaezlge.controller.user.Dto.SessionDto;
import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    public String login_id(HttpSession session) {
        return (String) session.getAttribute("user_id");
    }

    @GetMapping("/index")
    public String main_form(){
        log.info("page_move: index.jsp");
        return "index";
    }

    @GetMapping("/banner")
    public String banner_form(){

        log.info("page_move: banner.jsp");
        return "banner";
    }

    @GetMapping("/eBookDetail")
    public String eBookDetail_form(){

        log.info("page_move: eBookDetail.jsp");
        return "eBookDetail";
    }

}
