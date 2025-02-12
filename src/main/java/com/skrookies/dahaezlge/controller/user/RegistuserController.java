package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RegistuserController {

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


    /** 회원가입 페이지 */
    @GetMapping("/registerForm")
    public String registerForm_form(){

        log.info("registerForm");
        return "registerForm";
    }



    /** 회원가입 프로세스 */
    @PostMapping("/registerProc")
    public String registerProc_form(Model model, @ModelAttribute UserDto userDto, HttpSession session){
        String user_Encrypted = userDto.getUser_encrypted();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // '<', '>' --> '&lt', '&gt'
        String user_id = userDto.getUser_id();
        user_id = convertToHtmlEntities(user_id);
        String user_pw = userDto.getUser_pw();
        if (!isPasswordStrong(user_pw).equals("true")){
            String status = isPasswordStrong(user_pw);
            if (status.equals("8자")){
                session.setAttribute("status","8자");
            } else if (status.equals("대문자")){
                session.setAttribute("status","대문자");
            } else if (status.equals("소문자")){
                session.setAttribute("status","소문자");
            } else if (status.equals("숫자")){
                session.setAttribute("status","숫자");
            } else if (status.equals("특수문자")){
                session.setAttribute("status","특수문자");
            }
            return "redirect:/registerForm";
        }
        user_pw = convertToHtmlEntities(user_pw);
        String re_user_pw = userDto.getRe_user_pw();
        re_user_pw = convertToHtmlEntities(re_user_pw);
        String user_phone = userDto.getUser_phone();
        user_phone = convertToHtmlEntities(user_phone);
        String user_email = userDto.getUser_email();
        user_phone = convertToHtmlEntities(user_phone);

        String user_agree = userDto.getUser_agree();
        log.info("agree: " + user_agree);
        if(userService.checkId(user_id)) {
            log.info("이미 있는 아이디");
            session.setAttribute("status", "1");
            return "redirect:/registerForm";
        } else if (!user_pw.equals(re_user_pw)) {
            log.info("비밀번호 불일치");
            session.setAttribute("status", "5");
            return "redirect:/registerForm";
        } else {
                if (user_agree != null) {
                    if(!isValidPhoneNumber(user_phone)) {
                        log.info("전화번호 형태가 아닙니다.");
                        session.setAttribute("status", "4");
                        return "redirect:/registerForm";
                    } else if (user_id != null || user_pw != null || user_phone != null || user_email != null) {

                        String encodedPassword = passwordEncoder.encode(user_pw);
                        userService.registerUser(user_id, encodedPassword, user_phone, user_email);
                        // 회원가입 직후 자동 로그인
                        int point = userService.userPoint(user_id);
                        session.setAttribute("user_id", user_id);
                        session.setAttribute("point", point);
                        session.removeAttribute("status");
                        return "redirect:/index";
                    } else {
                        session.setAttribute("status","2");
                        log.info("모든 input 채우기");
                        return "redirect:/registerForm";
                    }

                } else {
                    session.setAttribute("status","3");
                    log.info("활용 동의");
                    return "redirect:/registerForm";
                }
        }


    }

}
