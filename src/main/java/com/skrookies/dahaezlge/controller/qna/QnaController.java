package com.skrookies.dahaezlge.controller.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import com.skrookies.dahaezlge.repository.qnaRe.QnaReRepository;
import com.skrookies.dahaezlge.service.qna.QnaService;
import com.skrookies.dahaezlge.service.qnaRe.QnaReService;
import jakarta.servlet.http.HttpSession;
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
    private final QnaReService QnaReService;

    @GetMapping("/qnaList")
    public String qnaList_form(@RequestParam(defaultValue = "1") int page, Model model) {
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
    public String qnaWrite_form(HttpSession session) {
        // 세션에서 user_id 확인
        String userId = (String) session.getAttribute("user_id");

        if (userId == null || userId.isEmpty()) {
            // 세션에 user_id가 없으면 loginForm으로 리다이렉트
            return "redirect:/loginForm";
        }

        log.info("page_move: qnaWrite.jsp");
        return "qnaWrite";
    }

    @GetMapping("/qnaDetail")
    public String QnaDetail_form(HttpSession session, @RequestParam("qna_re_id") int qna_re_id,@RequestParam("qna_id") int qna_id, Model model) {
        // 세션에서 user_id를 가져옵니다.
        String userId = (String) session.getAttribute("user_id");

        // qna_user_id는 세션에서 가져온 userId로 설정합니다.
        QnaDto qnaDto = new QnaDto();
        qnaDto.setQna_user_id(userId); // 세션에서 가져온 user_id를 설정

        // QnaService에서 qna 정보를 가져옵니다.
        QnaDto qnaDetail = QnaService.getQnaById(qna_id);
        List<QnaReDto> replies = QnaReService.getRepliesByQnaId((long) qna_re_id);

        model.addAttribute("qnaDetail", qnaDetail);
        model.addAttribute("replies", replies);

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
    public String qnaWrite(HttpSession session, Model model, @ModelAttribute QnaDto qnaDto) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("user_id");

        if (userId == null || userId.isEmpty()) {
            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
            return "redirect:/loginForm";
        }

        // 사용자 ID와 작성 시간 설정
        qnaDto.setQna_user_id(userId);
        qnaDto.setQna_created_at(LocalDateTime.now());

        // QnA 저장 처리
        int qnaResult = QnaService.qna(qnaDto);

        if (qnaResult > 0) {
            return "redirect:/qnaList"; // 성공 시 목록 페이지로 이동
        } else {
            model.addAttribute("errorMessage", "글 작성에 실패했습니다.");
            return "qnaWrite"; // 실패 시 작성 페이지로 이동
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

    @GetMapping("/qnaSearch")
    public String searchQnaList(@RequestParam("keyword") String keyword, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        List<QnaDto> qnaList = QnaService.searchQnaByKeyword(keyword, page);
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "qnaList";
    }

    @PostMapping("/qnaReply")
    public String saveReply(HttpSession session, @RequestParam("qna_id") Long qnaId, @RequestParam("qna_re_body") String replyBody) {
        String userId = (String) session.getAttribute("user_id");

        QnaReDto qnaReDto = new QnaReDto();
        qnaReDto.setQna_re_user_id(userId);
        qnaReDto.setQna_re_body(replyBody);
        qnaReDto.setQna_re_created_at(LocalDateTime.now());

        QnaReService.saveReply(qnaReDto);
        return "redirect:/qnaDetail?qna_id=" + qnaId;
    }

}
