package com.skrookies.dahaezlge.controller.notice;

import com.skrookies.dahaezlge.controller.notice.Dto.NoticeDto;
import com.skrookies.dahaezlge.service.common.DownFilterService;
import com.skrookies.dahaezlge.service.common.SqlFilterService;
import com.skrookies.dahaezlge.service.common.XssFilterService;
import com.skrookies.dahaezlge.service.notice.NoticeService;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class NoticeController {
    private final XssFilterService xssFilterService;
    private final SqlFilterService sqlFilterService;
    private final DownFilterService downFilterService;
    private final NoticeService noticeService;
    
    /**notice 게시판 글 목록 */
    @GetMapping("/noticeList")
    public String noticeList_form(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10; // 한 페이지에 표시할 게시글 수
        int totalnotices = noticeService.getTotalNotices(); // 전체 게시글 수
        int totalPages = (int) Math.ceil((double) totalnotices / pageSize);

        // 현재 페이지에 해당하는 notice 목록 가져오기
        List<NoticeDto> noticeList = noticeService.getNoticesByPage(page, pageSize);

        // 페이징 범위 계산
        int maxPagesToShow = 5;
        int startPage = Math.max(1, page - maxPagesToShow / 2);
        int endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);
        startPage = Math.max(1, endPage - maxPagesToShow + 1);

        // 모델에 데이터 추가
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "noticeList";
    }

    /**notice 게시판 글 작성 */
    @GetMapping("/admin/noticeWrite")
    public String noticeWrite_form(HttpSession session) {
        // 세션에서 user_id 확인
        String userId = (String) session.getAttribute("user_id");

//        // 세션에 user_id가 없으면 loginForm으로 리다이렉트
//        if (userId == null || userId.isEmpty()) {
//
//            return "redirect:/loginForm";
//        }
//        // 10분동안 게시글 5개 미만 작성 시 게시글 작성 가능
//        else if(!noticeService.noticeWriteTry(userId)){
//
//            return "redirect:/noticeList";
//        }

        log.info("page_move: noticeWrite.jsp");
        return "noticeWrite";
    }

    /**notice 게시판 글 상세 페이지 */
    @GetMapping("/noticeDetail")
    public String noticeDetail_form(HttpSession session, @RequestParam("notice_id") int notice_id, Model model) {
        // 세션에서 user_id를 가져옵니다.
        String userId = (String) session.getAttribute("user_id");
        Integer userLevel = (Integer) session.getAttribute("user_level");
        if (userLevel == null) {
            userLevel = 0;  // 기본값으로 0을 설정 (혹은 세션에 값을 아예 넣지 않도록 설정할 수 있음)
        }
        // notice_user_id는 세션에서 가져온 userId로 설정합니다.
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNotice_user_id(userId); // 세션에서 가져온 user_id를 설정

        // noticeService에서 notice 정보를 가져옵니다.
        NoticeDto noticeDetail = noticeService.getNoticeById(notice_id);

//        if(noticeDetail.getSecret() && (userLevel != 123 && !Objects.equals(noticeDetail.getnotice_user_id(), userId))) {
//            session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
//            return "redirect:/noticeList";
//        }

        model.addAttribute("noticeDetail", noticeDetail);

        log.info("page_move: noticeDetail.jsp");
        return "noticeDetail";
    }

    /**notice 게시판 글 수정 */
    @GetMapping("/admin/noticeEdit")
    public String noticeEdit_form(HttpSession session, @RequestParam("notice_id") int notice_id, Model model) {

        String userId = (String) session.getAttribute("user_id");
        Integer userLevel = (Integer) session.getAttribute("user_level");

//        if (userId == null || userId.isEmpty()) {
//            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
//            return "redirect:/loginForm";
//        }

        NoticeDto noticeDetail = noticeService.getNoticeById(notice_id);

//        if((userLevel != 123 && !Objects.equals(noticeDetail.getNotice_user_id(), userId))) {
//            session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
//            return "redirect:/noticeList";
//        }

        model.addAttribute("noticeDetail", noticeDetail);
        log.info("page_move: noticeEdit.jsp");
        return "noticeEdit";
    }

    /**notice 게시판 글 작성 프로세스 */
    @PostMapping("/admin/noticeWriteProcess")
    public String noticeWrite(HttpSession session, Model model, @ModelAttribute NoticeDto noticeDto) {
        // 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("user_id");

//        if (userId == null || userId.isEmpty()) {
//            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
//            return "redirect:/loginForm";
//        }

        // 제목과 내용이 비어 있는 경우 예외 처리
        if (noticeDto.getNotice_title() == null || noticeDto.getNotice_title().trim().isEmpty()) {
            session.setAttribute("errorMessage", "제목을 적어주세요.");
            return "noticeWrite"; // 다시 작성 페이지로 이동
        }

        if (noticeDto.getNotice_body() == null || noticeDto.getNotice_body().trim().isEmpty()) {
            session.setAttribute("errorMessage", "내용을 적어주세요.");
            return "noticeWrite";
        }

        // 제목 길이 제한 (100자 이상일 경우 예외 처리)
        if (noticeDto.getNotice_title().length() > 100) {
            session.setAttribute("errorMessage", "제목이 너무 깁니다! (최대 100자).");
            return "noticeWrite";
        }

        // 내용 길이 제한 (500자 이상일 경우 예외 처리)
        if (noticeDto.getNotice_body().length() > 5000) {
            session.setAttribute("errorMessage", "내용이 너무 많습니다! (최대 5000자).");
            return "noticeWrite";
        }



        // SQL, XSS 필터링 적용
        String notice_title = xssFilterService.filter(noticeDto.getNotice_title());
        noticeDto.setNotice_title(sqlFilterService.filter(notice_title));

        /** XSS 공격 허용 */
        String notice_body = xssFilterService.filter(noticeDto.getNotice_body());
        noticeDto.setNotice_body(sqlFilterService.filter(notice_body));

        // 제목과 내용이 비어 있는 경우 예외 처리
        if (noticeDto.getNotice_title() == null || noticeDto.getNotice_title().trim().isEmpty()) {
            return "noticeWrite"; // 다시 작성 페이지로 이동
        }

        if (noticeDto.getNotice_body() == null || noticeDto.getNotice_body().trim().isEmpty()) {
            return "noticeWrite";
        }

        // 사용자 ID와 작성 시간 설정
        noticeDto.setNotice_user_id(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        noticeDto.setNotice_created_at(now);
        noticeDto.setFormattedCreatedAt(now.format(formatter));

        // 파일 처리
        if (noticeDto.getNotice_file() != null && !noticeDto.getNotice_file().isEmpty()) {
            String fileName = StringUtils.cleanPath(noticeDto.getNotice_file().getOriginalFilename());
            String fileExtension = ""; // 파일 확장자 (예: .jpg, .png 등)
            String fileBaseName = fileName; // 확장자 없는 파일명

            // 확장자 분리
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileBaseName = fileName.substring(0, dotIndex);
                fileExtension = fileName.substring(dotIndex);
            }

//            // **추가 검사: fileBaseName에 추가 '.'가 포함되어 있다면 이중 확장자로 판단**
//            if (fileBaseName.contains(".")) {
//                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
//                return "noticeWrite";
//            }
//
//            // 화이트리스트 방식 확장자 체크: 마지막 확장자가 .jpg 또는 .png만 허용
//            if (!fileExtension.equalsIgnoreCase(".jpg") && !fileExtension.equalsIgnoreCase(".png")) {
//                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
//                return "noticeWrite";
//            }

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
                noticeDto.getNotice_file().transferTo(filePath.toFile());

                // 상대 경로 예시: "/uploads/dog_20250203_205142.jpg"
                String relativeFilePath = "/uploads/" + newFileName;

                // 파일 정보 설정
                noticeDto.setFile_name(fileName);         // 원본 파일명
                noticeDto.setNew_file_name(newFileName);    // 새 파일명 (타임스탬프 포함)
                noticeDto.setFile_path(relativeFilePath);   // 상대 경로 저장
                noticeDto.setFile_size(noticeDto.getNotice_file().getSize());
            } catch (IOException e) {
                log.error("파일 업로드 실패", e);
                session.setAttribute("errorMessage", "파일 업로드 실패.");
                return "noticeWrite"; // 파일 업로드 실패 시 다시 작성 페이지로 이동
            }
        } else {
            log.info("업로드된 파일이 없음");
        }


        // notice 저장 처리
        int noticeResult = noticeService.notice(noticeDto);

        if (noticeResult > 0) {
            return "redirect:/noticeList"; // 성공 시 목록 페이지로 이동
        } else {
            session.setAttribute("errorMessage", "글 작성에 실패했습니다.");
            return "noticeWrite"; // 실패 시 작성 페이지로 이동
        }
    }

    /**notice 게시판 글 수정 프로세스 */
    @PostMapping("/admin/noticeUpdateProcess")
    public String noticeUpdate_form(Model model, @ModelAttribute NoticeDto noticeDto, HttpSession session) {

        String userId = (String) session.getAttribute("user_id");

//        if (userId == null || userId.isEmpty()) {
//            return "redirect:/loginForm"; // 로그인 필요
//        }

        // 제목과 내용이 비어 있는 경우 예외 처리
        if (noticeDto.getNotice_title() == null || noticeDto.getNotice_title().trim().isEmpty()) {
            session.setAttribute("errorMessage", "제목을 적어주세요.");
            return "noticeWrite"; // 다시 작성 페이지로 이동
        }

        if (noticeDto.getNotice_body() == null || noticeDto.getNotice_body().trim().isEmpty()) {
            session.setAttribute("errorMessage", "내용을 적어주세요.");
            return "noticeWrite";
        }

        // 제목 길이 제한 (100자 이상일 경우 예외 처리)
        if (noticeDto.getNotice_title().length() > 100) {
            session.setAttribute("errorMessage", "제목이 너무 깁니다! (최대 100자).");
            return "noticeWrite";
        }

        // 내용 길이 제한 (5000자 이상일 경우 예외 처리)
        if (noticeDto.getNotice_body().length() > 5000) {
            session.setAttribute("errorMessage", "내용이 너무 많습니다! (최대 5000자).");
            return "noticeWrite";
        }

        // SQL, XSS 필터링 적용
        String notice_title = xssFilterService.filter(noticeDto.getNotice_title());
        noticeDto.setNotice_title(sqlFilterService.filter(notice_title));

        /** XSS 공격 허용 */
        String notice_body = xssFilterService.filter(noticeDto.getNotice_body());
        noticeDto.setNotice_body(sqlFilterService.filter(notice_body));

        // 필터링 되어 제목과 내용이 비어 있는 경우 예외 처리
        if (noticeDto.getNotice_title() == null || noticeDto.getNotice_title().trim().isEmpty()) {
            return "redirect:/noticeList";
        }

        if (noticeDto.getNotice_body() == null || noticeDto.getNotice_body().trim().isEmpty()) {
            return "redirect:/noticeList";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        noticeDto.setNotice_created_at(now);
        noticeDto.setFormattedCreatedAt(now.format(formatter));

        // 기존 파일 정보 가져오기
        NoticeDto existingnotice = noticeService.getNoticeById(Math.toIntExact(noticeDto.getNotice_id()));

        // 파일 처리
        if (noticeDto.getNotice_file() != null && !noticeDto.getNotice_file().isEmpty()) {
            String fileName = StringUtils.cleanPath(noticeDto.getNotice_file().getOriginalFilename());
            String fileExtension = ""; // 파일 확장자
            String fileBaseName = fileName; // 확장자 없는 파일명

            // 확장자 분리
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileBaseName = fileName.substring(0, dotIndex);
                fileExtension = fileName.substring(dotIndex);
            }

//            // **추가 검사: fileBaseName에 추가 '.'가 포함되어 있다면 이중 확장자로 판단**
//            if (fileBaseName.contains(".")) {
//                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
//                return "noticeWrite";
//            }
//
//            // 화이트리스트 방식 확장자 체크: 마지막 확장자가 .jpg 또는 .png 만 허용
//            if (!fileExtension.equalsIgnoreCase(".jpg") && !fileExtension.equalsIgnoreCase(".png")) {
//                session.setAttribute("errorMessage", "jpg, png 방식 확장자만 업로드할 수 있습니다.");
//                return "noticeWrite";
//            }

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
                if (existingnotice.getNew_file_name() != null) {
                    Path oldFilePath = Paths.get(uploadDir, existingnotice.getNew_file_name());
                    Files.deleteIfExists(oldFilePath);
                }

                // 새 파일 저장
                Path filePath = Paths.get(uploadDir, newFileName);
                noticeDto.getNotice_file().transferTo(filePath.toFile());

                // 상대 경로 저장 (예: "/uploads/dog_20250203_205142.jpg")
                String relativeFilePath = "/uploads/" + newFileName;

                // 파일 정보 설정
                noticeDto.setFile_name(fileName);
                noticeDto.setNew_file_name(newFileName);
                noticeDto.setFile_path(relativeFilePath);
                noticeDto.setFile_size(noticeDto.getNotice_file().getSize());

                int noticeResult = noticeService.noticeUpdate(noticeDto);
                if (noticeResult > 0) {

                    return "redirect:/noticeDetail?notice_id=" + Math.toIntExact(noticeDto.getNotice_id());
                } else {
                    return "noticeEdit";
                }
            } catch (IOException e) {
                log.error("파일 업로드 실패", e);
                session.setAttribute("errorMessage", "파일 업로드 실패");
                return "noticeEdit";
            }
        } else {
            // 기존 파일이 있는 경우 유지
            noticeDto.setFile_name(existingnotice.getFile_name());
            noticeDto.setNew_file_name(existingnotice.getNew_file_name());
            noticeDto.setFile_path(existingnotice.getFile_path());
            noticeDto.setFile_size(existingnotice.getFile_size());

            int noticeResult2 = noticeService.noticeUpdate2(noticeDto);

            if (noticeResult2 > 0) {
                log.info("noticeUpdateProcess");
                log.info(noticeDto.getNotice_id().toString());
                String notice_id = noticeDto.getNotice_id().toString();
                return "redirect:/noticeDetail?notice_id=" + notice_id;
            } else {
                return "noticeEdit";
            }
        }
    }

    /**notice 게시판 글 삭제 */
    @GetMapping("/admin/noticeDelete")
    public String noticeDelete_form(@RequestParam("notice_id") int notice_id, @ModelAttribute NoticeDto noticeDto, HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("user_id");
        Integer userLevel = (Integer) session.getAttribute("user_level");
        // 기존 파일 정보 가져오기
        NoticeDto existingnotice = noticeService.getNoticeById(Math.toIntExact(noticeDto.getNotice_id()));
        String uploadDir = session.getServletContext().getRealPath("/") + "uploads";
        if (existingnotice.getNew_file_name() != null) {
            Path oldFilePath = Paths.get(uploadDir, existingnotice.getNew_file_name());
            Files.deleteIfExists(oldFilePath);
        }
//        if (userId == null || userId.isEmpty()) {
//            // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
//            return "redirect:/loginForm";
//        }
        NoticeDto noticeDetail = noticeService.getNoticeById(notice_id);

//        if((userLevel != 123 && !Objects.equals(noticeDetail.getNotice_user_id(), userId))) {
//            session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
//            return "redirect:/noticeList";
//        }

        noticeService.deleteNotice(notice_id);

        session.setAttribute("deleteQns", "글이 삭제되었습니다.");
        return "redirect:/noticeList";
    }

    /**notice 게시판 파일 다운로드 */
    @GetMapping("/admin/download")
    public String downloadFile(@RequestParam("file_name") String fileName,
                               @RequestParam("file_path") String filePath,
                               HttpServletResponse response, HttpSession session) {
        try {
            String userId = (String) session.getAttribute("user_id");
            Integer userLevel = (Integer) session.getAttribute("user_level");

//            if (userId == null || userId.isEmpty()) {
//                // 사용자 인증 실패 시 로그인 페이지로 리다이렉트
//                return "redirect:/loginForm";
//            }
//            if(userLevel != 123) {
//                session.setAttribute("errorMessage", "권한이 없는 사용자입니다.");
//                return "redirect:/noticeList";
//            }

            // 파일 경로 필터링
            filePath = downFilterService.filter(filePath);
            // 파일이 저장된 절대 경로 (예: /uploads/dog_20250203_205142.jpg)
            String uploadDir = "src/main/webapp";
            File file = new File(uploadDir + filePath);

            log.info(Paths.get(System.getProperty("user.dir"), "src", "main", "webapp", "uploads").toString());

            if (!file.exists()) {
                session.setAttribute("errorMessage", "파일 다운 실패.");
                // 파일이 존재하지 않으면 리다이렉트
                return "redirect:/noticeList";
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


    /**notice 게시판 글 검색*/
    @GetMapping("/noticeSearch")
    public String searchnoticeList(@RequestParam("keyword") String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                Model model) {
        keyword = xssFilterService.filter(keyword);
        keyword = sqlFilterService.filter(keyword);


        int pageSize = 10;
        List<NoticeDto> noticeList = noticeService.searchNoticeByKeyword(keyword, page);

        // 검색된 게시글 수 (필터링된 결과의 총 개수)
        int totalSearchResults = noticeService.getTotalNoticesByKeyword(keyword);
        int totalPages = (int) Math.ceil((double) totalSearchResults / pageSize);

        // 페이지 네비게이션에 필요한 정보 추가
        int startPage = Math.max(1, page - 2);
        int endPage = Math.min(totalPages, page + 2);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("keyword", keyword);

        return "noticeList";
    }


}

