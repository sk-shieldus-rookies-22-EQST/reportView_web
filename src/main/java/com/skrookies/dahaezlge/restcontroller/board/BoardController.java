package com.skrookies.dahaezlge.restcontroller.board;

import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
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

    @GetMapping("/qna")
    public ResponseEntity<Map<String, Object>> getQnaList() {
        // Replace with logic to fetch QnA list
        List<Map<String, Object>> qnaList = List.of(
                Map.of("id", 1, "title", "1", "username", "작성자")
        );
        return ResponseEntity.ok(Map.of("qna", qnaList));
    }

    @GetMapping("/qna/{qnaID}")
    public ResponseEntity<Map<String, Object>> getQnaDetails(@PathVariable int qnaID) {
        // Replace with logic to fetch QnA details by ID
        Map<String, Object> qnaDetails = Map.of(
                "id", qnaID,
                "title", "1",
                "content", "11111",
                "comment", "답변"
        );
        return ResponseEntity.ok(qnaDetails);
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