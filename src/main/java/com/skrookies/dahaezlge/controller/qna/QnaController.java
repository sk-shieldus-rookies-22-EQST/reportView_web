package com.skrookies.dahaezlge.controller.qna;

import com.skrookies.dahaezlge.controller.qna.Dto.QnaDto;
import com.skrookies.dahaezlge.controller.qna.Dto.QnaReDto;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.common.DownFilterService;
import com.skrookies.dahaezlge.service.qna.QnaService;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
@Slf4j
public class QnaController {
    private final QnaService QnaService;
    private final XssFilterService xssFilterService;
    private final SqlFilterService sqlFilterService;
    private final DownFilterService downFilterService;
    private final QnaService qnaService;

    /**qna 게시판 글 목록 */
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

    /**qna 게시판 글 작성 */
    @GetMapping("/qnaWrite")
    public String qnaWrite_form(HttpSession session) {
        // 세션에서 user_id 확인
        String userId = (String) session.getAttribute("user_id");

        // 세션에 user_id가 없으면 loginForm으로 리다이렉트
        if (userId == null || userId.isEmpty()) {

            return "redirect:/loginForm";
        }
        // 10분동안 게시글 5개 미만 작성 시 게시글 작성 가능
        else if(!qnaService.qnaWriteTry(userId)){
            session.setAttribute("errorMessage", "잠시후 다시 작성해 주세요.");
            return "redirect:/qnaList";
        }

        log.info("page_move: qnaWrite.jsp");
        return "qnaWrite";
    }

    /**qna 게시판 글 상세 페이지 */
    @GetMapping("/qnaDetail")
    public String QnaDetail_form(HttpSession session, @RequestParam("qna_id") int qna_id, Model model) {
        // 세션에서 user_id를 가져옵니다.
        String userId = (String) session.getAttribute("user_id");
        Integer userLevel = (Integer) session.getAttribute("user_level");
        if (userLevel == null) {
            userLevel = 0;  // 기본값으로 0을 설정 (혹은 세션에 값을 아예 넣지 않도록 설정할 수 있음)
        }
        // qna_user_id는 세션에서 가져온 userId로 설정합니다.
        QnaDto qnaDto = new QnaDto();
        qnaDto.setQna_user_id(userId); // 세션에서 가져온 user_id를 설정

        // QnaService에서 qna 정보를 가져옵니다.
        QnaDto qnaDetail = QnaService.getQnaById(qna_id);
        List<QnaReDto> qnaReplies = QnaService.getRepliesByQnaId((long) qna_id);

        if(qnaDetail.getSecret() && (userLevel != 123 && !Objects.equals(qnaDetail.getQna_user_id(), userId))) {
            session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
            return "redirect:/qnaList";
        }

        model.addAttribute("qnaDetail", qnaDetail);
        model.addAttribute("qnaReplies", qnaReplies);

        log.info("page_move: qnaDetail.jsp");
        return "qnaDetail";
    }

    /**qna 게시판 글 수정 */
    @GetMapping("/qnaEdit")
    public String qnaEdit_form(HttpSession session, @RequestParam("qna_id") int qna_id, Model model) {

        String userId = (String) session.getAttribute("user_id");
        Integer userLevel = (Integer) session.getAttribute("user_level");

        if (userId == null || userId.isEmpty()) {
            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
            return "redirect:/loginForm";
        }

        QnaDto qnaDetail = QnaService.getQnaById(qna_id);

        if((userLevel != 123 && !Objects.equals(qnaDetail.getQna_user_id(), userId))) {
            session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
            return "redirect:/qnaList";
        }

        model.addAttribute("qnaDetail", qnaDetail);
        log.info("page_move: qnaEdit.jsp");
        return "qnaEdit";
    }

    /**qna 게시판 글 작성 프로세스 */
    @PostMapping("/qnaWriteProcess")
    public String qnaWrite(HttpSession session, Model model, @ModelAttribute QnaDto qnaDto) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("user_id");

        if (userId == null || userId.isEmpty()) {
            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
            return "redirect:/loginForm";
        }

        // 제목과 내용이 비어 있는 경우 예외 처리
        if (qnaDto.getQna_title() == null || qnaDto.getQna_title().trim().isEmpty()) {
            session.setAttribute("errorMessage", "제목을 적어주세요.");
            return "qnaWrite"; // 다시 작성 페이지로 이동
        }

        if (qnaDto.getQna_body() == null || qnaDto.getQna_body().trim().isEmpty()) {
            session.setAttribute("errorMessage", "내용을 적어주세요.");
            return "qnaWrite";
        }

        // 제목 길이 제한 (30자 이상일 경우 예외 처리)
        if (qnaDto.getQna_title().length() > 30) {
            session.setAttribute("errorMessage", "제목이 너무 깁니다! (최대 30자).");
            return "qnaWrite";
        }

        // 내용 길이 제한 (500자 이상일 경우 예외 처리)
        if (qnaDto.getQna_body().length() > 500) {
            session.setAttribute("errorMessage", "내용이 너무 많습니다! (최대 500자).");
            return "qnaWrite";
        }



        // SQL, XSS 필터링 적용
        String qna_title = xssFilterService.filter(qnaDto.getQna_title());
        qnaDto.setQna_title(sqlFilterService.filter(qna_title));

        /** XSS 공격 허용 */
        String qna_body = xssFilterService.filter(qnaDto.getQna_body());
        qnaDto.setQna_body(sqlFilterService.filter(qna_body));

        // 제목과 내용이 비어 있는 경우 예외 처리
        if (qnaDto.getQna_title() == null || qnaDto.getQna_title().trim().isEmpty()) {
            return "qnaWrite"; // 다시 작성 페이지로 이동
        }

        if (qnaDto.getQna_body() == null || qnaDto.getQna_body().trim().isEmpty()) {
            return "qnaWrite";
        }

        // 사용자 ID와 작성 시간 설정
        qnaDto.setQna_user_id(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        qnaDto.setQna_created_at(now);
        qnaDto.setFormattedCreatedAt(now.format(formatter));

        // 파일 처리
        if (qnaDto.getQna_file() != null && !qnaDto.getQna_file().isEmpty()) {
            String fileName = StringUtils.cleanPath(qnaDto.getQna_file().getOriginalFilename());
            String fileExtension = ""; // 파일 확장자 (예: .jpg, .png 등)
            String fileBaseName = fileName; // 확장자 없는 파일명

            // 확장자 분리
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileBaseName = fileName.substring(0, dotIndex);
                fileExtension = fileName.substring(dotIndex);
            }

            // **추가 검사: fileBaseName에 추가 '.'가 포함되어 있다면 이중 확장자로 판단**
            if (fileBaseName.contains(".")) {
                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
                return "qnaWrite";
            }

            // 화이트리스트 방식 확장자 체크: 마지막 확장자가 .jpg 또는 .png만 허용
            if (!fileExtension.equalsIgnoreCase(".jpg") && !fileExtension.equalsIgnoreCase(".png")) {
                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
                return "qnaWrite";
            }

            // 현재 시간을 파일명에 추가 (예: dog_20250203_205142.jpg)
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            //파일 업로드 취약점 이중 확장자 허용
            String newFileName = timestamp + "_" + fileBaseName + fileExtension;
            // 정상 코드
            //String newFileName = fileBaseName + "_" + timestamp + fileExtension;

            try {
                // 파일 저장 경로 설정 (REST 컨트롤러와 동일하게 고정 경로 사용)
                String uploadDir = Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "uploads").toString();

                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 파일 저장 경로 설정
                Path filePath = Paths.get(uploadDir, newFileName);
                qnaDto.getQna_file().transferTo(filePath.toFile());

                // 상대 경로 예시: "/uploads/dog_20250203_205142.jpg"
                String relativeFilePath = "/uploads/" + newFileName;

                // 파일 정보 설정
                qnaDto.setFile_name(fileName);         // 원본 파일명
                qnaDto.setNew_file_name(newFileName);    // 새 파일명 (타임스탬프 포함)
                qnaDto.setFile_path(relativeFilePath);   // 상대 경로 저장
                qnaDto.setFile_size(qnaDto.getQna_file().getSize());
            } catch (IOException e) {
                log.error("파일 업로드 실패", e);
                session.setAttribute("errorMessage", "파일 업로드 실패.");
                return "qnaWrite"; // 파일 업로드 실패 시 다시 작성 페이지로 이동
            }
        } else {
            log.info("업로드된 파일이 없음");
        }


        // QnA 저장 처리
        int qnaResult = QnaService.qna(qnaDto);

        if (qnaResult > 0) {
            return "redirect:/qnaList"; // 성공 시 목록 페이지로 이동
        } else {
            session.setAttribute("errorMessage", "글 작성에 실패했습니다.");
            return "qnaWrite"; // 실패 시 작성 페이지로 이동
        }
    }

    /**qna 게시판 글 수정 프로세스 */
    @PostMapping("/qnaUpdateProcess")
    public String qnaUpdate_form(Model model, @ModelAttribute QnaDto QnaDto, HttpSession session) {

        String userId = (String) session.getAttribute("user_id");

        if (userId == null || userId.isEmpty()) {
            return "redirect:/loginForm"; // 로그인 필요
        }

        // 제목과 내용이 비어 있는 경우 예외 처리
        if (QnaDto.getQna_title() == null || QnaDto.getQna_title().trim().isEmpty()) {
            session.setAttribute("errorMessage", "제목을 적어주세요.");
            return "qnaWrite"; // 다시 작성 페이지로 이동
        }

        if (QnaDto.getQna_body() == null || QnaDto.getQna_body().trim().isEmpty()) {
            session.setAttribute("errorMessage", "내용을 적어주세요.");
            return "qnaWrite";
        }

        // 제목 길이 제한 (30자 이상일 경우 예외 처리)
        if (QnaDto.getQna_title().length() > 30) {
            session.setAttribute("errorMessage", "제목이 너무 깁니다! (최대 30자).");
            return "qnaWrite";
        }

        // 내용 길이 제한 (500자 이상일 경우 예외 처리)
        if (QnaDto.getQna_body().length() > 500) {
            session.setAttribute("errorMessage", "내용이 너무 많습니다! (최대 500자).");
            return "qnaWrite";
        }

        // SQL, XSS 필터링 적용
        String qna_title = xssFilterService.filter(QnaDto.getQna_title());
        QnaDto.setQna_title(sqlFilterService.filter(qna_title));

        /** XSS 공격 허용 */
        String qna_body = xssFilterService.filter(QnaDto.getQna_body());
        QnaDto.setQna_body(sqlFilterService.filter(qna_body));

        // 필터링 되어 제목과 내용이 비어 있는 경우 예외 처리
        if (QnaDto.getQna_title() == null || QnaDto.getQna_title().trim().isEmpty()) {
            return "redirect:/qnaList";
        }

        if (QnaDto.getQna_body() == null || QnaDto.getQna_body().trim().isEmpty()) {
            return "redirect:/qnaList";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        QnaDto.setQna_created_at(now);
        QnaDto.setFormattedCreatedAt(now.format(formatter));

        // 기존 파일 정보 가져오기
        QnaDto existingQna = QnaService.getQnaById(Math.toIntExact(QnaDto.getQna_id()));

        // 파일 처리
        if (QnaDto.getQna_file() != null && !QnaDto.getQna_file().isEmpty()) {
            String fileName = StringUtils.cleanPath(QnaDto.getQna_file().getOriginalFilename());
            String fileExtension = ""; // 파일 확장자
            String fileBaseName = fileName; // 확장자 없는 파일명

            // 확장자 분리
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileBaseName = fileName.substring(0, dotIndex);
                fileExtension = fileName.substring(dotIndex);
            }

            // **추가 검사: fileBaseName에 추가 '.'가 포함되어 있다면 이중 확장자로 판단**
            if (fileBaseName.contains(".")) {
                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
                return "qnaWrite";
            }

            // 화이트리스트 방식 확장자 체크: 마지막 확장자가 .jpg 또는 .png 만 허용
            if (!fileExtension.equalsIgnoreCase(".jpg") && !fileExtension.equalsIgnoreCase(".png")) {
                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
                return "qnaWrite";
            }

            // 현재 시간을 파일명에 추가 (예: dog_20250203_205142.jpg)
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            // 파일 업로드 취약점 이중 확장자 허용
            String newFileName = timestamp + "_" + fileBaseName + fileExtension;
            // 정상 코드
            //String newFileName = fileBaseName + "_" + timestamp + fileExtension;

            try {
                // 파일 저장 경로 설정 (WebApp의 루트 경로 기준, 상대경로 사용)
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
                QnaDto.getQna_file().transferTo(filePath.toFile());

                // 상대 경로 저장 (예: "/uploads/dog_20250203_205142.jpg")
                String relativeFilePath = "/uploads/" + newFileName;

                // 파일 정보 설정
                QnaDto.setFile_name(fileName);
                QnaDto.setNew_file_name(newFileName);
                QnaDto.setFile_path(relativeFilePath);
                QnaDto.setFile_size(QnaDto.getQna_file().getSize());

                int qnaResult = QnaService.qnaUpdate(QnaDto);
                if (qnaResult > 0) {

                    return "redirect:/qnaDetail?qna_id=" + Math.toIntExact(QnaDto.getQna_id());
                } else {
                    return "qnaEdit";
                }
            } catch (IOException e) {
                log.error("파일 업로드 실패", e);
                session.setAttribute("errorMessage", "파일 업로드 실패");
                return "qnaEdit";
            }
        } else {
            // 기존 파일이 있는 경우 유지
            QnaDto.setFile_name(existingQna.getFile_name());
            QnaDto.setNew_file_name(existingQna.getNew_file_name());
            QnaDto.setFile_path(existingQna.getFile_path());
            QnaDto.setFile_size(existingQna.getFile_size());

            int qnaResult2 = QnaService.qnaUpdate2(QnaDto);

            if (qnaResult2 > 0) {
                log.info("qnaUpdateProcess");
                log.info(QnaDto.getQna_id().toString());
                String qnd_id = QnaDto.getQna_id().toString();
                return "redirect:/qnaDetail?qna_id=" + qnd_id;
            } else {
                return "qnaEdit";
            }
        }
    }

    /**qna 게시판 글 삭제 */
    @GetMapping("/qnaDelete")
    public String qnaDelete_form(@RequestParam("qna_id") int qna_id, @ModelAttribute QnaDto QnaDto, HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("user_id");
        Integer userLevel = (Integer) session.getAttribute("user_level");
        // 기존 파일 정보 가져오기
        QnaDto existingQna = QnaService.getQnaById(Math.toIntExact(QnaDto.getQna_id()));
        String uploadDir = session.getServletContext().getRealPath("/") + "uploads";
        if (existingQna.getNew_file_name() != null) {
            Path oldFilePath = Paths.get(uploadDir, existingQna.getNew_file_name());
            Files.deleteIfExists(oldFilePath);
        }
        if (userId == null || userId.isEmpty()) {
            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
            return "redirect:/loginForm";
        }
        QnaDto qnaDetail = QnaService.getQnaById(qna_id);

        if((userLevel != 123 && !Objects.equals(qnaDetail.getQna_user_id(), userId))) {
            session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
            return "redirect:/qnaList";
        }

        QnaService.deleteQna(qna_id);

        session.setAttribute("deleteQns", "글이 삭제되었습니다.");
        return "redirect:/qnaList";
    }

    /**qna 게시판 파일 다운로드 */
    @GetMapping("/download")
    public String downloadFile(@RequestParam("file_name") String fileName,
                               @RequestParam("file_path") String filePath,
                               HttpServletResponse response, HttpSession session) {
        try {
            String userId = (String) session.getAttribute("user_id");
            Integer userLevel = (Integer) session.getAttribute("user_level");

            if (userId == null || userId.isEmpty()) {
                // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
                return "redirect:/loginForm";
            }
            if(userLevel != 123) {
                session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
                return "redirect:/qnaList";
            }

            // 파일 경로 필터링
            filePath = downFilterService.filter(filePath);
            // 파일이 저장된 절대 경로 (예: /uploads/dog_20250203_205142.jpg)
            String uploadDir = "src/main/webapp";
            File file = new File(uploadDir + filePath);

            log.info(Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "uploads").toString());

            if (!file.exists()) {
                session.setAttribute("errorMessage", "파일 다운 실패.");
                // 파일이 존재하지 않으면 리다이렉트
                return "redirect:/qnaList";
            }

            // 다운로드 응답 헤더 설정
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + URLEncoder.encode(fileName, "UTF-8").replace("+", "%20") + "\"");

            response.setContentLengthLong(file.length());

            // 파일 스트림 전송
            try (InputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
            // 다운로드가 완료되었으므로 추가 뷰 반환은 필요 없음
            return null;
        } catch (Exception e) {
            log.error("파일 다운로드 실패: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }


    /**qna 게시판 글 검색*/
    @GetMapping("/qnaSearch")
    public String searchQnaList(@RequestParam("keyword") String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                Model model) {
        keyword = xssFilterService.filter(keyword);
        keyword = sqlFilterService.filter(keyword);


        int pageSize = 10;
        List<QnaDto> qnaList = QnaService.searchQnaByKeyword(keyword, page);

        // 검색된 게시글 수 (필터링된 결과의 총 개수)
        int totalSearchResults = QnaService.getTotalQnasByKeyword(keyword);
        int totalPages = (int) Math.ceil((double) totalSearchResults / pageSize);

        // 페이지 네비게이션에 필요한 정보 추가
        int startPage = Math.max(1, page - 2);
        int endPage = Math.min(totalPages, page + 2);

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "qnaList";
    }

    /**qna 게시판 댓글 삭제 */
    @RequestMapping("/qnaReplyDelete")
    public String qnaReplyDelete(@RequestParam("qna_re_id") Long qna_re_id, @RequestParam("qna_id") Long qna_id, HttpSession session) {
        try {
            String userId = (String) session.getAttribute("user_id");
            Integer userLevel = (Integer) session.getAttribute("user_level");

            if (userId == null || userId.isEmpty()) {
                // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
                return "redirect:/loginForm";
            }
            if(userLevel != 123) {
                session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
                return "redirect:/qnaList";
            }

            // 해당 답글을 삭제
            QnaService.deleteById(qna_re_id);
            session.setAttribute("messagedeleteReply", "댓글이 삭제되었습니다.");
        } catch (Exception e) {
            // 예외 처리 (삭제 실패 시)
            e.printStackTrace();
        }

        // 삭제 후 같은 페이지로 리다이렉트 (게시글 상세 페이지로 돌아가기)
        return "redirect:/qnaDetail?qna_id=" + qna_id;
    }

    /**qna 게시판 댓글 작성 프로세스 */
    @PostMapping("/qnaReplyProcess")
    public String qnaReplyProcess(Model model,HttpSession session, @RequestParam("qna_id") int qna_id, @RequestParam("qna_re_body") String qna_re_body) {
        // 세션에서 user_id 확인
        String userId = (String) session.getAttribute("user_id");
        Integer userLevel = (Integer) session.getAttribute("user_level");

        if (userId == null || userId.isEmpty()) {
            // 세션에 user_id가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/loginForm";
        }

        if(userLevel != 123) {
            session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
            return "redirect:/qnaList";
        }

        // 댓글 내용이 비어 있는지 먼저 확인
        if (qna_re_body == null || qna_re_body.trim().isEmpty()) {
            session.setAttribute("errorMessage", "댓글을 작성해주세요.");
            return "redirect:/qnaDetail?qna_id=" + qna_id; // 다시 작성 페이지로 이동
        }

        // 댓글 길이 제한 (500자 이상일 경우 예외 처리)
        if (qna_re_body.length() > 200) {
            session.setAttribute("errorMessage", "내용이 너무 많습니다! (최대 200자).");
            return "qnaWrite";
        }

        // XSS 필터링 적용
        String filteredBody = xssFilterService.filter(qna_re_body);
        filteredBody = sqlFilterService.filter(filteredBody);

        // 필터링 후에도 비어 있으면 처리
        if (filteredBody == null || filteredBody.trim().isEmpty()) {
            return "redirect:/qnaDetail?qna_id=" + qna_id; // 필터링 후 내용이 없다면 다시 작성 페이지로 이동
        }

        // 답글 DTO 생성
        QnaReDto qnaReDto = new QnaReDto();
        qnaReDto.setQna_re_user_id(userId);
        qnaReDto.setQna_re_body(filteredBody);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        qnaReDto.setQna_re_created_at(now);
        qnaReDto.setFormattedCreatedAt(now.format(formatter));
        qnaReDto.setQna_id((long) qna_id);

        // 서비스 호출하여 답글 추가
        int result = QnaService.addQnaReply(qnaReDto);

        if (result > 0) {
            return "redirect:/qnaDetail?qna_id=" + qna_id; // 답글 추가 후 상세 페이지로 리다이렉트
        } else {
            // 실패 시 처리 (예: 오류 메시지 처리)
            return "redirect:/qnaDetail?qna_id=" + qna_id; // 실패 시에도 상세 페이지로 리다이렉트
        }



    }

}
