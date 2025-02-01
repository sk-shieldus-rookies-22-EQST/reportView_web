package com.skrookies.dahaezlge.restcontroller.board;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.restcontroller.board.dto.*;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
import com.skrookies.dahaezlge.service.qna.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    /** web과의 작동 동일성을 위해 file 처리를 Controller에 작성 */
    @PostMapping("qna/modify")
    public ResponseEntity<StatusDto> modifyQna(@RequestBody QnaModifyDto qnaModifyDto){

        QnaDto existingQna = qnaService.getQnaById(Math.toIntExact(qnaModifyDto.getQna_id()));

        String fileName = StringUtils.cleanPath(qnaModifyDto.getQna_file().getOriginalFilename());
        String fileExtension = ""; // 파일 확장자 (예: .pdf)
        String fileBaseName = fileName; // 확장자 없는 파일명

        // 확장자 분리
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileBaseName = fileName.substring(0, dotIndex);
            fileExtension = fileName.substring(dotIndex);
        }

        // 현재 시간을 파일명에 추가
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String newFileName = fileBaseName + "_" + timestamp + fileExtension;

        try {
            // 파일 저장 경로 설정
            String uploadDir = "src/main/webapp/uploads";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 기존 파일이 있다면 삭제
            if (existingQna.getNew_file_name() != null) {
                Path oldFilePath = Paths.get(uploadDir, existingQna.getNew_file_name());
                Files.deleteIfExists(oldFilePath);
            }

            // 새 파일 저장
            Path filePath = Paths.get(uploadDir, newFileName);
            qnaModifyDto.getQna_file().transferTo(filePath.toFile());

            // Qna 게시글 수정 정보 설정
            QnaDto qnaDto = new QnaDto();

            qnaDto.setQna_id(qnaModifyDto.getQna_id());
            qnaDto.setQna_title(qnaModifyDto.getTitle());
            qnaDto.setQna_body(qnaModifyDto.getContent());
            qnaDto.setQna_user_id(existingQna.getQna_user_id());
            qnaDto.setQna_created_at(LocalDateTime.parse(timestamp));
            qnaDto.setQna_file(qnaModifyDto.getQna_file());

            qnaDto.setFile_name(fileName);
            qnaDto.setNew_file_name(newFileName);
            qnaDto.setFile_path(filePath.toString());
            qnaDto.setFile_size(qnaDto.getQna_file().getSize());

            StatusDto statusDto = new StatusDto();
            statusDto.setStatus(qnaService.qnaUpdate(qnaDto) > 0);

            return ResponseEntity.ok()
                    .body(statusDto);

        } catch (IOException e) {
            log.error("파일 업로드 실패", e);

            StatusDto statusDto = new StatusDto();
            statusDto.setStatus(false);

            return ResponseEntity.ok()
                    .body(statusDto);
        }
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