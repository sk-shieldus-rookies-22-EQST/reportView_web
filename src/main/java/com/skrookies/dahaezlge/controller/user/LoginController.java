package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.security.SecurityController;
import com.skrookies.dahaezlge.service.security.AESService;
import com.skrookies.dahaezlge.service.user.UserService;
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


    /** 로그인 폼 */
    @GetMapping("/loginForm")
    public String loginForm_form(){

        log.info("loginForm");
        return "loginForm";
    }

    /** 로그인 프로세스 */
    @PostMapping("/loginProc")
    public String loginProc_form(Model model, @RequestParam String encrypted_pw, HttpSession session){
//        String user_id = loginDto.getUser_id();
//        log.info("Login Id: " + user_id);
//        log.info("Login Password: " + loginDto.getUser_pw());
        log.info("loginProc");
        try {
            log.info("login try encrypted: "+ encrypted_pw);
            String decryptedPassword = aesService.decrypt(encrypted_pw);
            log.info("login try decrypted: "+ decryptedPassword);

            String[] passwordParts = decryptedPassword.split("&&&&");

            // 예시: 분리된 값들로 추가 작업
            log.info("ID: " + passwordParts[0]);
            log.info("PW: " + passwordParts[1]);

            String user_id = passwordParts[0];
            String user_pw = passwordParts[1];

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
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
