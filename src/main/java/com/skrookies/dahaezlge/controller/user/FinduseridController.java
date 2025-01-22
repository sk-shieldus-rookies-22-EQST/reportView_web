package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.controller.user.Dto.FinduseridDto;
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
public class FinduseridController {

    private final UserService userService;

    @PostMapping("/findUseridpw")
    public String findUseridpw_form(Model model, @ModelAttribute FinduseridDto finduseridDto, HttpSession session){

        log.info("page_move: findUseridpw.jsp");
        String user_id = userService.findUserId(finduseridDto.getUser_phone(), finduseridDto.getUser_email());
        if(user_id == "no_users"){
            return "redirect:/findUseridpw?warn='1'";
        } else if (user_id == ""){
            return "";
        } else {
            return "redirect:/findUseridpw?foundId="+user_id;
        }
    }
}
