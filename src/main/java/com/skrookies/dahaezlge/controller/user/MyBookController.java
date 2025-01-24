package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

public class MyBookController {
    private final UserService userService;

    @GetMapping("/myBook")
    public String myBook_form(Model model, HttpSession session){
        log.info("page_move: myBook.jsp");
        String login_ok = (String) session.getAttribute("user_id");
        if (login_ok != null) {

            return "myBook";
        } else {
            return "redirect:/loginForm";
        }


    }

}
