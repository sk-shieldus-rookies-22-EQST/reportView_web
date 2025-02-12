package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.controller.user.Dto.FinduseridDto;
import com.skrookies.dahaezlge.controller.user.Dto.LoginDto;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FinduseridController {

    private final UserService userService;
    private final XssFilterService xssFilterService;
    private final SqlFilterService sqlFilterService;

    // HTML 엔티티로 치환 ('<', '>')
    public static String convertToHtmlEntities(String input) {
        return input.replace("<", "&lt;").replace(">", "&gt;");
    }

    //전화번호 형태 점검
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\d{3}-\\d{4}-\\d{4}$";
        // Pattern 객체 생성
        Pattern pattern = Pattern.compile(regex);
        // 입력값을 Matcher로 확인
        Matcher matcher = pattern.matcher(phoneNumber);
        // 정규식에 맞는지 확인
        return matcher.matches();
    }

    // 비밀번호 복잡도 검사 함수
    public static String isPasswordStrong(String password) {
        // 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자가 포함되어야 함
        String lengthPattern = "^.{8,}$";  // 최소 8자 이상
        String upperCasePattern = ".*[A-Z].*"; // 대문자
        String lowerCasePattern = ".*[a-z].*"; // 소문자
        String digitPattern = ".*[0-9].*";     // 숫자
        String specialCharPattern = ".*[!@#$%^&*(),.?\":{}|<>].*"; // 특수문자

        if (!Pattern.matches(lengthPattern, password)) {
            System.out.println("비밀번호는 최소 8자 이상이어야 합니다.");
            return "8자";
        }

        if (!Pattern.matches(upperCasePattern, password)) {
            System.out.println("비밀번호는 대문자를 포함해야 합니다.");
            return "대문자";
        }

        if (!Pattern.matches(lowerCasePattern, password)) {
            System.out.println("비밀번호는 소문자를 포함해야 합니다.");
            return "소문자";
        }

        if (!Pattern.matches(digitPattern, password)) {
            System.out.println("비밀번호는 숫자를 포함해야 합니다.");
            return "숫자";
        }

        if (!Pattern.matches(specialCharPattern, password)) {
            System.out.println("비밀번호는 특수문자를 포함해야 합니다.");
            return "특수문자";
        }

        return "true";  // 모든 조건을 만족하면 true 반환
    }

    /** 아이디, 비밀번호 찾기 페이지 이동*/
    @GetMapping("/findUseridpw")
    public String findUseridpw_form(){

        log.info("page_move: findUseridpw.jsp");
        return "findUseridpw";
    }

    /** 아이디 찾기 프로세스*/
    @PostMapping("/findUserProc")
    public String findUseridpw_form(HttpSession session, @ModelAttribute FinduseridDto finduseridDto, @RequestParam("whatFind") Integer whatFind){





        log.info("FinduseridController - whatfind = " + whatFind);
         if(whatFind == 1){
             String user_phone = finduseridDto.getUser_phone();
             String user_email = finduseridDto.getUser_email();
            String found_user_id = userService.findUserId(user_phone, user_email);
             log.info("FinduseridController - user_id = " + found_user_id);
            if(found_user_id.equals("no_users")){
                return "redirect:/findUseridpw?warnid=1";
            } else if (found_user_id.equals("error")){
                return "redirect:/findUseridpw?errorid=1";
            } else if(!isValidPhoneNumber(user_phone)) {
                log.info("전화번호 형태가 아닙니다.");
                return "redirect:/registerForm?warn_phone_id=1";
            } else {
                return "redirect:/findUseridpw?foundId=" + found_user_id;
            }
        } else if(whatFind == 2) {
             String user_id_pw = finduseridDto.getUser_id_pw();
             user_id_pw = convertToHtmlEntities(user_id_pw);
             String user_phone_pw = finduseridDto.getUser_phone_pw();
             String user_email_pw = finduseridDto.getUser_email_pw();
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
             } else if(!isValidPhoneNumber(user_phone_pw)) {
                 log.info("전화번호 형태가 아닙니다.");
                 return "redirect:/registerForm?warn_phone_pw=1";
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
        if (!isPasswordStrong(new_user_pw).equals("true")){
            session.setAttribute("status",isPasswordStrong(new_user_pw));
            return "redirect:/modifyUserpwForm";
        }
        if (new_user_pw.toString().equals(re_new_user_pw.toString())){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(new_user_pw);
            Boolean login_result = userService.updateUserpw(find_pw_userid, encodedPassword);
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
