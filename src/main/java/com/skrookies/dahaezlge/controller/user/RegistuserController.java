package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.security.AESService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
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
    private final AESService aesService;
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
    public String registerProc_form(Model model, @ModelAttribute UserDto userDto, HttpSession session) throws Exception {
        String user_Encrypted = userDto.getUser_encrypted();
        log.info("user_Encrypted");

        String user_Decrypted = aesService.decrypt(user_Encrypted);
        if (user_Decrypted == null || user_Decrypted.isEmpty()) {
            log.info("No data received");
            model.addAttribute("warn", "1"); // 데이터가 비었을 때 경고
            return "registerForm";
        } else {
            // &&&& 구분자로 분리된 값을 배열로 나누기
            String[] RegisterInfoParts = user_Decrypted.split("&&&&");

            // 필요한 최소 필드 개수 체크 (예: user_id, user_pw, re_user_pw, user_email, user_phone, user_agree)
            int requiredFields = 6;  // 최소 필요한 필드 개수 (필요에 따라 조정)
            if (RegisterInfoParts.length < requiredFields) {
                log.info("Invalid data, missing required fields");
                model.addAttribute("warn", "3"); // 필드가 부족한 경우 경고
                return "registerForm";
            }

            // 각각의 값을 변수에 할당
            String user_id = RegisterInfoParts[0];        // 첫 번째 값: user_id
            String user_pw = RegisterInfoParts[1];        // 두 번째 값: user_pw
            String re_user_pw = RegisterInfoParts[2];     // 세 번째 값: re_user_pw
            String user_email = RegisterInfoParts[3];     // 네 번째 값: user_email
            String user_phone = RegisterInfoParts[4];     // 다섯 번째 값: user_phone
            String user_agree = RegisterInfoParts[5];     // 여섯 번째 값: user_agree

            // 데이터가 모두 올바르게 전달되었는지 확인
            log.info("user_id: {}", user_id);
            log.info("user_pw: {}", user_pw);
            log.info("re_user_pw: {}", re_user_pw);
            log.info("user_email: {}", user_email);
            log.info("user_phone: {}", user_phone);
            log.info("user_agree: {}", user_agree);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // '<', '>' --> '&lt', '&gt'
            user_id = convertToHtmlEntities(user_id);
            if (!isPasswordStrong(user_pw).equals("true")) {
                String status = isPasswordStrong(user_pw);
                if (status.equals("8자")) {
                    session.setAttribute("status", "8자");
                } else if (status.equals("대문자")) {
                    session.setAttribute("status", "대문자");
                } else if (status.equals("소문자")) {
                    session.setAttribute("status", "소문자");
                } else if (status.equals("숫자")) {
                    session.setAttribute("status", "숫자");
                } else if (status.equals("특수문자")) {
                    session.setAttribute("status", "특수문자");
                }
                return "redirect:/registerForm";
            }
            user_pw = convertToHtmlEntities(user_pw);
            re_user_pw = convertToHtmlEntities(re_user_pw);
            user_phone = convertToHtmlEntities(user_phone);
            user_phone = convertToHtmlEntities(user_phone);
            log.info("agree: " + user_agree);
            if (userService.checkId(user_id)) {
                log.info("이미 있는 아이디");
                session.setAttribute("status", "1");
                return "redirect:/registerForm";
            } else if (!user_pw.equals(re_user_pw)) {
                log.info("비밀번호 불일치");
                session.setAttribute("status", "5");
                return "redirect:/registerForm";
            } else {
                if (user_agree != null) {
                    if (!isValidPhoneNumber(user_phone)) {
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
                        session.setAttribute("status", "2");
                        log.info("모든 input 채우기");
                        return "redirect:/registerForm";
                    }

                } else {
                    session.setAttribute("status", "3");
                    log.info("활용 동의");
                    return "redirect:/registerForm";
                }
            }
        }

    }

}
