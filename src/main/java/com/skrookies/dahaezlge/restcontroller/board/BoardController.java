package com.skrookies.dahaezlge.restcontroller.board;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import com.skrookies.dahaezlge.restcontroller.board.dto.*;
import com.skrookies.dahaezlge.restcontroller.util.dto.StatusDto;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
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
@RequestMapping(value = "/board", produces = "application/json")
@RequiredArgsConstructor
public class BoardController {

    private final QnaService qnaService;
    private final XssFilterService xssFilterService;
    private final SqlFilterService sqlFilterService;

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

        log.info("Android Qna Modify 실행");
        log.info("변경 파일 수령 현황: {}", qnaModifyDto.getQna_file());

        QnaDto existingQna = qnaService.getQnaById(Math.toIntExact(qnaModifyDto.getQna_id()));

        /** 파일 변경 시 작동 */
        if(qnaModifyDto.getQna_file() != null) {
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
                String uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "uploads").toString();
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
                qnaDto.setQna_title(sqlFilterService.filter(xssFilterService.filter(qnaModifyDto.getTitle())));
                qnaDto.setQna_body(sqlFilterService.filter(xssFilterService.filter(qnaModifyDto.getContent())));
                qnaDto.setSecret(qnaModifyDto.getSecret());
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
        /** 변경할 파일이 없을 시 */
        else{
            // Qna 게시글 수정 정보 설정
            QnaDto qnaDto = new QnaDto();

            qnaDto.setQna_id(qnaModifyDto.getQna_id());
            qnaDto.setQna_title(sqlFilterService.filter(xssFilterService.filter(qnaModifyDto.getTitle())));
            qnaDto.setQna_body(sqlFilterService.filter(xssFilterService.filter(qnaModifyDto.getContent())));
            qnaDto.setSecret(qnaModifyDto.getSecret());
            qnaDto.setQna_user_id(existingQna.getQna_user_id());
            qnaDto.setQna_created_at(LocalDateTime.now());

            StatusDto statusDto = new StatusDto();
            statusDto.setStatus(qnaService.qnaUpdate2(qnaDto) > 0);

            return ResponseEntity.ok()
                    .body(statusDto);
        }
    }


    @PostMapping("/qna/delete")
    public ResponseEntity<StatusDto> deleteQna(@RequestBody QnaIDDto qnaIDDto) {

        log.info("qna 게시글 삭제 시도");

        QnaDto existingQna = qnaService.getQnaById(Integer.parseInt(qnaIDDto.getQna_id()));
        String uploadDir = "src/main/webapp/uploads";

        log.info("게시글에 업로드된 파일 존재 현황 {}", existingQna.getQna_file());

        if (existingQna.getNew_file_name() != null) {
            try {
                Path oldFilePath = Paths.get(uploadDir, existingQna.getNew_file_name());
                Files.deleteIfExists(oldFilePath);
                qnaService.deleteQna(Integer.parseInt(qnaIDDto.getQna_id()));

                log.info("qna 게시글 삭제 성공");

                StatusDto statusDto = new StatusDto();
                statusDto.setStatus(true);

                return ResponseEntity.ok()
                        .body(statusDto);
            }
            catch (IOException e) {
                log.error(e.getMessage(), e);

                StatusDto statusDto = new StatusDto();
                statusDto.setStatus(false);

                return ResponseEntity.ok()
                        .body(statusDto);
            }
        }
        else{
            qnaService.deleteQna(Integer.parseInt(qnaIDDto.getQna_id()));

            StatusDto statusDto = new StatusDto();
            statusDto.setStatus(true);

            return ResponseEntity.ok()
                    .body(statusDto);
        }
    }


    @PostMapping("/qna/write")
    public ResponseEntity<StatusDto> writeQna(@RequestBody QnaWriteDto qnaWriteDto) {
        log.info("Android Qna Write 실행");

        boolean write_result = qnaService.qnaWriteTry(qnaWriteDto.getWriter());

        log.info("게시글 작성 제한 여부: {}", write_result);

        if(write_result) {
            log.info("업로드 파일 수령 현황: {}", qnaWriteDto.getQna_file());

            /** 파일 변경 시 작동 */
            if (qnaWriteDto.getQna_file() != null) {
                String fileName = StringUtils.cleanPath(qnaWriteDto.getQna_file().getOriginalFilename());
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
                    String uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "uploads").toString();
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    // 새 파일 저장
                    Path filePath = Paths.get(uploadDir, newFileName);
                    qnaWriteDto.getQna_file().transferTo(filePath.toFile());

                    // Qna 게시글 수정 정보 설정
                    QnaDto qnaDto = new QnaDto();

                    qnaDto.setQna_title(sqlFilterService.filter(xssFilterService.filter(qnaWriteDto.getTitle())));
                    qnaDto.setQna_body(sqlFilterService.filter(xssFilterService.filter(qnaWriteDto.getContent())));
                    qnaDto.setQna_user_id(qnaWriteDto.getWriter());
                    qnaDto.setSecret(qnaWriteDto.getSecret());
                    qnaDto.setQna_created_at(LocalDateTime.parse(timestamp));
                    qnaDto.setQna_file(qnaWriteDto.getQna_file());

                    qnaDto.setFile_name(fileName);
                    qnaDto.setNew_file_name(newFileName);
                    qnaDto.setFile_path(filePath.toString());
                    qnaDto.setFile_size(qnaDto.getQna_file().getSize());

                    StatusDto statusDto = new StatusDto();
                    statusDto.setStatus(qnaService.qna(qnaDto) > 0);

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
            /** 업로드할 파일이 없을 시 */
            else {
                // Qna 게시글 수정 정보 설정
                QnaDto qnaDto = new QnaDto();

                qnaDto.setQna_title(sqlFilterService.filter(xssFilterService.filter(qnaWriteDto.getTitle())));
                qnaDto.setQna_body(sqlFilterService.filter(xssFilterService.filter(qnaWriteDto.getContent())));
                qnaDto.setQna_user_id(qnaWriteDto.getWriter());
                qnaDto.setSecret(qnaWriteDto.getSecret());
                qnaDto.setQna_created_at(LocalDateTime.now());
                qnaDto.setQna_file(qnaWriteDto.getQna_file());

                // 파일 정보가 없는 경우 기본값 설정
                qnaDto.setFile_name(null);
                qnaDto.setFile_path(null);
                qnaDto.setFile_size(0L);
                qnaDto.setNew_file_name(null);

                StatusDto statusDto = new StatusDto();
                statusDto.setStatus(qnaService.qna(qnaDto) > 0);

                return ResponseEntity.ok()
                        .body(statusDto);
            }
        }
        else{
            StatusDto statusDto = new StatusDto();
            statusDto.setStatus(false);

            return ResponseEntity.ok()
                    .body(statusDto);
        }

    }


    /** qna 댓글 작성 */
    @PostMapping("/qna/comment")
    public ResponseEntity<StatusDto> writeComment(@RequestBody CommentWriteDto commentWriteDto) {

        QnaReDto qnaReDto = new QnaReDto();

        qnaReDto.setQna_id(commentWriteDto.getQna_id());
        qnaReDto.setQna_re_user_id(commentWriteDto.getWriter());
        qnaReDto.setQna_re_body(commentWriteDto.getContent());
        qnaReDto.setQna_re_created_at(LocalDateTime.now());

        StatusDto statusDto = new StatusDto();
        statusDto.setStatus(qnaService.addQnaReply(qnaReDto) > 0);

        return ResponseEntity.ok()
                .body(statusDto);
    }
}