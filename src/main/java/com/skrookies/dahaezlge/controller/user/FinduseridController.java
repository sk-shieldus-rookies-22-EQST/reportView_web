package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.controller.user.Dto.FinduseridDto;
import com.skrookies.dahaezlge.controller.user.Dto.LoginDto;
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

    @GetMapping("/findUseridpw")
    public String findUseridpw_form(){

        log.info("page_move: findUseridpw.jsp");
        return "findUseridpw";
    }

    @PostMapping("/findUserProc")
    public String findUseridpw_form(Model model, HttpSession session, @ModelAttribute FinduseridDto finduseridDto, @RequestParam("whatFind") Integer whatFind){
        String user_id = userService.findUserId(finduseridDto.getUser_phone(), finduseridDto.getUser_email());
        log.info("FinduseridController - whatfind = " + whatFind);
         if(whatFind == 1){
             log.info("FinduseridController - user_id = " + user_id);
            if(user_id == "no_users"){
                return "redirect:/findUseridpw?warnid='1'";
            } else if (user_id == "error"){
                return "redirect:/findUseridpw?errorid='1'";
            } else {
                return "redirect:/findUseridpw?foundId=" + user_id;
            }
        } else if(whatFind == 2) {
             log.info("FinduseridController - user_id = " + user_id);
             if(user_id == "no_users"){
                 log.info("FinduseridController - nousers ");
                 return "redirect:/findUseridpw?warnpw='1'";
             } else if (user_id == "error"){
                 log.info("FinduseridController - error ");
                 return "redirect:/findUseridpw?errorpw='1'";
             } else {
                 session.setAttribute("find_pw_userid", user_id);
                 return "redirect:/modifyUserpwForm";
             }
        } else {
            return "redirect:/findUseridpw";
        }
    }

    @GetMapping("/modifyUserpwForm")
    public String modifyUserpwForm_form(Model model, HttpSession session){
        String find_pw_userid = (String) session.getAttribute("find_pw_userid");
        log.info("page_move: find_pw_userid = " + find_pw_userid);
        log.info("page_move: modifyUserpwForm.jsp");
        return "/modifyUserpw";
    }

    @PostMapping("/modifyUserpw")
    public String modifyUserpw_form(Model model, @ModelAttribute FinduseridDto finduseridDto, HttpSession session){
        String new_user_pw = (String) finduseridDto.getNew_user_pw();
        String re_new_user_pw = (String) finduseridDto.getRe_new_user_pw();
        String find_pw_userid = (String) session.getAttribute("find_pw_userid");
        if (new_user_pw.toString().equals(re_new_user_pw.toString())){
            Boolean login_result = userService.updateUserpw(find_pw_userid, new_user_pw);
            log.info("login_result: " + login_result);
            if(login_result){
                return "redirect:/findUseridpw?success=1";
            } else {
                return "redirect:/findUseridpw?errorpw=1";
            }
        }else {

            log.info("new_user_pw: " + new_user_pw);
            log.info("re_new_user_pw: " + re_new_user_pw);
            return "redirect:/modifyUserpwForm?warn=1";
        }
    }
}
