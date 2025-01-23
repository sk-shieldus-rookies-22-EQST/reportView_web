package com.skrookies.dahaezlge.controller.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
@Slf4j
public class QnaController {
    private final QnaService QnaService;

    @GetMapping("/qnaList")
    public String qnaList_form(){

        log.info("page_move: qnaList.jsp");
        return "qnaList";
    }

    @GetMapping("/qnaWrite")
    public String qnaWrite_form(){

        log.info("page_move: qnaWrite.jsp");
        return "qnaWrite";
    }


    @PostMapping("/qnaWriteProcess")
    public String qnaWrite(@ModelAttribute QnaDto QnaDto) {
        QnaDto.setQna_created_at(LocalDate.now());
        int qnaResult = QnaService.qna(QnaDto);
        if (qnaResult > 0) {
            return "redirect:/qnaList/";
        } else {
            return "qnaWrite";
        }
    }
}
