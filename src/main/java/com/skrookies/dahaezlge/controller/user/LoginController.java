package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.security.SecurityController;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.security.AESService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

                /** SQL, XSS 필터링*/
                user_id = xssFilterService.filter(user_id);
                user_id = sqlFilterService.filter(user_id);

                user_pw = xssFilterService.filter(user_pw);
                user_pw = sqlFilterService.filter(user_pw);

                log.info("no user_pw, user_id");
                log.info("ID: " + user_id);
                log.info("PW: " + user_pw);


                if(userService.login(user_id, user_pw)){
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
                }
                else{
                    log.info("없는 아이디");

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
    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {
        // 세션을 무효화하여 모든 정보를 삭제
        session.invalidate(); // 세션 무효화 (모든 세션 속성 삭제)
        return "redirect:/index";
    }

}
