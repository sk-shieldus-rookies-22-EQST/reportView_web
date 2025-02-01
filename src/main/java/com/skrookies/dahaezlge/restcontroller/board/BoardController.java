package com.skrookies.dahaezlge.restcontroller.board;

import com.skrookies.dahaezlge.restcontroller.board.dto.QnaDetailDto;
import com.skrookies.dahaezlge.restcontroller.board.dto.QnaListCapDto;
import com.skrookies.dahaezlge.restcontroller.board.dto.QnaListDto;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
import com.skrookies.dahaezlge.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final QnaService qnaService;

    /** qna 게시글 전체 반환 */
    @PostMapping("/qna")
    public ResponseEntity<QnaListCapDto> getQnaList() {

        List<QnaListDto> qnaList = qnaService.getAllQnaList();

        QnaListCapDto qnaListCapDto = new QnaListCapDto(qnaList);

        return ResponseEntity.ok()
                .body(qnaListCapDto);
    }

    /** qna 상세 페이지 데이터 반환
     * 댓글 정보 포함 */
    @GetMapping("/qna/{qna_id}")
    public ResponseEntity<QnaDetailDto> getQnaDetails(@PathVariable String qna_id) {

        QnaDetailDto qnaDetailDto = qnaService.getQnaDetailById(Integer.parseInt(qna_id));

        return ResponseEntity.ok()
                .body(qnaDetailDto);
    }

    @PostMapping("/qna/write")
    public ResponseEntity<StatusDto> writeQna(@RequestBody Map<String, Object> qnaData) {
        String title = (String) qnaData.get("title");
        String content = (String) qnaData.get("content");
        String userId = (String) qnaData.get("userid");

        // Add logic to handle QnA writing
        boolean isSuccessful = true; // Replace with actual result

        return ResponseEntity.ok(new StatusDto(isSuccessful));
    }

    @PostMapping("/qna/comment")
    public ResponseEntity<StatusDto> writeComment(@RequestBody Map<String, Object> commentData) {
        int qnaId = (int) commentData.get("qnaid");
        String comment = (String) commentData.get("comment");

        // Add logic to handle comment writing
        boolean isSuccessful = true; // Replace with actual result

        return ResponseEntity.ok(new StatusDto(isSuccessful));
    }
}