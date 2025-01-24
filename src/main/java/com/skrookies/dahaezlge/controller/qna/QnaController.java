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
    public String qnaList_form(
            @RequestParam(defaultValue = "1") int page, // 현재 페이지 (기본값: 1)
            Model model) {
        int pageSize = 10; // 한 페이지에 표시할 게시글 수
        int totalQnas = QnaService.getTotalQnas(); // 전체 게시글 수
        int totalPages = (int) Math.ceil((double) totalQnas / pageSize);

        // 현재 페이지에 해당하는 QnA 목록 가져오기
        List<QnaDto> qnaList = QnaService.getQnasByPage(page, pageSize);

        // 페이징 범위 계산
        int maxPagesToShow = 5;
        int startPage = Math.max(1, page - maxPagesToShow / 2);
        int endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);
        startPage = Math.max(1, endPage - maxPagesToShow + 1);

        // 모델에 데이터 추가
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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

    @GetMapping("/qnaEdit")
    public String qnaEdit_form(@RequestParam("qna_id") int qna_id, Model model) {
        QnaDto qnaDetail = QnaService.getQnaById(qna_id);
        model.addAttribute("qnaDetail", qnaDetail);
        log.info("page_move: qnaEdit.jsp");
        return "qnaEdit";
    }

    @PostMapping("/qnaWriteProcess")
    public String qnaWrite(Model model, @ModelAttribute QnaDto QnaDto) {
        QnaDto.setQna_created_at(LocalDateTime.now());
        int qnaResult = QnaService.qna(QnaDto);
        if (qnaResult > 0) {
            return "redirect:/qnaList";
        } else {
            return "qnaWrite";
        }
    }

    @PostMapping("/qnaUpdateProcess")
    public String qnaUpdate_form(Model model, @ModelAttribute QnaDto QnaDto) {
        QnaDto.setQna_created_at(LocalDateTime.now());
        int qnaResult = QnaService.qnaUpdate(QnaDto);
        if (qnaResult > 0) {
            return "redirect:/qnaList";
        } else {
            return "qnaEdit";
        }
    }

    @GetMapping("/qnaDelete")
    public String qnaDelete_form(@RequestParam("qna_id") int qna_id){
        QnaService.deleteQna(qna_id);
        return "redirect:/qnaList";
    }



}
