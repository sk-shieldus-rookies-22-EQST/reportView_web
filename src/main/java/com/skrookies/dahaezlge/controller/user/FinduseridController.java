package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.controller.user.Dto.FinduseridDto;
import com.skrookies.dahaezlge.controller.user.Dto.LoginDto;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
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
    private final XssFilterService xssFilterService;
    private final SqlFilterService sqlFilterService;

    /** 아이디, 비밀번호 찾기 페이지 이동*/
    @GetMapping("/findUseridpw")
    public String findUseridpw_form(){

        log.info("page_move: findUseridpw.jsp");
        return "findUseridpw";
    }

    /** 아이디 찾기 프로세스*/
    @PostMapping("/findUserProc")
    public String findUseridpw_form(HttpSession session, @ModelAttribute FinduseridDto finduseridDto, @RequestParam("whatFind") Integer whatFind){
        String user_phone = finduseridDto.getUser_phone();
        String user_email = finduseridDto.getUser_email();
        String user_id_pw = finduseridDto.getUser_id_pw();
        String user_phone_pw = finduseridDto.getUser_phone_pw();
        String user_email_pw = finduseridDto.getUser_email_pw();

        /** SQL, XSS 필터링 */
        user_phone = xssFilterService.filter(user_phone);
        user_phone = sqlFilterService.filter(user_phone);
        user_email = xssFilterService.filter(user_email);
        user_email = sqlFilterService.filter(user_email);

        user_id_pw = xssFilterService.filter(user_id_pw);
        user_id_pw = sqlFilterService.filter(user_id_pw);
        user_phone_pw = xssFilterService.filter(user_phone_pw);
        user_phone_pw = sqlFilterService.filter(user_phone_pw);
        user_email_pw = xssFilterService.filter(user_email_pw);
        user_email_pw = sqlFilterService.filter(user_email_pw);

        log.info("FinduseridController - whatfind = " + whatFind);
         if(whatFind == 1){
            String found_user_id = userService.findUserId(user_phone, user_email);
             log.info("FinduseridController - user_id = " + found_user_id);
            if(found_user_id == "no_users" && found_user_id == "no_users"){
                return "redirect:/findUseridpw?warnid=1";
            } else if (found_user_id == "error" && found_user_id == "error"){
                return "redirect:/findUseridpw?errorid=1";
            } else {
                return "redirect:/findUseridpw?foundId=" + found_user_id;
            }
        } else if(whatFind == 2) {
             String found_user_id_pw = userService.findUserId(user_phone_pw, user_email_pw);
             log.info("FinduseridController - found_user_id_pw = " + found_user_id_pw);
             if(found_user_id_pw == "no_users" && found_user_id_pw == "no_users"){
                 log.info("FinduseridController - nousers ");
                 return "redirect:/findUseridpw?warnpw=1";
             } else if (found_user_id_pw == "error" && found_user_id_pw == "error"){
                 log.info("FinduseridController - error ");
                 return "redirect:/findUseridpw?errorpw=1";
             } else if (found_user_id_pw.equals(user_id_pw)){
                 session.setAttribute("find_pw_userid", found_user_id_pw);
                 return "redirect:/modifyUserpwForm";
             } else {
                 return "redirect:/findUseridpw?warnpw=1";
             }
        } else {
            return "redirect:/findUseridpw";
        }
    }

    /** 비밀번호 수정 페이지 */
    @GetMapping("/modifyUserpwForm")
    public String modifyUserpwForm_form(Model model, HttpSession session){
        String find_pw_userid = (String) session.getAttribute("find_pw_userid");
        log.info("page_move: find_pw_userid = " + find_pw_userid);
        log.info("page_move: modifyUserpwForm.jsp");
        return "modifyUserpw";
    }

    /** 비밀번호 수정 프로세스 */
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
