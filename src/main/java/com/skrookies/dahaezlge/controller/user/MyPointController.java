package com.skrookies.dahaezlge.controller.user;

import com.skrookies.dahaezlge.service.purchase.PurchaseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPointController {
    private final PurchaseService purchaseService;

    @GetMapping("/pointCharger")
    public String pointCharger(HttpSession session) {
        log.info("pointCharger");
        return "pointCharger";
    }

    @PostMapping("/pointChargeProc")
    public String pointChargeProc(Model model, RedirectAttributes redirectAttributes,
                                  @RequestParam("charge_point") int charge_point, HttpSession session){
        log.info("pointChargeProc");
        if(session.getAttribute("user_id") != null) {
            int point = (int) session.getAttribute("point");
            log.info("pointChargeProc");
            log.info("point: "+ point);
            log.info("charge_point: " + charge_point);
            int after_charge_point = charge_point+point;
            log.info("충전 후 잔액 : " + after_charge_point);
            if(purchaseService.chargePoint((String)session.getAttribute("user_id"), charge_point)){
                session.setAttribute("point", after_charge_point);
                redirectAttributes.addFlashAttribute("showPointChargerModal", false);

                return "redirect:/eBookPurchase";
            } else {
                return "false";
            }
        } else {
            return "redirect:/loginForm";
        }
    }


}
