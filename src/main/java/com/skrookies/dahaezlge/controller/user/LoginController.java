package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.skrookies.dahaezlge.service.security.AESService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final AESService aesService;
    private final XssFilterService xssFilterService;
    private final SqlFilterService sqlFilterService;

    /** 로그인 폼 */
    @GetMapping("/loginForm")
    public String loginForm_form(){

        log.info("loginForm");
        return "loginForm";
    }

    /** 로그인 프로세스 */
    @PostMapping("/loginProc")
    public String loginProc_form(Model model, @RequestParam String encrypted_data, HttpSession session, HttpServletRequest request){
        session.invalidate();
        session = request.getSession(true);

        log.info("loginProc");
        try {
            log.info("login try encrypted: "+ encrypted_data);
            String decryptedPassword = aesService.decrypt(encrypted_data);
            log.info("login try decrypted: "+ decryptedPassword);

            if (decryptedPassword.equals("&&&&")) {
                log.info("no user_pw, user_id");
                model.addAttribute("warn", "2");
                return "loginForm";
            } else {

                String[] LoginInfoParts = decryptedPassword.split("&&&&");
                String user_id = LoginInfoParts[0];
                String user_pw = LoginInfoParts[1];


                log.info("no user_pw, user_id");
                log.info("ID: " + user_id);
                log.info("PW: " + user_pw);

                String login_result = userService.login(user_id, user_pw);
                if (login_result.equals("locked")){
                    session.setAttribute("login_locked","1");
                }
                if(login_result.equals("true")){
                    log.info("user_id = " + user_id);

                    session.setAttribute("user_id", user_id);
                    log.info("sessionId = " + session.getAttribute("user_id"));

                    /** user_level 가져오기 */
                    int userLevel = userService.getUserLevel(user_id); // user_level 조회 메서드
                    session.setAttribute("user_level", userLevel);
                    log.info("user_level = " + userLevel);

                    /** point select 메소드 */
                    int point = userService.userPoint(user_id);
                    session.setAttribute("point", point);
                    log.info("point = " + point);
                    log.info("user_id = " + user_id);
                    return "redirect:/index";
                } else if (login_result.equals("no_uesr")) {
                    model.addAttribute("warn", "1");
                    return "loginForm";
                }
                else{
                    log.info("error");
                    model.addAttribute("warn", "1");
                    return "loginForm";
                }}
        } catch (Exception e) {
            e.printStackTrace();
            log.info("error");
            return "loginForm";
        }


    }

    /** 로그아웃 프로세스 */
    @GetMapping("/logoutForm")
    public String logoutForm_form(Model model, HttpServletRequest request, HttpSession session) {
        log.info("errorMessage: " + session.getAttribute("errorMessage"));
        log.info("user: " + session.getAttribute("user_id"));
        if (session.getAttribute("errorMessage") != null){
            log.info("errorMessage: " + session.getAttribute("errorMessage"));
            String errorMessage = "비밀번호 5회 오류로 계정이 잠겼습니다. 10분 후 다시 시도하십시오.";
            session.invalidate(); // 세션 무효화 (모든 세션 속성 삭제)
            // 기존 세션을 가져오거나 새로운 세션을 생성
            session = request.getSession(true);
            session.setAttribute("errorMessage", errorMessage);
        } else {
            log.info("/logout");
            // 세션을 무효화하여 모든 정보를 삭제
            session.invalidate(); // 세션 무효화 (모든 세션 속성 삭제)
        }
        return "redirect:/index";
    }

}
