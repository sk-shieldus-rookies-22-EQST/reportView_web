package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.AttributedString;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPointController {
    private final PurchaseService purchaseService;

    /** 포인트 충전 페이지 */
    @GetMapping("/pointCharger")
    public String pointCharger(HttpSession session ) {
        log.info("pointCharger");
        return "pointCharger";
    }

    /** 포인트 충전 프로세스 */
    @PostMapping("/pointChargeProc")
    public String pointChargeProc(Model model, RedirectAttributes redirectAttributes,
                                  HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("charge_point") String input_point,
                                  HttpSession session) throws ServletException, IOException {
        log.info("pointChargeProc");
        int charge_point = 0;
        if (!input_point.equals("")) {
            charge_point = Integer.parseInt(input_point);
        }
        if (Integer.parseInt(input_point) < 0){
            charge_point = 0;
        }

        String referer = (String) session.getAttribute("referer");
        log.info("Previous Page URL: " + referer);

        if(session.getAttribute("user_id") != null) {
            int point = (int) session.getAttribute("point");
            log.info("pointChargeProc");
            log.info("point: "+ point);
            log.info("charge_point: " + charge_point);

            int after_charge_point = charge_point+point;
            log.info("충전 후 잔액 : " + after_charge_point);

            if(purchaseService.chargePoint((String)session.getAttribute("user_id"), charge_point)) {
                session.setAttribute("point", after_charge_point);
                log.info(referer);
                try {
                    if (referer != null && referer.contains("PurchaseItem")) {
                        return "forward:/eBookPurchaseItem";
                    } else if (referer != null && referer.contains("Purchase")) {
                        return "forward:/eBookPurchase";
                    } else {
                        // 마지막 '/' 이후의 부분 추출 (history 기능)
                        String lastElement = referer.substring(referer.lastIndexOf('/') + 1);
                        log.info(lastElement);
                        return "redirect:/" + lastElement;
                    }
                } catch (Exception e) {
                    return "redirect:/index";
                }
            } else {
                return "false";
            }
        } else {
            return "redirect:/loginForm";
        }
    }
}
