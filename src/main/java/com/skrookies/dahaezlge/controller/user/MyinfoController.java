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

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor

public class MyinfoController {
    private final UserService userService;
    private final XssFilterService xssFilterService;
    private final SqlFilterService sqlFilterService;

    /** 회원 정보 페이지 */
    @GetMapping("/myInfo")
    public String myInfo_form(Model model, HttpSession session){

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
                log.info(user.getUser_pw());
                //model.addAttribute("user_id", user.getUser_id());
                model.addAttribute("user_pw", user.getUser_pw());
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

                model.addAttribute("user_pw", user.getUser_pw());
                model.addAttribute("user_phone", user.getUser_phone());
                model.addAttribute("user_email", user.getUser_email());
                model.addAttribute("myInfoModifyForm", "1");
                return "myInfo";
            }
        }
    }

    /** 수정된 회원 정보 저장 */
    @PostMapping("/myInfoSave")
    public String myInfoSave_form(Model model, UserDto userDto, HttpSession session){
        log.info("myInfoSave");
        String user_id = (String)session.getAttribute("user_id");

        String user_pw = xssFilterService.filter(userDto.getUser_pw());
        user_pw = sqlFilterService.filter(user_pw);
        String user_phone = xssFilterService.filter(userDto.getUser_phone());
        user_phone = sqlFilterService.filter2(user_phone);
        String user_email = xssFilterService.filter(userDto.getUser_email());
        user_email = sqlFilterService.filter(user_email);
        if(user_pw != null && user_phone != null && user_email != null){
            log.info("입력한 모든 값이 not null입니다.");
            Boolean update_result = userService.updateUserInfo(user_id, user_pw,user_phone,user_email);
            if (update_result){
                return "redirect:/myInfo";
            }
        } else {
            log.info("입력한 모든 값 중 null이 있습니다.");
            return "myInfo";
        }

        return "myInfo";
    }

    @PostMapping("/delUser")
    public String delUser_form(@RequestParam("password") String password, Model model, UserDto userDto, HttpSession session, HttpServletRequest request){
        log.info("delUser");
        String user_id = (String)session.getAttribute("user_id");
        if(user_id != null){
            log.info("탈퇴할 user_id: "+ user_id);
            Boolean user_check = userService.login(user_id,password);
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
                return "redirect:/myInfo";
            }
        } else {
            log.info("탈퇴할 user_id가 null입니다. 로그인 필요.");
            return "redirect:/loginForm";
        }

        return "redirect:/myInfo";
    }
}
