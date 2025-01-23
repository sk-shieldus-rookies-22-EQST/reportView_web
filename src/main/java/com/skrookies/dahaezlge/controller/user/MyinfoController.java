package com.skrookies.dahaezlge.controller.user;


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

public class MyinfoController {
    private final UserService userService;

    @GetMapping("/myInfo")
    public String myInfo_form(Model model, HttpSession session){

        log.info("page_move: myInfo.jsp");

        String user_id = (String) session.getAttribute("user_id");
        log.info(user_id);
        if (user_id == null) {
            log.info("User not logged in");
            return "redirect:/loginForm";  // 로그인 페이지로 리다이렉트
        } else {
            // 사용자 정보를 가져오기
            log.info("mtinfocontroller - else문 입장!");
            List<Users> user_info = userService.userInfo(user_id);

            if (user_info == null || user_info.isEmpty()) {
                log.info("User information not found for user_id: " + user_id);
                return "redirect:/loginForm";  // 예시로 에러 페이지로 리다이렉트
            } else {
                Users user = user_info.get(0);
                model.addAttribute("user_id", user.getUser_id());
                model.addAttribute("user_pw", user.getUser_pw());
                model.addAttribute("user_phone", user.getUser_phone());
                model.addAttribute("user_email", user.getUser_email());
                return "/myInfo";
            }
        }
    }
}
