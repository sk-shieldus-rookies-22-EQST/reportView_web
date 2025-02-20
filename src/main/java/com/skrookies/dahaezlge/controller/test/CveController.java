package com.skrookies.dahaezlge.controller.test;

import com.skrookies.dahaezlge.controller.test.Dto.LoginRequest;
import com.skrookies.dahaezlge.service.security.AESService;
import com.skrookies.dahaezlge.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CveController {

    private final UserService userService;
    private final AESService aesService;

    /** 로그인 폼 */
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "loginForm";
    }

    /** 로그인 프로세스 */
    @PostMapping("/login")
    public String loginProcess(@ModelAttribute LoginRequest loginRequest, HttpSession session, HttpServletRequest request, Model model) {
        session.invalidate();
        session = request.getSession(true); // request에서 새로운 세션 가져오기

        try {
            log.info("Encrypted Data: " + loginRequest.getEncryptedData());
            String decryptedPassword = aesService.decrypt(loginRequest.getEncryptedData());
            log.info("Decrypted Data: " + decryptedPassword);

            if (decryptedPassword.isEmpty() || !decryptedPassword.contains("&&&&")) {
                model.addAttribute("warn", "2");
                return "loginForm";
            }

            String[] loginInfoParts = decryptedPassword.split("&&&&");
            String userId = loginInfoParts[0];
            String userPw = loginInfoParts[1];

            log.info("User ID: " + userId);
            log.info("User PW: " + userPw);

            String loginResult = userService.login(userId, userPw);
            if (loginResult.equals("locked")) {
                session.setAttribute("login_locked", "1");
                return "loginForm";
            }

            if (loginResult.equals("true")) {
                session.setAttribute("user_id", userId);
                session.setAttribute("user_level", userService.getUserLevel(userId));
                session.setAttribute("point", userService.userPoint(userId));
                return "redirect:/index";
            }

            model.addAttribute("warn", "1");
            return "loginForm";
        } catch (Exception e) {
            log.error("Login error", e);
            return "loginForm";
        }
    }


    /** 로그아웃 */
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        String errorMessage = (String) session.getAttribute("errorMessage");

        session.invalidate();
        if (errorMessage != null) {
            session = request.getSession(true);
            session.setAttribute("errorMessage", "비밀번호 5회 오류로 계정이 잠겼습니다. 10분 후 다시 시도하십시오.");
        }

        return "redirect:/index";
    }
}
