package com.skrookies.dahaezlge.controller.main;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MainController {

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

    @GetMapping("/loginFormBoot")
    public String loginFormBoot_form(){

        log.info("page_move: loginFormBoot.jsp");
        return "loginFormBoot";
    }

    @GetMapping("/loginProc")
    public String loginProc_form(){

        log.info("page_move: loginProc.jsp");
        return "loginProc";
    }

    @GetMapping("/myInfo")
    public String myInfo_form(){

        log.info("page_move: myInfo.jsp");
        return "myInfo";
    }

    @GetMapping("/myInfoModify")
    public String myInfoModify_form(){

        log.info("page_move: myInfoModify.jsp");
        return "myInfoModify";
    }

    @GetMapping("/myInfoProc")
    public String myInfoProc_form(){

        log.info("page_move: myInfoProc.jsp");
        return "myInfoProc";
    }

    @GetMapping("/eBookDetail")
    public String eBookDetail_form(){

        log.info("page_move: eBookDetail.jsp");
        return "eBookDetail";
    }
}
