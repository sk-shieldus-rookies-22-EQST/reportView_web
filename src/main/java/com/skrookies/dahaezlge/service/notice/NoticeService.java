package com.skrookies.dahaezlge.service.notice;

import com.skrookies.dahaezlge.controller.notice.Dto.NoticeDto;
import com.skrookies.dahaezlge.repository.notice.NoticeRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private static final Logger log = LoggerFactory.getLogger(NoticeService.class);
    private final NoticeRepository noticeRepository;


    public int notice(NoticeDto noticeDto) {
        return noticeRepository.notice(noticeDto);
    }


    /** notice 작성 글 전체 반환
     * @return List<noticeListDto></> */
    public List<NoticeDto> getAllNoticeList() {
        List<NoticeDto> notice_datas = noticeRepository.getNoticeList();

        List<NoticeDto> notice_list = new ArrayList<>();
        for(NoticeDto noticeDto : notice_datas){
            noticeDto.setNotice_id(noticeDto.getNotice_id());
            noticeDto.setNotice_title(noticeDto.getNotice_title());
            noticeDto.setNotice_user_id(noticeDto.getNotice_user_id());
            noticeDto.setNotice_created_at(noticeDto.getNotice_created_at());

            notice_list.add(noticeDto);
        }

        return notice_list;
    }


    /** notice 게시글 상세 페이지 Data
     * 댓글 정보까지 포함된 Dto return
     * @return noticeDetailDto */
    public NoticeDto getNoticeDetailById(int notice_id) {

        NoticeDto noticeDto = noticeRepository.NoticeById(notice_id);

        NoticeDto noticeDetailDto = new NoticeDto();

        noticeDetailDto.setNotice_id(noticeDto.getNotice_id());
        noticeDetailDto.setNotice_title(noticeDto.getNotice_title());
        noticeDetailDto.setNotice_user_id(noticeDto.getNotice_user_id());
        noticeDetailDto.setNotice_created_at(noticeDto.getNotice_created_at());
        noticeDetailDto.setNotice_body(noticeDto.getNotice_body());
        noticeDetailDto.setFile_name(noticeDto.getFile_name());
        noticeDetailDto.setFile_path(noticeDto.getFile_path());

        return noticeDetailDto;
    }


    public NoticeDto getNoticeById(int notice_id) {
        return noticeRepository.NoticeById(notice_id);
    }


    public void deleteNotice(int notice_id) {
        noticeRepository.deleteNotice(notice_id);
    }


    public int noticeUpdate(NoticeDto noticeDto) {
        return noticeRepository.noticeUpdate(noticeDto);
    }


    public int noticeUpdate2(NoticeDto noticeDto) {
        return noticeRepository.noticeUpdate2(noticeDto);
    }


    public int getTotalNotices() {
        return noticeRepository.countTotalNotices(); //전체 게시글 수 조회
    }


    public List<NoticeDto> getNoticesByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize; // 가져올 데이터 시작 위치
        return noticeRepository.findnoticesByPage(offset, pageSize);
    }

    public NoticeDto getNoticeByFileName(String fileName) {
        return noticeRepository.findByFileName(fileName);
    }

    public int getTotalNoticesByKeyword(String keyword) {
        return noticeRepository.countTotalNoticesByKeyword(keyword);
    }


    public List<NoticeDto> searchNoticeByKeyword(String keyword, int page) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        return noticeRepository.findByKeyword(keyword, offset, pageSize);
    }
}
