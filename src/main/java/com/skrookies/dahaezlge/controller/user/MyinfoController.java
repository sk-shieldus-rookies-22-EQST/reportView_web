package com.skrookies.dahaezlge.controller.user;


import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.entity.user.Users;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.security.AESService;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor

public class MyinfoController {
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

    @PostMapping("/goToMyInfo")
    public String goToMyInfo_form(@RequestParam("encrypted_password") String password, HttpSession session) throws Exception {
        String user_id = (String)session.getAttribute("user_id");

        if(user_id != null){
            log.info("goToMyInfo user_id: "+ user_id);
            log.info("goToMyInfo password: "+ password);

            String decryptedPassword = aesService.decrypt(password);
            log.info("goToMyInfo decrypted: "+ decryptedPassword);

            Boolean user_check = userService.login(user_id,decryptedPassword);
            if (user_check){
                session.setAttribute("CanGoMyInfo", "true");
                return "redirect:/myInfo";
            } else {
                log.info("Wrong Password");
                log.info("여긴 goToMyInfo");
                session.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                return "redirect:/index";
            }
        } else {
            log.info("user_id가 null입니다. 로그인 필요.");
            return "redirect:/loginForm";
        }
    }

    /** 회원 정보 페이지 */
    @GetMapping("/myInfo")
    public String myInfo_form(Model model, HttpSession session){
        if (session.getAttribute("CanGoMyInfo") == null) {
            return "redirect:/index";
        }
        log.info("page_move: myInfo.jsp");

        String user_id = (String) session.getAttribute("user_id");
        log.info(user_id);
        if (user_id == null) {
            log.info("User not logged in");
            return "redirect:/loginForm";  // 로그인 페이지로 리다이렉트
        } else {
            // 사용자 정보를 가져오기
            log.info("myinfocontroller - else문 입장!");
            List<Users> user_info = userService.userInfo(user_id);

            if (user_info == null || user_info.isEmpty()) {
                log.info("User information not found for user_id: " + user_id);
                return "redirect:/loginForm";  // 예시로 에러 페이지로 리다이렉트
            } else {
                Users user = user_info.get(0);
                model.addAttribute("user_phone", user.getUser_phone());
                model.addAttribute("user_email", user.getUser_email());
                model.addAttribute("myInfoModifyForm", "0");
                return "myInfo";
            }
        }
    }
    /** 회원 정보 수정 페이지 */
    @GetMapping("/myInfoModify")
    public String myInfoModify_form(Model model, UserDto userDto, HttpSession session){
        if (session.getAttribute("CanGoMyInfo") == null) {
            return "redirect:/index";
        }
        String user_id = (String) session.getAttribute("user_id");
        if (user_id == null) {
            log.info("User not logged in");
            return "redirect:/loginForm";  // 로그인 페이지로 리다이렉트
        } else {
            // 사용자 정보를 가져오기
            log.info("mtinfocontroller - else문 입장!");
            List<Users> user_info = userService.userInfo(user_id);

            if (user_info == null || user_info.isEmpty()) {
                return "redirect:/loginForm";  // 예시로 에러 페이지로 리다이렉트
            } else {
                Users user = user_info.get(0);
                model.addAttribute("user_phone", user.getUser_phone());
                model.addAttribute("user_email", user.getUser_email());
                model.addAttribute("myInfoModifyForm", "1");
                return "myInfo";
            }
        }
    }

    /** 수정된 회원 정보 저장 */
    @PostMapping("/myInfoSave")
    public String myInfoSave_form(Model model, UserDto userDto, HttpSession session) throws Exception {
        log.info("myInfoSave");
        String user_id = (String)session.getAttribute("user_id");

        String info_Encrypted = userDto.getUser_encrypted();
        log.info("info_Encrypted: " + info_Encrypted);

        String info_Decrypted = aesService.decrypt(info_Encrypted);
        if (info_Decrypted == null || info_Decrypted.isEmpty()) {
            log.info("No data received");
            model.addAttribute("warn", "1"); // 데이터가 비었을 때 경고
            return "myInfo";
        } else {
            // &&&& 구분자로 분리된 값을 배열로 나누기
            String[] RegisterInfoParts = info_Decrypted.split("&&&&");

            // 필요한 최소 필드 개수 체크
            int requiredFields = 4;  // 최소 필요한 필드 개수 (필요에 따라 조정)
            if (RegisterInfoParts.length < requiredFields) {
                log.info("Invalid data, missing required fields");
                model.addAttribute("warn", "3"); // 필드가 부족한 경우 경고
                return "myInfo";
            }

            // 각각의 값을 변수에 할당
            String user_pw = RegisterInfoParts[0];
            String re_user_pw = RegisterInfoParts[1];
            String user_phone = RegisterInfoParts[2];
            String user_email = RegisterInfoParts[3];

            // 데이터가 모두 올바르게 전달되었는지 확인
            log.info("user_pw: {}", user_pw);
            log.info("re_user_pw: {}", re_user_pw);
            log.info("user_email: {}", user_email);
            log.info("user_phone: {}", user_phone);

            // '<', '>' --> '&lt', '&gt'
            if (!isPasswordStrong(user_pw).equals("true")) {
                String status = isPasswordStrong(user_pw);
                log.info("status: " + status);
                session.setAttribute("status", status);
                return "redirect:/myInfoModify";
            }
            user_pw = convertToHtmlEntities(user_pw);
            re_user_pw = convertToHtmlEntities(re_user_pw);
            user_phone = convertToHtmlEntities(user_phone);
            user_email = convertToHtmlEntities(user_email);

            if (user_pw != null && re_user_pw != null && user_phone != null && user_email != null) {
                if (!user_pw.equals(re_user_pw)) {
                    log.info("비밀번호 불일치");
                    session.setAttribute("status", "1");
                    return "redirect:/myInfoModify";
                }
                if (!isValidPhoneNumber(user_phone)) {
                    log.info("전화번호 형태 확인");
                    session.setAttribute("status", "2");
                    return "redirect:/myInfoModify";
                } else {
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    log.info("입력한 모든 값이 not null입니다.");
                    String encodedPassword = passwordEncoder.encode(user_pw);
                    Boolean update_result = userService.updateUserInfo(user_id, encodedPassword, user_phone, user_email);
                    if (update_result) {
                        return "redirect:/myInfo";
                    }
                }
            } else {
                log.info("입력한 모든 값 중 null이 있습니다.");
                return "myInfo";
            }

            return "myInfo";
        }
    }

    @PostMapping("/delUser")
    public String delUser_form(@RequestParam("del_password") String del_password, Model model, UserDto userDto, HttpSession session, HttpServletRequest request){
        log.info("delUser");
        String user_id = (String)session.getAttribute("user_id");
        if(user_id != null){
            log.info("탈퇴할 user_id: "+ user_id);

            Boolean user_check = userService.login(user_id,del_password);
            if (user_check){
                Boolean deleted_user = userService.deleteUser(user_id);
                if (deleted_user){
                    log.info("deluser: user_delete");
                    session.invalidate();
                    HttpSession newSession = request.getSession(true); // 새로운 세션을 생성
                    newSession.setAttribute("deletedMessage", "탈퇴가 완료되었습니다.");
                    return "redirect:/index";
                }
            } else {
                log.info("Wrong Password");
                session.setAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                log.info("index로 가지 마라 제발");
                return "redirect:/myInfo";
            }
        } else {
            log.info("탈퇴할 user_id가 null입니다. 로그인 필요.");
            return "redirect:/loginForm";
        }

        return "redirect:/myInfo";
    }
}
