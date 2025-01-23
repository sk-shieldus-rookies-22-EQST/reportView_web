package com.skrookies.dahaezlge.controller.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class QnaController {
    private final QnaService QnaService;

    @GetMapping("/qnaList")
    public String qnaList_form(Model model){
        List<QnaDto> qnaList = QnaService.getQnaList();
        model.addAttribute("qnaList", qnaList);
        log.info("page_move: qnaList.jsp");
        return "qnaList";
    }

    @GetMapping("/qnaWrite")
    public String qnaWrite_form(){

        log.info("page_move: qnaWrite.jsp");
        return "qnaWrite";
    }

    @GetMapping("/qnaDetail")
    public String QnaDetail_form(@RequestParam("qna_id") int qna_id, Model model) {
        QnaDto qnaDetail = QnaService.getQnaById(qna_id);
        model.addAttribute("qnaDetail", qnaDetail);
        log.info("page_move: qnaDetail.jsp");
        return "qnaDetail";
    }


    @PostMapping("/qnaWriteProcess")
    public String qnaWrite(Model model, @ModelAttribute QnaDto QnaDto) {
        QnaDto.setQna_created_at(LocalDateTime.now());
        int qnaResult = QnaService.qna(QnaDto);
        List<QnaDto> qnaList = QnaService.getQnaList();
        model.addAttribute("qnaList", qnaList);
        if (qnaResult > 0) {
            return "redirect:/qnaList";
        } else {
            return "qnaWrite";
        }
    }

}
