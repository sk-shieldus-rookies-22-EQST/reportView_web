package com.skrookies.dahaezlge.controller.main;

import com.skrookies.dahaezlge.controller.user.Dto.SessionDto;
import com.skrookies.dahaezlge.controller.user.Dto.UserDto;
import com.skrookies.dahaezlge.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    public String login_id(HttpSession session) {
        return (String) session.getAttribute("user_id");
    }

    @GetMapping("/index")
    public String main_form(){
        log.info("page_move: index.jsp");
        return "index";
    }

    @GetMapping("/banner")
    public String banner_form(){

        log.info("page_move: banner.jsp");
        return "banner";
    }




//    @GetMapping("/myInfo") => FinduserController로 이동
//    public String myInfo_form(Model model, HttpSession session){
//
//        log.info("page_move: myInfo.jsp");
//
//        String user_id = (String) session.getAttribute("id");
//        if (user_id == null) {
//            log.warn("User not logged in");
//            return "redirect:/loginForm";  // 로그인 페이지로 리다이렉트
//        }
//
//        // 사용자 정보를 가져오기
//        List user_info = userService.userInfo(user_id);
//        if (user_info == null || user_info.isEmpty()) {
//            log.error("User information not found for user_id: " + user_id);
//            return "redirect:/loginForm";  // 예시로 에러 페이지로 리다이렉트
//        }
//
//        // UserDto 객체 생성
//        //UserDto userDto = new UserDto(user_id, (String)user_info.get(0));
//
//        // 모델에 데이터 추가
//        model.addAttribute("user_id", new UserDto(user_id, (String)user_info.get(0)));
//        return "myInfo";
//    }

    @GetMapping("/registerForm")
    public String registerForm_form(){

        log.info("page_move: registerForm.jsp");
        return "registerForm";
    }

    @GetMapping("/registerProc")
    public String registerProc_form(){

        log.info("page_move: registerProc.jsp");
        return "registerProc";
    }

    @GetMapping("/myInfoModify")
    public String myInfoModify_form(){

        log.info("page_move: myInfoModify.jsp");
        return "myInfoModify";
    }

    @GetMapping("/myInfoProc")
    public String myInfoProc_form(){

        log.info("page_move: myInfoProc.jsp");
        return "myInfoProc";
    }

    @GetMapping("/eBookDetail")
    public String eBookDetail_form(){

        log.info("page_move: eBookDetail.jsp");
        return "eBookDetail";
    }

    @GetMapping("/qnaList")
    public String qnaList_form(){

        log.info("page_move: qnaList.jsp");
        return "qnaList";
    }
}
