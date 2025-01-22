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

    @GetMapping("/loginForm")
    public String loginFormBoot_form(){

        log.info("page_move: loginForm.jsp");
        return "loginForm";
    }

    @GetMapping("/loginProc")
    public String loginProc_form(){

        log.info("page_move: loginProc.jsp");
        return "loginProc";
    }

    @GetMapping("/findUseridpw")
    public String findUseridpw_form(){

        log.info("page_move: findUseridpw.jsp");
        return "findUseridpw";
    }

    @GetMapping("/myInfo")
    public String myInfo_form(){

        log.info("page_move: myInfo.jsp");
        return "myInfo";
    }

    @GetMapping("/registerForm")
    public String registerFrom_form(){

        log.info("page_move: registerForm.jsp");
        return "registerForm";
    }

    @GetMapping("/registerProc")
    public String registerProc_form(){

        log.info("page_move: registerProc.jsp");
        return "registerProc";
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

    @GetMapping("/qnaList")
    public String qnaList_form(){

        log.info("page_move: qnaList.jsp");
        return "qnaList";
    }
}
