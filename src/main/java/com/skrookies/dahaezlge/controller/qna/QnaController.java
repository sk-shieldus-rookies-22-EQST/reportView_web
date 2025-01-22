package com.skrookies.dahaezlge.controller.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qnaList")
public class QnaController {
    @GetMapping("/qnaWrite")
    public String qnaWrite_form() {
        return "qnaWrite";
    }

    @PostMapping("/qnaWriteProcess")
    public String qnaWrite(@ModelAttribute QnaDto QnaDto) {
        return "true";
    }
}
