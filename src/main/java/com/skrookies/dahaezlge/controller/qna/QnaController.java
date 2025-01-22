package com.skrookies.dahaezlge.controller.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.service.qna.QnaService;
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
    private final QnaService QnaService;

    @GetMapping("/qnaWrite")
    public String qnaWrite_form() {
        return "qnaWrite";
    }

    @PostMapping("/qnaWriteProcess")
    public String qnaWrite(@ModelAttribute QnaDto QnaDto) {
        int qnaResult = QnaService.qna(QnaDto);
        if (qnaResult > 0) {
            return "redirect:/qnaList/";
        } else {
            return "qnaWrite";
        }
    }
}
